package tobyspring.splearn.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {
    @Test
    @DisplayName("회원을 생성하면 초기 상태는 가입대기(PENDING)이다.")
    void createMember() {
        var member = new Member("toby@splearn.app", "Toby", "secret");
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }
}