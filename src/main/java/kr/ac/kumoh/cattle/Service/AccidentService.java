package kr.ac.kumoh.cattle.Service;



import kr.ac.kumoh.cattle.DTO.AccidentDTO;
import kr.ac.kumoh.cattle.Repository.AccidentRepository;
import kr.ac.kumoh.cattle.Repository.Data.Search;
import kr.ac.kumoh.cattle.Repository.Data.Wait;
import kr.ac.kumoh.cattle.Repository.MemorySearchRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class AccidentService {
    @Autowired
    AccidentRepository accidentRepository;

    @Autowired
    MemorySearchRepository searchRepository;

    //기존 사고 조회
    private List<AccidentDTO> inquireAccident(double user_latitude, double user_longitude){

        ArrayList<AccidentDTO> items = new ArrayList<AccidentDTO>();
        List<Long> ids = searchRepository.inquire(user_latitude,user_longitude);
        for(Long id : ids){
            items.add(accidentRepository.findById(id).get().makeDTO());
        }
        return items;
    }


    //새로운 사고 정보 요청
    private boolean requestAccident() throws IOException {
        JSONArray items= ApiConnection.connect();

        System.out.println(items.length());

        boolean result = false;

        for (int i = 0; i < items.length(); i++){
            if(verifyAccident(items.getJSONObject(i)) && !result)
                result = true;
        }

        return result;
    }


    //중복사고 검증 후 정보 추가
    private synchronized boolean verifyAccident(JSONObject obj){
        Long check = ApiConnection.genObjectId(obj.getString("startDate"), obj.getDouble("coordY"), obj.getDouble("coordX"));
        if(!searchRepository.duplicateCheck(check)) {
            AccidentDTO item = ApiConnection.jsonToDTO(obj);
            searchRepository.addSearch(item.getAccident_id(), new Search(item.extractRequired(), 0, item.getLatitude(), item.getLongitude()));
            accidentRepository.save(item.makeEntity());
            searchRepository.addWait(new Wait(item.getAccident_id(), item.getLatitude(), item.getLongitude()));

            return true;
        }
        return false;
    }


    //유저 사고 탐색 요청 처리
    public List<AccidentDTO> searchAccident(double user_latitude, double user_longitude) throws IOException {
        List<AccidentDTO> items = inquireAccident(user_latitude, user_longitude);

        if(items.size() == 0){
            if(requestAccident())
                items = inquireAccident(user_latitude, user_longitude);
        }

        return items;
    }


    //매칭 수락
    public synchronized boolean tryMatching(Long accident_id){
        Search target = searchRepository.getSearch(accident_id);

        if(target.checkCleared()) {
            return false;
        }

        target.increaseCurrent();
        if(target.checkCleared()){
            searchRepository.deleteWait(accident_id);
        }

        return true;
    }


    //매칭 취소
    public synchronized void cancelMatching(Long accident_id){
        Search target = searchRepository.getSearch(accident_id);

        target.decreaseCurrent();

        if(!searchRepository.presentWait(accident_id)){
            searchRepository.addWait(new Wait(accident_id, target.getLatitude(), target.getLongitude()));
        }
    }
}
