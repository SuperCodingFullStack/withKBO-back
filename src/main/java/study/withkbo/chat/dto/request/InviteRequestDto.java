package study.withkbo.chat.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InviteRequestDto {
    private Long inviterUserId; // 초대자
    private List<Long> inviteeUserId; // 초대받는 사람
}
