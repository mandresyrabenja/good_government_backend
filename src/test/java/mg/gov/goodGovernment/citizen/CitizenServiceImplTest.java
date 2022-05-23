package mg.gov.goodGovernment.citizen;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static mg.gov.goodGovernment.citizen.dataValidation.CitizenValidationResult.PASSWORD_TOO_SHORT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CitizenServiceImplTest {

    @Mock private CitizenRepository citizenRepository;
    private CitizenServiceImpl underTest;
    private Citizen citizen;

    @BeforeEach
    void setUp() {
        underTest = new CitizenServiceImpl(citizenRepository, new BCryptPasswordEncoder());
        citizen = new Citizen();
    }

    @AfterEach
    void tearDown() {
        citizen = null;
    }

    @Test
    void itShouldLoadUserDetailsUsingEmail() {
        //given
        String email = "mandresyrabenj@gmail.com";
        citizen = new Citizen(
                123_456_789L,
                "Mandresy",
                "Rabenja",
                LocalDate.of(2001, Month.MARCH, 8),
                email,
                "abcd1234");

        given(citizenRepository.findByEmail(email))
                .willReturn(Optional.of(citizen));

        //when
        UserDetails actual = underTest.loadUserByUsername(email);

        //then
        assertThat(actual.getUsername()).isEqualTo(email);
    }

    @Test
    void itShouldVerifyIfACitizenIsAnAdult() {
        //given
        citizen.setDob(LocalDate.of(2001, Month.MARCH, 8));

        //when
        boolean result = underTest.isAdultCitizen(citizen);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void passwordShouldContainEightOrMoreWords() {
        //given
        citizen.setPassword("abcd123");

        //when
        //then
        assertThatThrownBy(() -> underTest.createCitizen(citizen))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(PASSWORD_TOO_SHORT.getResult());
        verify(citizenRepository, never()).save(citizen);
    }

    @Test
    void canCreateACitizen() {
        // given
        citizen = new Citizen(
                123_456_789L,
                "Mandresy",
                "Rabenja",
                LocalDate.of(2001, Month.MARCH, 8),
                "mandresyrabenj@gmail.com",
                "abcd1234");

        // when
        underTest.createCitizen(citizen);

        // then
        ArgumentCaptor<Citizen> argumentCaptor = ArgumentCaptor.forClass(Citizen.class);
        verify(citizenRepository).save(argumentCaptor.capture());
        Citizen capturedCitizen = argumentCaptor.getValue();
        assertThat(capturedCitizen).isEqualTo(citizen);
    }

    @Test
    void canGetAllCitizens() {
        // when
        underTest.findAllCitizens();

        // then
        verify(citizenRepository).findAll();
    }

    @Test
    void itShouldDeleteACitizen() {
        //given
        long id = 1L;
        citizen.setId(id);
        given(citizenRepository.findById(id))
                .willReturn(Optional.of(citizen));

        //when
        underTest.deleteCitizen(id);

        //then
        verify(citizenRepository).delete(citizen);
    }

    @Test
    void itShouldFindCitizenUsingId() {
        //given
        long id = 123L;
        citizen.setId(id);
        given(citizenRepository.findById(id))
                .willReturn(Optional.of(citizen));

        //when
        Citizen result = underTest.findCitizen(id);

        //then
        assertThat(result).isEqualTo(citizen);
    }

}