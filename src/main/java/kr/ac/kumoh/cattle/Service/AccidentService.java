package kr.ac.kumoh.cattle.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kr.ac.kumoh.cattle.DTO.AccidentDTO;
import kr.ac.kumoh.cattle.Repository.AccidentRepository;
import kr.ac.kumoh.cattle.Repository.Data.Search;
import kr.ac.kumoh.cattle.Repository.Data.Wait;
import kr.ac.kumoh.cattle.Repository.MemorySearchRepostiry;
import org.apache.catalina.mapper.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AccidentService {
    @Autowired
    AccidentRepository accidentRepository;
    @Autowired
    MemorySearchRepostiry memorySearchRepostiry;

    Logger logger=LoggerFactory.getLogger(AccidentService.class);
    @Value("${value.request_value.api_key}") private String apiKey;
    @Value("${value.request_value.url}") private String url;
    @Value("${value.request_value.event_type}") private String eventType;
    @Value("${value.request_value.get_type}") private String getType;
    @Value("${value.request_value.max_x}") private String maxX;
    @Value("${value.request_value.max_y}") private String maxY;
    @Value("${value.request_value.min_x}") private String minX;
    @Value("${value.request_value.min_y}") private String minY;
    @Value("${value.request_value.type}") private String type;

    //기존 사고 조회
//    private AccidentDTO inquireAccident(double user_latitude, double user_longitude){
//        Long result = memorySearchRepostiry.inquire(user_latitude,user_longitude);
//            return result != null ? accidentRepository.findById(result).get().makeDTO() : null;
//    }

    //TODO: API 요청
//    private List<AccidentDTO> responseItems() throws IOException {
//        String itemsBuff=apiConnection();
//        System.out.println(itemsBuff);
//        return null;
//    }

    //중복사고 검증 후 정보 추가
//    private synchronized boolean verifyAccident(AccidentDTO item){
//        if(!memorySearchRepostiry.duplicateCheck(item.getAccident_id())) {
//            memorySearchRepostiry.addSearch(item.getAccident_id(),
//                    new Search(item.extractRequired(), 0, item.getLatitude(), item.getLongitude()));
//            accidentRepository.save(item.makeEntity());
//            memorySearchRepostiry.addWait(new Wait(item.getAccident_id(), item.getLatitude(), item.getLongitude()));
//
//            return true;
//        }
//
//        return false;
//    }

    //새로운 사고 정보 요청
//    private boolean requestAccident() throws IOException {
//        List<AccidentDTO> items=new ArrayList<>();
//        //TODO: DTO를 반환하도록 items를 변경
//        // List<AccidentDTO> items= responseItems();
//        boolean result = false;
//
//        for(AccidentDTO item:items){
//            if(!result && verifyAccident(item))
//                result = true;
//        }
//
//        return result;
//    }


    //유저 사고 탐색 요청 처리
//    public AccidentDTO searchAccident(double user_latitude, double user_longitude) throws IOException {
//        AccidentDTO result = inquireAccident(user_latitude, user_longitude);
//
//        if(result != null)
//            return result;
//
//        if(requestAccident()){
//            return inquireAccident(user_latitude, user_longitude);
//        }
//
//        return null;
//    }

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
    public String  apiConnection() throws IOException {
        //TODO: List<AccidentDTO> 반환
        BufferedReader rd;
        HttpURLConnection conn;
        StringBuilder urlBuilder = new StringBuilder(url); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("apiKey", "UTF-8") + "=" + URLEncoder.encode(apiKey, "UTF-8")); /*공개키*/
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")); /*도로유형*/
        urlBuilder.append("&" + URLEncoder.encode("eventType","UTF-8") + "=" + URLEncoder.encode(eventType, "UTF-8")); /*이벤트유형*/
        urlBuilder.append("&" + URLEncoder.encode("minX","UTF-8") + "=" + URLEncoder.encode(minX, "UTF-8")); /*최소경도영역*/
        urlBuilder.append("&" + URLEncoder.encode("maxX","UTF-8") + "=" + URLEncoder.encode(maxX, "UTF-8")); /*최대경도영역*/
        urlBuilder.append("&" + URLEncoder.encode("minY","UTF-8") + "=" + URLEncoder.encode(minY, "UTF-8")); /*최소위도영역*/
        urlBuilder.append("&" + URLEncoder.encode("maxY","UTF-8") + "=" + URLEncoder.encode(maxY, "UTF-8")); /*최대위도영역*/
        urlBuilder.append("&" + URLEncoder.encode("getType","UTF-8") + "=" + URLEncoder.encode(getType, "UTF-8")); /*출력타입*/
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

        return sb.toString();
    }

    public List<AccidentDTO> jsonArrayToDTOList(String items) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<AccidentDTO> dtos = new ObjectMapper().readValue(items, new TypeReference<List<AccidentDTO>>() {});
        return dtos;
    }
}
