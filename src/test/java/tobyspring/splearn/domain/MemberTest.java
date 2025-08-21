package tobyspring.splearn.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {
    @Test
    @DisplayName("회원을 생성하면 초기 상태는 가입대기(PENDING)이다.")
    void createMember() {
        var member = new Member("toby@splearn.app", "Toby", "secret");

        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    @DisplayName("회원 가입 시 null 값은 허용되지 않는다. ")
    void constructorNullCheck() {
        assertThatThrownBy(() -> new Member("toby@splearn.app", null, "secret"))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("회원을 activate 하면 가입 완료 상태가 된다.")
    void activate() {
        var member = new Member("toby@splearn.app", "Toby", "secret");

        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }
    
    @Test
    @DisplayName("이미 가입이 완료된 경우 에러가 발생한다. (PENDING 상태가 아닌 경우)")
    void activateFail() {
        var member = new Member("toby@splearn.app", "Toby", "secret");

        member.activate();

        assertThatThrownBy(member::activate).isInstanceOf(IllegalStateException.class);
    }
    
    @Test
    @DisplayName("회원을 deactivate 하면 탈퇴 상태가 된다.")
    void deactivate() {
        var member = new Member("toby@splearn.app", "Toby", "secret");
        member.activate();

        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void deactivateFail() {
        var member = new Member("toby@splearn.app", "Toby", "secret");

        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
    }
}