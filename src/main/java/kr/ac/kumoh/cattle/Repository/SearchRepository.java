package kr.ac.kumoh.cattle.Repository;


import kr.ac.kumoh.cattle.Repository.Data.Search;
import kr.ac.kumoh.cattle.Repository.Data.Wait;

public interface SearchRepository {
    public Long inquire(double user_latitude, double user_longitude);

    public boolean duplicateCheck(Long accident_id);

    public void addSearch(Long accident_id, Search newone);

    public Search getSearch(Long accident_id);

    public void addWait(Wait newone);

    public Wait getWait(Long accident_id);

    public void deleteWait(Long accident_id);

    public boolean presentWait(Long accident_id);
}
