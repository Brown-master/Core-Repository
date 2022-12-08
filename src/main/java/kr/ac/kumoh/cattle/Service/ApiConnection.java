package kr.ac.kumoh.cattle.Service;

import kr.ac.kumoh.cattle.DTO.AccidentDTO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Slf4j
public class ApiConnection {
    @Value("${value.request_value.api_key}") private static String apiKey;
    @Value("${value.request_value.url}") private static String url;
    @Value("${value.request_value.event_type}") private static String eventType;
    @Value("${value.request_value.get_type}") private static String getType;
    @Value("${value.request_value.max_x}") private static String maxX;
    @Value("${value.request_value.max_y}") private static String maxY;
    @Value("${value.request_value.min_x}") private static String minX;
    @Value("${value.request_value.min_y}") private static String minY;
    @Value("${value.request_value.type}") private static String type;
    static public JSONArray connect() throws IOException {
        BufferedReader rd;
        HttpURLConnection conn;
        StringBuilder urlBuilder = new StringBuilder(url); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("apiKey", "UTF-8") + "=" + URLEncoder.encode(apiKey, "UTF-8")); /*공개키*/
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")); /*도로유형*/
        urlBuilder.append("&" + URLEncoder.encode("eventType","UTF-8") + "=" + URLEncoder.encode(eventType, "UTF-8")); /*이벤트유형*/
        urlBuilder.append("&" + URLEncoder.encode("minX","UTF-8") + "=" + URLEncoder.encode(minY, "UTF-8")); /*최소경도영역*/
        urlBuilder.append("&" + URLEncoder.encode("maxX","UTF-8") + "=" + URLEncoder.encode(maxY, "UTF-8")); /*최대경도영역*/
        urlBuilder.append("&" + URLEncoder.encode("minY","UTF-8") + "=" + URLEncoder.encode(minX, "UTF-8")); /*최소위도영역*/
        urlBuilder.append("&" + URLEncoder.encode("maxY","UTF-8") + "=" + URLEncoder.encode(maxX, "UTF-8")); /*최대위도영역*/
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

//        JSONObject jObject = new JSONObject(sb.toString());
        JSONArray array=new JSONArray("[{\"coordX\":\"126.6745\",\"coordY\":\"35.4678\",\"eventDetailType\":\"사고\",\"lanesBlocked\":\"2차로 차단\",\"endDate\":\"\",\"eventType\":\"교통사고\",\"type\":\"고속도로\",\"message\":\"(2차로,갓길)승용차관련사고처리중\",\"roadName\":\"서해안선\",\"roadDrcType\":\"종점\",\"linkId\":\"3180020702\",\"roadNo\":\"15\",\"lanesBlockType\":\"\",\"startDate\":\"20221204003825\"},\n" +
                "{\"coordX\":\"128.3923986\",\"coordY\":\"36.1457765\",\"eventDetailType\":\"사고\",\"lanesBlocked\":\"2차로 차단\",\"endDate\":\"\",\"eventType\":\"교통사고\",\"type\":\"고속도로\",\"message\":\"(1,2차로)승용차관련사고처리중\",\"roadName\":\"경부선\",\"roadDrcType\":\"기점\",\"linkId\":\"2760435500\",\"roadNo\":\"1\",\"lanesBlockType\":\"\",\"startDate\":\"20221204001230\"},\n" +
                "{\"coordX\":\"128.235\",\"coordY\":\"36.4005\",\"eventDetailType\":\"사고\",\"lanesBlocked\":\"2차로 차단\",\"endDate\":\"\",\"eventType\":\"교통사고\",\"type\":\"고속도로\",\"message\":\"(2차로)승용차관련사고처리중\",\"roadName\":\"중부내륙선\",\"roadDrcType\":\"기점\",\"linkId\":\"3580008304\",\"roadNo\":\"45\",\"lanesBlockType\":\"\",\"startDate\":\"20221203235227\"},\n" +
                "{\"coordX\":\"126.5694\",\"coordY\":\"36.2668\",\"eventDetailType\":\"사고\",\"lanesBlocked\":\"2차로 차단\",\"endDate\":\"\",\"eventType\":\"교통사고\",\"type\":\"고속도로\",\"message\":\"(2차로,갓길)승용차관련사고처리중\",\"roadName\":\"서해안선\",\"roadDrcType\":\"기점\",\"linkId\":\"2870007301\",\"roadNo\":\"15\",\"lanesBlockType\":\"\",\"startDate\":\"20221204002653\"}]");
        return array;
    }

    static public AccidentDTO jsonToDTO(JSONObject obj){
        //Long link_id = obj.getLong("linkId");
        String message = obj.getString("message");
        String road_name = obj.getString("roadName");
        Integer road_num = obj.getInt("roadNo");
        String road_direction = obj.getString("roadDrcType");
        Double latitude = obj.getDouble("coordY");
        Double longitude = obj.getDouble("coordX");
        String date_time = obj.getString("startDate");
        Long accident_id = genObjectId(date_time, latitude, longitude);

        date_time = date_time.substring(0,4) + "-" + date_time.substring(4,6) + "-" + date_time.substring(6,8)
                + " " +date_time.substring(8,10) + ":" + date_time.substring(10,12) + ":" + date_time.substring(12,14);

        return AccidentDTO.builder().accident_id(accident_id).message(message)
                .road_name(road_name).road_num(road_num).road_direction(road_direction)
                .latitude(latitude).longitude(longitude).date_time(date_time).build();
    }
    static public Long genObjectId(String date_time, Double latitude, Double longitude) {
        String time=date_time.substring(4,10);
        String posY= String.valueOf((int) Math.round(latitude*1000));
        String posX= String.valueOf((int) Math.round(longitude*1000));
        return Long.valueOf(time+posY+posX)/1024;
    }
}
