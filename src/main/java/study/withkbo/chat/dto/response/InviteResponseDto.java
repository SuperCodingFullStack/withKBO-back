package study.withkbo.chat.dto.response;

import lombok.Getter;
import lombok.Setter;
import study.withkbo.chat.entity.ChatInvitation;
import study.withkbo.user.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class InviteResponseDto {
    private Long invitiationId;
    private String status;
    private List<Long> inviteeIds;

    public InviteResponseDto(ChatInvitation invitation) {
        this.invitiationId = invitation.getId();
        this.status = invitation.getStatus();
        this.inviteeIds = invitation.getInvitee()
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }
}
