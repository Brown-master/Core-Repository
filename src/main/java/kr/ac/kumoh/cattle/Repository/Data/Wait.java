package kr.ac.kumoh.cattle.Repository.Data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Wait {
    private Long accident_id;

    private double latitude;

    private double longitude;

    //TODO: 거리 판별
    public boolean distanceFilter(double user_latitude, double user_longitude){
        return true;
    }
}
