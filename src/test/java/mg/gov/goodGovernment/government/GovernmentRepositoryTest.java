package mg.gov.goodGovernment.government;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class GovernmentRepositoryTest {

    @Autowired
    private GovernmentRepository underTest;
    private Government govAccount;

    @BeforeEach
    void setUp() {
        govAccount = new Government("admin", "admin");
        underTest.save(govAccount);
    }

    @AfterEach
    void tearDown(){ underTest.deleteAll(); }

    @ParameterizedTest
    @CsvSource({
            "admin, true",
            "foo, false"
    })
    void itShouldCheckIfGovAccountExistsUsingLogin(String login, boolean expected) {
        //when
        boolean actual = underTest.existsByLoginIgnoreCase(login);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void itShouldFindGovAccountUsingLogin() {
        //when
        Optional<Government> actual = underTest.findByLoginIgnoreCase("admin");

        //then
        assertThat(actual).isNotEmpty()
                .get()
                .isEqualTo(govAccount);
    }
}