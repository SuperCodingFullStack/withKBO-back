package study.withkbo.party.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.withkbo.exception.common.CommonError;
import study.withkbo.exception.common.CommonException;
import study.withkbo.jwt.JwtUtil;
import study.withkbo.party.dto.response.PartyCreateResponseDto;
import study.withkbo.party.dto.response.PartyListResponseDto;
import study.withkbo.party.entity.Party;
import study.withkbo.party.repository.PartyRepository;
import study.withkbo.user.entity.User;
import study.withkbo.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartyService {
    private final PartyRepository partyRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public void createParty(Long partyPostId, HttpServletRequest request) {
    }

    public List<PartyListResponseDto> getPartyList(User user) {
        List<Party> partyList = partyRepository.findByUser(user);
        if (partyList.isEmpty()) {
            //예외 임시처리
            throw new CommonException(CommonError.PARTY_NOT_FOUND);
        }

        return partyList.stream().map(PartyListResponseDto::new).toList();
    }

    public void deleteParty(Long partyId) {
    }

}