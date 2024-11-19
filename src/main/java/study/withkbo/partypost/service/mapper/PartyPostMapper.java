package study.withkbo.partypost.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import study.withkbo.comment.dto.response.CommentResponseDto;
import study.withkbo.comment.entity.Comment;
import study.withkbo.partypost.dto.response.PartyPostResponseDto;
import study.withkbo.partypost.entity.PartyPost;


import java.util.List;


@Mapper
@Component
public interface PartyPostMapper {

    // PartyPostMapper의 싱글톤 인스턴스 생성
    PartyPostMapper INSTANCE = Mappers.getMapper(PartyPostMapper.class);

    // PartyPost -> PartyPostResponseDto 변환
//    @Mapping(target = "userId", source = "user.id")
//    @Mapping(target = "userName", source = "user.uname")
    @Mapping(target = "userNickname", source = "user.UNickname")
    @Mapping(target = "userImg", source = "user.profileImg")
//    @Mapping(target = "matchLoc", source = "game.matchLoc")  // 예시로 매핑 처리
//    @Mapping(target = "matchImg", source = "game.matchImg")  // matchImg 매핑
//    @Mapping(target = "matchDate", source = "game.matchDateTime")  // matchDate 매핑
    @Mapping(target = "createAt", source = "createdDate")  // createAt 매핑
    @Mapping(target = "comments", source = "comments")  // 댓글 리스트 변환
    PartyPostResponseDto partyPostToPartyPostResponseDto(PartyPost post);

    // Comment -> CommentResponseDto 변환
    CommentResponseDto commentToCommentResponseDto(Comment comment);

    // PartyPost의 댓글 리스트를 CommentResponseDto 리스트로 변환
    List<CommentResponseDto> commentsToCommentResponseDtoList(List<Comment> comments);
}
