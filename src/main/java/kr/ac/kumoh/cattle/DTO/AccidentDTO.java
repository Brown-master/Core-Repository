package kr.ac.kumoh.cattle.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kr.ac.kumoh.cattle.Entity.Accident;
import kr.ac.kumoh.cattle.Repository.SearchRepository;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link Accident} entity
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccidentDTO implements Serializable {
    private String eventType;
    private String getType;
    private String eventDetailType;
    private String coordX;
    private String coordY;
    private String lanesBlocked;
    private String message;
    private String roadName;
    private String roadDrcType;
    private String linkId;
    private String roadNo;
    private String lanesBlockType;
    private String startDate;
    private String type;

    public AccidentDTO(String eventType, String getType, String eventDetailType, String coordX, String coordY,
                       String lanesBlocked, String message, String roadName, String roadDrcType, String linkId,
                       String roadNo, String lanesBlockType, String startDate, String type) {
        this.eventType = eventType;
        this.getType = getType;
        this.eventDetailType = eventDetailType;
        this.coordX = coordX;
        this.coordY = coordY;
        this.lanesBlocked = lanesBlocked;
        this.message = message;
        this.roadName = roadName;
        this.roadDrcType = roadDrcType;
        this.linkId = linkId;
        this.roadNo = roadNo;
        this.lanesBlockType = lanesBlockType;
        this.startDate = startDate;
        this.type = type;
    }

    public AccidentDTO() {
    }


}