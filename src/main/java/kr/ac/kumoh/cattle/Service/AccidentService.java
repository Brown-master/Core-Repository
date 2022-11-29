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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


@Service
public class AccidentService {
    @Autowired
    AccidentRepository accidentRepository;

    @Autowired
    MemorySearchRepository searchRepository;

    //@Autowired
    //RedisSearchRepository searchRepository;


    //API 요청
    private JSONArray apiConnection() throws IOException {
        BufferedReader rd;
        HttpURLConnection conn;
        StringBuilder urlBuilder = new StringBuilder("https://openapi.its.go.kr:9443/eventInfo"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("apiKey", "UTF-8") + "=" + URLEncoder.encode("059980d47126472a865a1fced65d1948", "UTF-8")); /*공개키*/
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("all", "UTF-8")); /*도로유형*/
        urlBuilder.append("&" + URLEncoder.encode("eventType","UTF-8") + "=" + URLEncoder.encode("acc", "UTF-8")); /*이벤트유형*/
        urlBuilder.append("&" + URLEncoder.encode("minX","UTF-8") + "=" + URLEncoder.encode("124.3636", "UTF-8")); /*최소경도영역*/
        urlBuilder.append("&" + URLEncoder.encode("maxX","UTF-8") + "=" + URLEncoder.encode("130.9198", "UTF-8")); /*최대경도영역*/
        urlBuilder.append("&" + URLEncoder.encode("minY","UTF-8") + "=" + URLEncoder.encode("33.643", "UTF-8")); /*최소위도영역*/
        urlBuilder.append("&" + URLEncoder.encode("maxY","UTF-8") + "=" + URLEncoder.encode("38.364", "UTF-8")); /*최대위도영역*/
        urlBuilder.append("&" + URLEncoder.encode("getType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*출력타입*/
        URL url = new URL(urlBuilder.toString());
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "text/json;charset=UTF-8");
        System.out.println("Response code: " + conn.getResponseCode());


        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        System.out.println(sb.toString());

        JSONObject jObject = new JSONObject(sb.toString());

        return jObject.getJSONObject("body").getJSONArray("items");
    }

    private AccidentDTO jsonToDTO(JSONObject obj){
        Long accident_id = obj.getLong("linkId");
        String message = obj.getString("message");
        String road_name = obj.getString("roadName");
        Integer road_num = obj.getInt("roadNo");
        String road_direction = obj.getString("roadDrcType");
        Double latitude = obj.getDouble("coordY");
        Double longitude = obj.getDouble("coordX");
        String date_time = obj.getString("startDate");


        return AccidentDTO.builder().accident_id(accident_id).message(message)
                .road_name(road_name).road_num(road_num).road_direction(road_direction)
                .latitude(latitude).longitude(longitude).date_time(date_time).build();
    }


    //단일 사고 조회
    /*
    public AccidentDTO inquireAccident(double user_latitude, double user_longitude){
        Long result = searchRepository.inquire(user_latitude,user_longitude);
        return result != null ? accidentRepository.findById(result).get().makeDTO() : null;
    }


    public synchronized boolean verifyAccident(AccidentDTO item){
        if(!memorySearchRepostiry.duplicateCheck(item.getAccident_id())) {
            memorySearchRepostiry.addSearch(item.getAccident_id(), new Search(item.extractRequired(), 0, item.getLatitude(), item.getLongitude()));
            //accidentRepository.save(item.makeEntity());
            memorySearchRepostiry.addWait(new Wait(item.getAccident_id(), item.getLatitude(), item.getLongitude()));

            return true;
        }

        return false;
    }


    public AccidentDTO SearchAccident(double user_latitude, double user_longitude) throws IOException {
        List<AccidentDTO> result = inquireAccident(user_latitude, user_longitude);

        if(result != null)
            return result;

        if(RequestAccident()){
            return inquireAccident(user_latitude, user_longitude);
        }

        return null;
    }
    */


    //기존 사고 조회
    public List<AccidentDTO> inquireAccident(double user_latitude, double user_longitude){

        ArrayList<AccidentDTO> items = new ArrayList<AccidentDTO>();
        List<Long> ids = searchRepository.inquire(user_latitude,user_longitude);
        for(Long id : ids){
            items.add(accidentRepository.findById(id).get().makeDTO());
        }
        return items;
    }


    //새로운 사고 정보 요청
    public boolean RequestAccident() throws IOException {
        JSONArray items= apiConnection();

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
        if(!searchRepository.duplicateCheck(obj.getLong("linkId"))) {
            AccidentDTO item = jsonToDTO(obj);
            searchRepository.addSearch(item.getAccident_id(), new Search(item.extractRequired(), 0, item.getLatitude(), item.getLongitude()));
            accidentRepository.save(item.makeEntity());
            searchRepository.addWait(new Wait(item.getAccident_id(), item.getLatitude(), item.getLongitude()));

            return true;
        }

        return false;
    }


    //유저 사고 탐색 요청 처리
    public List<AccidentDTO> SearchAccident(double user_latitude, double user_longitude) throws IOException {
        List<AccidentDTO> items = inquireAccident(user_latitude, user_longitude);

        if(items.size() == 0){
            if(RequestAccident())
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


    //매칭 취소 (거절 아님, 수락 이후의 취소)
    public synchronized void cancelMatching(Long accident_id){
        Search target = searchRepository.getSearch(accident_id);

        target.decreaseCurrent();

        if(!searchRepository.presentWait(accident_id)){
            searchRepository.addWait(new Wait(accident_id, target.getLatitude(), target.getLongitude()));
        }
    }
}
