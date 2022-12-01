package kr.ac.kumoh.cattle.Service;

import kr.ac.kumoh.cattle.DTO.AccidentDTO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ApiConnection {
    static public JSONArray connect() throws IOException {
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
