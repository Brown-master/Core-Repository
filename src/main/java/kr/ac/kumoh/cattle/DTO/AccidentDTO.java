package kr.ac.kumoh.cattle.DTO;

import kr.ac.kumoh.cattle.Entity.Accident;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link Accident} entity
 */
@Data
public class AccidentDTO implements Serializable {
    private final Long accident_id;
    private final String message;
    private final String road_name;
    private final Integer road_num;
    private final String road_direction;
    private final Double latitude;
    private final Double longitude;
    private final LocalDateTime date_time;

    @Builder
    public AccidentDTO(Long accident_id, String message, String road_name, Integer road_num, String road_direction, Double latitude, Double longitude, LocalDateTime date_time) {
        this.accident_id = accident_id;
        this.message = message;
        this.road_name = road_name;
        this.road_num = road_num;
        this.road_direction = road_direction;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date_time = date_time;
    }

    public Accident makeEntity(){
        return Accident.builder().accident_id(this.accident_id).message(this.message)
                .road_name(this.road_name).road_num(this.road_num).road_direction(this.road_direction)
                .latitude(this.latitude).longitude(this.longitude).date_time(this.date_time).build();
    }

    public int extractRequired(){
        return 2;
    }
}