package mg.gov.goodGovernment.region;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RegionServiceImplTest {

    private RegionServiceImpl underTest;
    @Mock private RegionRepository regionRepository;
    private Region regionAccount;

    @BeforeEach
    void setUp() {
        underTest = new RegionServiceImpl(regionRepository, new BCryptPasswordEncoder());
        regionAccount = new Region("foo", "bar");
    }

    @Test
    void itShouldLoadRegionUserDetailsUsingUsername() {
        //given
        String regionName = "foo";
        given(regionRepository.findByNameIgnoreCase(regionName)).willReturn(Optional.of(regionAccount));

        //when
        UserDetails userDetails = underTest.loadUserByUsername(regionName);

        //then
        assertThat(userDetails.getUsername()).isEqualTo(regionName);
    }

    @Test
    @DisplayName("Should fail when attempt to create a region account that has a name that already exist")
    void doNotCreateARegionWithANameAlreadyExist() {
        //given
        given(regionRepository.existsByName(regionAccount.getName())).willReturn(true);

        //then
        assertThatThrownBy(() -> underTest.createRegion(regionAccount))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("already");
        verify(regionRepository, never()).save(regionAccount);
    }

    @Test
    void itShouldCreateARegionAccount() {
        //given
        given(regionRepository.existsByName(regionAccount.getName())).willReturn(false);

        //when
        underTest.createRegion(regionAccount);

        //then
        ArgumentCaptor<Region> argumentCaptor = ArgumentCaptor.forClass(Region.class);
        verify(regionRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(regionAccount);
    }

    @Test
    void itShouldCountRegionNumberInDatabase() {
        //when
        underTest.count();

        //then
        verify(regionRepository).count();
    }

    @Test
    void itShouldFindAllRegionsUsingPage() {
        //given
        int page = 1;
        Pageable pageable = PageRequest.of(page, 10);

        //when
        underTest.findAllRegions(page);

        //then
        ArgumentCaptor<Pageable> argumentCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(regionRepository).findByOrderByName(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue())
                .isEqualTo(pageable);
    }

    @Test
    void itShouldFailToDeleteRegionWhenRegionIdDoesNotExists() {
        //given
        given(regionRepository.findById(1)).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> underTest.deleteRegion(1))
                .isInstanceOf(IllegalStateException.class);
        verify(regionRepository, never()).delete(new Region());
    }

    @Test
    void itShouldDeleteARegion() {
        //given
        given(regionRepository.findById(1))
                .willReturn(Optional.of(regionAccount));

        //when
        underTest.deleteRegion(1);

        //then
        ArgumentCaptor<Region> argumentCaptor = ArgumentCaptor.forClass(Region.class);
        verify(regionRepository).delete(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue())
                .isEqualTo(regionAccount);
    }
}