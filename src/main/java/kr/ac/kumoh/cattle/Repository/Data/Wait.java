package kr.ac.kumoh.cattle.Repository.Data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Wait {
    private Long accident_id;

    private double latitude;

    private double longitude;

    public boolean distanceFilter(double user_latitude, double user_longitude){
        return true;
    }
}
