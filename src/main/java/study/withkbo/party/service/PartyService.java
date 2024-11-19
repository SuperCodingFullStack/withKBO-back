package study.withkbo.party.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.withkbo.party.dto.response.PartyCreateResponseDto;
import study.withkbo.party.dto.response.PartyListResponseDto;
import study.withkbo.party.entity.Party;
import study.withkbo.party.repository.PartyRepository;
import study.withkbo.user.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartyService {
    private final PartyRepository partyRepository;

    public PartyCreateResponseDto createParty(Long partyPostId, User user) {

        if(partyRepository.findByPartyPostIdAndUser(partyPostId, user).isPresent()){
            throw new RuntimeException("error : party already exists");
        }
        Party party = partyRepository.save(new Party(partyPostId, user));
        return new PartyCreateResponseDto(party);
    }

    public List<PartyListResponseDto> getPartyList(User user) {
        List<Party> partyList = partyRepository.findByUser(user);
        if (partyList.isEmpty()) {
            //예외 임시처리
            throw new RuntimeException("error : party list is empty");
        }

        return partyList.stream().map(PartyListResponseDto::new).toList();
    }

    public void deleteParty(Long partyId, User user) {

        Party party = partyRepository.findById(partyId).orElseThrow(
                () -> new RuntimeException("error : party not found")
        );

        if(party.getUser().getId() != user.getId()){
            throw new RuntimeException("error : user id not match");
        }

        partyRepository.deleteById(partyId);

    }

}