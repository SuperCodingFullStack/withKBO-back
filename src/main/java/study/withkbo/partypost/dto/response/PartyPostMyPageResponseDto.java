package study.withkbo.partypost.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.withkbo.partypost.entity.PartyPost;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyPostMyPageResponseDto {
    private Long postId; // 어떤 게시글인지
    private String title; // 글 제목
    private LocalDateTime createdDate; // 작성일시

    public static PartyPostMyPageResponseDto fromEntity(PartyPost partyPost) {
        return  PartyPostMyPageResponseDto.builder()
                .postId(partyPost.getId())
                .title(partyPost.getTitle())
                .createdDate(partyPost.getCreatedDate())
                .build();
    }
}
