package kr.ac.kumoh.cattle.Repository.Data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Search {
    private int required;
    private int current;

    private double latitude;

    private double longitude;

    public boolean checkCleared(){
        return required==current?true:false;
    }

    public int increaseCurrent(){
        return ++ current;
    }

    public int decreaseCurrent(){
        return --current;
    }

}