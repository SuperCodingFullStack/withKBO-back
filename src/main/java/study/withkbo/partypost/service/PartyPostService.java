//package study.withkbo.partypost.service;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import study.withkbo.partypost.dto.response.PartyPostResponseDto;
//import study.withkbo.partypost.dto.response.PartyPostResponseDto;
//import study.withkbo.partypost.entity.PartyPost;
//import study.withkbo.partypost.repository.PartyPostRepository;
//import study.withkbo.partypost.service.Mapper.PartyPostMapper;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class PartyPostService {
//    private final PartyPostRepository partyPostRepository;
//    public Page<PartyPostResponseDto> findAllWithPageable(Pageable pageable) {
//        Page<PartyPost> partyPostEntities = partyPostRepository.findAll(pageable);
//        return partyPostEntities.map(PartyPostMapper.INSTANCE::partyPostEntityToPartyPostDto);
//    }
//}
