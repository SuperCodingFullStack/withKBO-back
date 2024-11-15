//package study.withkbo.comment.service;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.openqa.selenium.NotFoundException;
//import org.springframework.stereotype.Service;
//import study.withkbo.comment.entity.Comment;
//import study.withkbo.comment.repository.CommentRepository;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class CommentService {
//    private final CommentRepository commentRepository;
//
//    public List<Comment> getAllCommentsById(String partyPostId) {
//        Long idLong = Long.parseLong(partyPostId);
//        List<Comment> comments = commentRepository.findAllById(Collections.singleton(idLong));
//        if (comments.isEmpty()) throw new NotFoundException("해당 게시글에는 작성된 댓글이 없습니다. " + partyPostId);
//
//        return comments.stream().map(CommentMapper.INSTANCE::commentTOCommmentDTO).collect(Collectors.toList());
//    }
//}
