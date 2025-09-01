package tobyspring.splearn.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {
    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };

        member = Member.register(new MemberRegisterRequest("toby@splearn.app", "Toby", "secret"), passwordEncoder);
    }

    @Test
    @DisplayName("회원을 생성하면 초기 상태는 가입대기(PENDING)이다.")
    void registerMember() {
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    @DisplayName("회원을 activate 하면 가입 완료 상태가 된다.")
    void activate() {
        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }
    
    @Test
    @DisplayName("이미 가입이 완료된 경우 에러가 발생한다. (PENDING 상태가 아닌 경우)")
    void activateFail() {
        member.activate();

        assertThatThrownBy(member::activate).isInstanceOf(IllegalStateException.class);
    }
    
    @Test
    @DisplayName("회원을 deactivate 하면 탈퇴 상태가 된다.")
    void deactivate() {
        member.activate();

        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void deactivateFail() {
        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void varifyPassword() {
        assertThat(member.verifyPassword("secret", passwordEncoder)).isTrue();
        assertThat(member.verifyPassword("hello", passwordEncoder)).isFalse();
    }

    @Test
    void changeNickname() {
        assertThat(member.getNickname()).isEqualTo("Toby");
        member.changeNickname("charlie");
        assertThat(member.getNickname()).isEqualTo("charlie");
    }

    @Test
    void changePassword() {
        member.changePassword("verysecret", passwordEncoder);
        assertThat(member.verifyPassword("verysecret", passwordEncoder)).isTrue();
    }

    @Test
    void isActive() {
        assertThat(member.isActive()).isFalse();
        member.activate();
        assertThat(member.isActive()).isTrue();
        member.deactivate();
        assertThat(member.isActive()).isFalse();
    }

    @Test
    void invalidEmail() {
        assertThatThrownBy(() ->
                Member.register(new MemberRegisterRequest("invalid email", "Toby", "secret"), passwordEncoder)
        ).isInstanceOf(IllegalArgumentException.class);

        Member.register(new MemberRegisterRequest("toby@splearn.app", "Toby", "secret"), passwordEncoder);
    }
}