package study.withkbo.party.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.withkbo.common.response.ApiResponseDto;
import study.withkbo.common.response.MessageType;
import study.withkbo.party.dto.response.PartyCreateResponseDto;
import study.withkbo.party.dto.response.PartyListResponseDto;
import study.withkbo.party.service.PartyService;
import study.withkbo.security.UserDetailsImpl;
import study.withkbo.user.entity.User;

import java.util.List;

@RestController
@RequestMapping("/api/party")
@RequiredArgsConstructor
public class PartyController {

    private final PartyService partyService;

    @PostMapping("/{partyPostId}")
    public ApiResponseDto<PartyCreateResponseDto> createParty(@PathVariable("partyPostId") Long partyPostId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        PartyCreateResponseDto pcrDto = partyService.createParty(partyPostId,user);
        return ApiResponseDto.success(MessageType.CREATE,pcrDto);
    }

    @GetMapping()
    public ApiResponseDto<List<PartyListResponseDto>> getPartyList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        List<PartyListResponseDto> result = partyService.getPartyList(user);
        return ApiResponseDto.success(MessageType.RETRIEVE,result);
    }

    @DeleteMapping("/{partyId}")//user 구현 후 userid 추가 예정
    public ApiResponseDto deleteParty(@PathVariable("partyId") Long partyId) {
        partyService.deleteParty(partyId);
        return ApiResponseDto.success(MessageType.DELETE,partyId);
    }
}
