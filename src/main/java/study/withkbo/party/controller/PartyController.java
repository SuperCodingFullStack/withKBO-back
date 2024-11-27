package study.withkbo.party.controller;

import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping("/{partyPostId}") //user 구현 후 userid 추가 예정
    public void createParty(@PathVariable("partyPostId") Long partyPostId, HttpServletRequest request) {
    }

    @GetMapping()//user 구현 후 userid 추가 예정
    public void getPartyList() {
    }

    @DeleteMapping("/{partyId}")//user 구현 후 userid 추가 예정
    public ApiResponseDto deleteParty(@PathVariable("partyId") Long partyId) {
        partyService.deleteParty(partyId);
        return ApiResponseDto.success(MessageType.DELETE,partyId);
    }
}
