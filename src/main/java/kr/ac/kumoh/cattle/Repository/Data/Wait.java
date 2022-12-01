package kr.ac.kumoh.cattle.Repository.Data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
@AllArgsConstructor
public class Wait {
    private Long accident_id;
    private double latitude;
    private double longitude;
    public boolean distanceFilter(double user_latitude, double user_longitude){
        if(Distance.getDistance(latitude,longitude,user_latitude,user_longitude)<=5)
            return true;
        return false;
    }
}