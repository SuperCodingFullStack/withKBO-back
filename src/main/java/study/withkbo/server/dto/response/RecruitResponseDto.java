package study.withkbo.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecruitResponseDto {
    // 서버 -> 클라이언트
    private Long messageId;
    private String roomName;
    private String sender;
    private String message;
    private String timestamp;
}
