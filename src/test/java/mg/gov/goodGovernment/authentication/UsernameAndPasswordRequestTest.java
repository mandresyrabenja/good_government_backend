package mg.gov.goodGovernment.authentication;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UsernameAndPasswordRequestTest {

    @Test
    void itShouldHashUserPassword() {
        //given
        UsernameAndPasswordRequest underTest = new UsernameAndPasswordRequest();
        underTest.setPassword("abcd");
        String excpected = "88d4266fd4e6338d13b845fcf289579d209c897823b9217da3e161936f031589";

        //when
        underTest.hashPassword();

        //then
        assertThat(underTest.getPassword()).isEqualTo(excpected);
    }
}