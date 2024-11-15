//package study.withkbo.partypost.controller;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import study.withkbo.partypost.dto.response.PartyPostResponseDto;
//import study.withkbo.partypost.service.PartyPostService;
//
//@RestController
//@RequestMapping("/api")
//@RequiredArgsConstructor
//@Slf4j
//public class PartyPostController {
//    private final PartyPostService partyPostService;
//
////    @ApiOperation("전체 글목록 페이지네이션 지원")
//    @GetMapping("/posts")
//    public Page<PartyPostResponseDto> findPartyPosts(Pageable pageable) {
//        return PartyPostService.findAllWithPageable(pageable);
//    }
//}
