package study.withkbo.party.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.withkbo.common.response.ApiResponseDto;
import study.withkbo.common.response.MessageType;
import study.withkbo.party.dto.response.PartyCreateResponseDto;
import study.withkbo.party.dto.response.PartyListResponseDto;
import study.withkbo.party.service.PartyService;
import study.withkbo.user.entity.User;

import java.util.List;

@RestController
@RequestMapping("/api/party")
@RequiredArgsConstructor
public class PartyController {

    private final PartyService partyService;

    //아직 공통응답 형식이 정해지지 않아 임시 dto return 중입니다.

    //임시 userId 생성
    User user = new User();

    @PostMapping("/{partyPostId}") //user 구현 후 userid 추가 예정
    public ApiResponseDto<PartyCreateResponseDto> createParty(@PathVariable("partyPostId") Long partyPostId) {
        return ApiResponseDto.success(MessageType.SEND,partyService.createParty(partyPostId, user));
    }

    @GetMapping()//user 구현 후 userid 추가 예정
    public ApiResponseDto<List<PartyListResponseDto>> getPartyList() {

        return ApiResponseDto.success(MessageType.RETRIEVE,partyService.getPartyList(user));
    }

    @DeleteMapping("/{partyId}")//user 구현 후 userid 추가 예정
    public ApiResponseDto deleteParty(@PathVariable("partyId") Long partyId) {
        partyService.deleteParty(partyId, user);
        return ApiResponseDto.success(MessageType.DELETE,partyId);
    }
}
