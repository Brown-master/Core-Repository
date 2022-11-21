package kr.ac.kumoh.cattle.Service;

import kr.ac.kumoh.cattle.DTO.AccidentDTO;
import kr.ac.kumoh.cattle.Repository.AccidentRepository;
import kr.ac.kumoh.cattle.Repository.Data.Search;
import kr.ac.kumoh.cattle.Repository.Data.Wait;
import kr.ac.kumoh.cattle.Repository.MemorySearchRepostiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccidentService {
    @Autowired
    AccidentRepository accidentRepository;

    @Autowired
    MemorySearchRepostiry memorySearchRepostiry;

    //기존 사고 조회
    private AccidentDTO inquireAccident(double user_latitude, double user_longitude){
        Long result = memorySearchRepostiry.inquire(user_latitude,user_longitude);
            return result != null ? accidentRepository.findById(result).get().makeDTO() : null;
    }

    //API 요청
    private List<AccidentDTO> requestAPI(){return null;}

    //중복사고 검증 후 정보 추가
    private synchronized boolean verifyAccident(AccidentDTO item){
        if(!memorySearchRepostiry.duplicateCheck(item.getAccident_id())) {
            memorySearchRepostiry.addSearch(item.getAccident_id(), new Search(item.extractRequired(), 0, item.getLatitude(), item.getLongitude()));
            accidentRepository.save(item.makeEntity());
            memorySearchRepostiry.addWait(new Wait(item.getAccident_id(), item.getLatitude(), item.getLongitude()));

            return true;
        }

        return false;
    }

    //새로운 사고 정보 요청
    private boolean RequestAccident(){
        List<AccidentDTO> items= requestAPI();

        boolean result = false;

        for(AccidentDTO item:items){
            if(!result && verifyAccident(item))
                result = true;
        }

        return result;
    }


    //유저 사고 탐색 요청 처리
    public AccidentDTO SearchAccident(double user_latitude, double user_longitude){
        AccidentDTO result = inquireAccident(user_latitude, user_longitude);

        if(result != null)
            return result;

        if(RequestAccident()){
            return inquireAccident(user_latitude, user_longitude);
        }

        return null;
    }

    //매칭 수락
    public synchronized boolean tryMatching(Long accident_id){
        Search target = memorySearchRepostiry.getSearch(accident_id);
        if(target.checkCleared()) {
            return false;
        }

        target.increaseCurrent();
        if(target.checkCleared()){
            memorySearchRepostiry.deleteWait(accident_id);
        }

        return true;
    }


    //매칭 취소 (거절 아님, 수락 이후의 취소)
    public synchronized void cancelMatching(Long accident_id){
        Search target = memorySearchRepostiry.getSearch(accident_id);

        target.decreaseCurrent();

        if(!memorySearchRepostiry.presentWait(accident_id)){
            memorySearchRepostiry.addWait(new Wait(accident_id, target.getLatitude(), target.getLongitude()));
        }
    }
}
