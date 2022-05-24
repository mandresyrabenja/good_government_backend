package mg.gov.goodGovernment.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Sha256Test {

    @Test
    void ItShouldHashAStringUsingSha256() {
        //given
        String given = "abcd1234";
        String expected = "e9cee71ab932fde863338d08be4de9dfe39ea049bdafb342ce659ec5450b69ae";

        //when
        String result = Sha256.hash(given);

        //then
        assertThat(result).isEqualTo(expected);

    }
}