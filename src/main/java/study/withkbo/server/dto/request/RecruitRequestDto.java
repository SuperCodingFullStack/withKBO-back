package study.withkbo.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecruitRequestDto {
    // 클라이언트 -> 서버
    private Long roomId;
    private String roomName;
    private Long senderId;
    private String sender;
    private String message; // 메시지 전송
}
