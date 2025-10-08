package tobyspring.splearn.domain.member;

/**
 * PasswordEncoder 는 특성이 required application 에 해당 하지만
 * 의존 방향이 application → domain 으로 되어야 하기 때문에
 * domain 패키지에 두어도 됨. (domain 도 application 내부이므로)
 */
public interface PasswordEncoder {
    String encode(String password);
    boolean matches(String password, String passwordHash);
}
