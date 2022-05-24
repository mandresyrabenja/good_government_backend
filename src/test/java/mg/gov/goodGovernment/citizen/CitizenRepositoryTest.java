package mg.gov.goodGovernment.citizen;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CitizenRepositoryTest {

    @Autowired
    private CitizenRepository underTest;
    private Citizen citizen;

    @BeforeEach
    void setUp() {
        citizen = new Citizen(
                123_456_789L,
                "Mandresy",
                "Rabenja",
                LocalDate.of(2001, Month.MARCH, 8),
                "mandresyrabenj@gmail.com",
                "abcd1234");
        underTest.save(citizen);
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldFindCitizenUsingEmail(){
        // given
        String email = "mandresyrabenj@gmail.com";

        //when
        Optional<Citizen> result = underTest.findByEmail(email);

        //then
        assertThat(result).isNotEmpty()
                .get()
                .isEqualTo(citizen);
    }

    @ParameterizedTest
    @CsvSource({
        "mandresyrabenj@gmail.com, true",
        "jeanba@gmail.com, false"
    })
    void itShouldCheckIfCitizenEmailExists(String email, boolean result) {
        // when
        boolean expected = underTest.existsByEmail(email);

        // then
        assertThat(expected).isEqualTo(result);
    }
}