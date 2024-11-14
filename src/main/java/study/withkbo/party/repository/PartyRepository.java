package study.withkbo.party.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.withkbo.party.entity.Party;

public interface PartyRepository extends JpaRepository<Party, Long> {
}
