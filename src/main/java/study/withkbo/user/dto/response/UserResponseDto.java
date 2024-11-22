package study.withkbo.user.dto.response;

import lombok.Getter;
import study.withkbo.user.entity.User;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {
    private Long id;
    private String username;
    private String name;
    private String nickname;
    private String email;
    private String phone;
    private String profileImg;
    private String address;
    private String role;
    private Boolean isDeleted;
    private LocalDateTime createdAt;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.profileImg = user.getProfileImg();
        this.address = user.getAddress();
        this.role = user.getRole().toString();
        this.isDeleted = user.getIsDeleted();
        this.createdAt = user.getCreatedDate();


    }
}
