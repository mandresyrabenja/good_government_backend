package mg.gov.goodGovernment.citizen.dataValidation;

import mg.gov.goodGovernment.citizen.Citizen;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

class CitizenRegistrationValidatorTest {

    private Citizen citizen;

    @BeforeEach
    void setUp() {
        citizen = new Citizen();
    }

    @AfterEach
    void tearDown() {
        citizen = null;
    }

    @Test
    void itShouldTestCitizenRegistrationValidatorCombination() {
        //given
        citizen.setPassword("abcd1234");
        citizen.setEmail("mandresyrabenj@gmail.com");

        //when
        CitizenValidationResult result = CitizenRegistrationValidator.isValidEmail()
                .and(CitizenRegistrationValidator.isPasswordContainsNoAccentedCharacter())
                .apply(citizen);

        //then
        assertThat(result).isEqualTo(CitizenValidationResult.SUCCESS);
    }

    @ParameterizedTest
    @CsvSource({
            "abcd1234, SUCCESS",
            "abcd123, PASSWORD_TOO_SHORT"
    })
    void itShouldVerifyIfPasswordContainsEightOrMoreWords(String password, CitizenValidationResult expected) {
        //given
        citizen.setPassword(password);

        //when
        CitizenValidationResult result = CitizenRegistrationValidator.isPasswordContainsEightOrMoreWords().apply(citizen);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "àbcdefgh, ACCENTED_PASSWORD",
            "abcdefgh, SUCCESS"
    })
    void itShouldVerifyIfPasswordContainsAccentedCharacter(String password, CitizenValidationResult expected) {
        //given
        citizen.setPassword(password);

        //when
        CitizenValidationResult result = CitizenRegistrationValidator.isPasswordContainsNoAccentedCharacter().apply(citizen);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "mandresyrabenja, INVALID_EMAIL",
            "mandresyrabenj@gmailtsyDotCom, INVALID_EMAIL",
            "mandresyrabenj@gmail.123, INVALID_EMAIL",
            "mandresyrabenj@gmail.com, SUCCESS"
    })
    void itShouldVerifyIfCitizenEmailFormatIsValid(String email, CitizenValidationResult expected) {
        //given
        citizen.setEmail(email);

        //when
        CitizenValidationResult result = CitizenRegistrationValidator.isValidEmail().apply(citizen);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "123456789, SUCCESS",
            "null, HAVE_NO_ID"
    })
    void itShouldVerifyIfCitizenIsAdultAndHaveId(String cin, CitizenValidationResult expected) {
        //given
        citizen.setDob(LocalDate.of(2001, Month.MARCH, 8));
        citizen.setCin(
                ("null".equals(cin)) ? null : Long.valueOf(cin)
        );

        //when
        CitizenValidationResult result = CitizenRegistrationValidator.isCitizenAdultAndHaveId().apply(citizen);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "132456789, HAVE_AN_ID",
            "null, SUCCESS"
    })
    void itShouldVerifyIfCitizenIsChildAndHaveNoId(String cin, CitizenValidationResult expected) {
        //given
        citizen.setDob(LocalDate.of(2022, Month.MARCH, 8));
        citizen.setCin(
                ("null".equals(cin)) ? null : Long.valueOf(cin)
        );

        //when
        CitizenValidationResult result = CitizenRegistrationValidator.isCitizenChildAndHaveNoId().apply(citizen);

        //then
        assertThat(result).isEqualTo(expected);
    }
}