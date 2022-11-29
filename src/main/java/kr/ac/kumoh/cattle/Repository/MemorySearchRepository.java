package kr.ac.kumoh.cattle.Repository;


import kr.ac.kumoh.cattle.Repository.Data.Search;
import kr.ac.kumoh.cattle.Repository.Data.Wait;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemorySearchRepository {
    public static Vector<Wait> wait_table = new Vector<Wait>();

    public static ConcurrentHashMap<Long, Search> search_table = new ConcurrentHashMap<Long, Search>();

    /*
    public Long inquire(double user_latitude, double user_longitude){
        for(Wait wait : wait_table){
            if(wait.distanceFilter(user_latitude, user_longitude))
                return wait.getAccident_id();
        }
        return null;
    }*/

    public List<Long> inquire(double user_latitude, double user_longitude){
        List<Long> items = new ArrayList<Long>();
        for(Wait wait : wait_table){
            if(wait.distanceFilter(user_latitude, user_longitude))
                items.add(wait.getAccident_id());
        }
        return items;
    }

    public boolean duplicateCheck(Long accident_id){
        return search_table.containsKey(accident_id);
    }

    public void addSearch(Long accident_id, Search newone){
        search_table.put(accident_id, newone);
    }

    public Search getSearch(Long accident_id){
        return search_table.get(accident_id);
    }

    public void addWait(Wait newone){
        wait_table.add(newone);
    }

    public Wait getWait(Long accident_id){
        for(int i=0; i<wait_table.size(); i++){
            if(wait_table.get(i).getAccident_id().equals(accident_id)) {
                return wait_table.get(i);
            }
        }

        return null;
    }

    public void deleteWait(Long accident_id){
        for(int i=0; i<wait_table.size(); i++){
            if(wait_table.get(i).getAccident_id().equals(accident_id)) {
                wait_table.remove(i);
                break;
            }
        }
    }

    public boolean presentWait(Long accident_id){
        for(int i=0; i<wait_table.size(); i++){
            if(wait_table.get(i).getAccident_id().equals(accident_id)) {
                return true;
            }
        }
        return false;
    }
}