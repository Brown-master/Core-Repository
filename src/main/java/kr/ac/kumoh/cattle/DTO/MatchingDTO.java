package kr.ac.kumoh.cattle.DTO;

import kr.ac.kumoh.cattle.Entity.Matching;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link Matching} entity
 */
@Data
public class MatchingDTO implements Serializable {
    private final Long matching_id;
    private final Long user_id;
    private final AccidentDTO accident;
    private final LocalDateTime date_time;

    @Builder
    public MatchingDTO(Long matching_id, Long user_id, AccidentDTO accident, LocalDateTime date_time){
        this.matching_id = matching_id;
        this.user_id = user_id;
        this.accident = accident;
        this.date_time = date_time;
    }
}