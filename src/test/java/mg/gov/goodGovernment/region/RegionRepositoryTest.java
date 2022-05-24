package mg.gov.goodGovernment.region;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RegionRepositoryTest {

    @Autowired
    private RegionRepository underTest;
    private Region regionAccount;

    @BeforeEach
    void setUp() {
        regionAccount = new Region("Andavamamba", "Ilay tanàna");
        underTest.save(regionAccount);
    }

    @AfterEach
    void tearDown() { underTest.deleteAll(); }

    @ParameterizedTest
    @CsvSource({
            "Andavamamba, true",
            "Itaosy, false"
    })
    void itShouldCheckIfRegionAccountExistsByName(String regionName, boolean expected) {
        //when
        boolean actual = underTest.existsByName(regionName);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void itShouldFindRegionAccountUsingNameAndThenOrderingThemByName() {
        //given
        Region anotherRegion = new Region("Bar", "Foo");
        underTest.save(anotherRegion);

        //when
        List<Region> result = underTest.findByOrderByName(PageRequest.of(0, 2));

        //then
        assertThat(result.get(0)).isEqualTo(regionAccount);
        assertThat(result.get(1)).isEqualTo(anotherRegion);
    }

    @Test
    void itShouldFindRegionAccountByNameAndIgnoreCase() {
        //when
        Optional<Region> result = underTest.findByNameIgnoreCase("anDaVamambA");

        //then
        assertThat(result).isNotEmpty()
                .get()
                .isEqualTo(regionAccount);
    }
}