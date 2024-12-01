package study.withkbo.user.entity;

import lombok.Getter;

@Getter
public enum UserRoleEnum {

    USER(Authority.USER),  // 사용자 권한
    ADMIN(Authority.ADMIN);  // 관리자 권한

    private final String authority;

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    // 권한 문자열을 UserRoleEnum으로 변환하는 메서드
    public static UserRoleEnum fromAuthority(String authority) {
        for (UserRoleEnum role : UserRoleEnum.values()) {
            if (role.getAuthority().equals(authority)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown authority: " + authority);
    }

    public static class Authority {
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
    }
}
