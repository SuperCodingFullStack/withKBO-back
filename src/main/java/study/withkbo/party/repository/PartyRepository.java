package study.withkbo.party.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.withkbo.party.dto.response.PartyListResponseDto;
import study.withkbo.party.entity.Party;
import study.withkbo.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface PartyRepository extends JpaRepository<Party, Long> {
    List<Party> findByUser(User user);

    Optional<Party> findByPartyPostIdAndUser(Long partyPostId, User user);
}
