package kr.ac.kumoh.cattle.Repository;


import kr.ac.kumoh.cattle.Repository.Data.Search;
import kr.ac.kumoh.cattle.Repository.Data.Wait;
import org.springframework.stereotype.Repository;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemorySearchRepostiry implements SearchRepository{
    public static Vector<Wait> wait_table = new Vector<Wait>();

    public static ConcurrentHashMap<Long, Search> search_table = new ConcurrentHashMap<Long, Search>();

    @Override
    public Long inquire(double user_latitude, double user_longitude){
        for(Wait wait : wait_table){
            if(wait.distanceFilter(user_latitude, user_longitude))
                return wait.getAccident_id();
        }
        return null;
    }

    @Override
    public boolean duplicateCheck(Long accident_id){
        return search_table.containsKey(accident_id);
    }

    @Override
    public void addSearch(Long accident_id, Search newone){
        search_table.put(accident_id, newone);
    }

    @Override
    public Search getSearch(Long accident_id){
        return search_table.get(accident_id);
    }

    @Override
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

    @Override
    public void deleteWait(Long accident_id){
        for(int i=0; i<wait_table.size(); i++){
            if(wait_table.get(i).getAccident_id().equals(accident_id)) {
                wait_table.remove(i);
                break;
            }
        }
    }

    @Override
    public boolean presentWait(Long accident_id){
        for(int i=0; i<wait_table.size(); i++){
            if(wait_table.get(i).getAccident_id().equals(accident_id)) {
                return true;
            }
        }
        return false;
    }
}