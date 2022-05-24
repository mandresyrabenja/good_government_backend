package mg.gov.goodGovernment.government;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GovernmentServiceImplTest {

    @Mock private GovernmentRepository governmentRepository;
    private GovernmentServiceImpl underTest;
    private Government govAccount;

    @BeforeEach
    void setUp() {
        underTest = new GovernmentServiceImpl(governmentRepository, new BCryptPasswordEncoder());
        govAccount = new Government();
    }

    @Test
    void itShouldloadUserDetailsUsingGovernmentAccountLogin() {
        //given
        String login = "rakoto";
        govAccount.setLogin(login);
        govAccount.setPassword("0000");
        given(governmentRepository.findByLoginIgnoreCase(login))
            .willReturn(Optional.of(govAccount));

        //when
        UserDetails result = underTest.loadUserByUsername(login);

        //then
        assertThat(result.getUsername()).isEqualTo(login);
    }

    @Test
    void itShouldCreateAGovernmentAccount() {
        //Given
        String login = "admin";
        govAccount.setLogin(login);
        govAccount.setPassword("admin");
        given(governmentRepository.existsByLoginIgnoreCase(login))
                .willReturn(false);

        //When
        underTest.createGovernment(govAccount);

        //Then
        ArgumentCaptor<Government> argumentCaptor = ArgumentCaptor.forClass(Government.class);
        verify(governmentRepository).save(argumentCaptor.capture());
        Government capturedGovAccount = argumentCaptor.getValue();
        assertThat(capturedGovAccount).isEqualTo(govAccount);

    }

    @Test
    void itShouldThrowAnExceptionWhenUsernameAlreadyExist() {
        //given
        String username = "foo";
        govAccount.setLogin(username);
        given(governmentRepository.existsByLoginIgnoreCase(username))
                .willReturn(true);

        //then
        assertThatThrownBy(() -> underTest.createGovernment(govAccount))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("already exists");
    }

    @Test
    void itShouldFindAllGovernmentAccount() {
        //when
        underTest.findAll();

        //verify
        verify(governmentRepository).findAll();
    }
}