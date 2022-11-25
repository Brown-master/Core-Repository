package kr.ac.kumoh.cattle.Entity;

import kr.ac.kumoh.cattle.DTO.AccidentDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "accident")
public class Accident {
    @Id
    @Column(name = "accident_id", nullable = false)
    private Long accident_id;

    @Column(name = "message")
    private String message;

    @Column(name = "road_name", nullable = false, length = 45)
    private String road_name;

    @Column(name = "road_num", nullable = false)
    private Integer road_num;

    @Column(name = "road_direction", nullable = false, length = 45)
    private String road_direction;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "date_time", nullable = false)
    private String date_time;


    @Builder
    public Accident(Long accident_id, String message, String road_name, Integer road_num, String road_direction, Double latitude, Double longitude, String date_time) {
        this.accident_id = accident_id;
        this.message = message;
        this.road_name = road_name;
        this.road_num = road_num;
        this.road_direction = road_direction;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date_time = date_time;
    }

    public AccidentDTO makeDTO(){
        return AccidentDTO.builder().accident_id(this.getId()).message(this.getMessage())
                .road_name(this.getRoad_name()).road_num(this.getRoad_num()).road_direction(this.getRoad_direction())
                .latitude(this.getLatitude()).longitude(this.getLongitude()).date_time(this.getDate_time()).build();
    }

    public Long getId() {
        return accident_id;
    }

    public String getMessage() {
        return message;
    }

    public Integer getRoad_num() {
        return road_num;
    }

    public String getRoad_name() {
        return road_name;
    }

    public String getRoad_direction() {
        return road_direction;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getDate_time() {
        return date_time;
    }

}