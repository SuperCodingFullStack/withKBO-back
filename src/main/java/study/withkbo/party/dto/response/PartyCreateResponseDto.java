package study.withkbo.party.dto.response;

import study.withkbo.party.entity.Party;

public class PartyCreateResponseDto {
    private Long partyId;
    private Long partyPostId;

    public PartyCreateResponseDto(Party party) {
        this.partyId = party.getId();
        this.partyPostId = party.getPartyPost().getId();
    }
}
