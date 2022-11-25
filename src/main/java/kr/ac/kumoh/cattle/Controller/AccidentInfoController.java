package kr.ac.kumoh.cattle.Controller;

import kr.ac.kumoh.cattle.DTO.AccidentDTO;
import kr.ac.kumoh.cattle.Service.AccidentService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.net.URL;
import java.util.*;

/*
@RestController
@RequestMapping("/accident")
@CrossOrigin(origins = "http://localhost:5000")
public class AccidentInfoController {
    Logger logger= LoggerFactory.getLogger(AccidentInfoController.class);


    static private List<JSONObject> itemsArchive=new ArrayList<>();

    @Autowired
    AccidentService accidentService;

    @GetMapping
    public String getAccident() throws IOException {
        String connection=accidentService.apiConnection();
        JSONObject responseJson = new JSONObject(connection);
        JSONArray items = responseJson.getJSONObject("body").getJSONArray("items");

        for (int i = 0; i < items.length(); i++) {
            items.getJSONObject(i).remove("endDate");
        }


        List<AccidentDTO> dtos=new ArrayList<>(accidentService.jsonArrayToDTOList(items.toString()));
        for (int i = 0; i < dtos.size(); i++) {
            logger.info("coordX: "+dtos.get(i).getCoordX());
            logger.info("coordY: "+dtos.get(i).getCoordY());
            logger.info("eventDetailType: "+dtos.get(i).getEventDetailType());
            logger.info("lanesBlocked: "+dtos.get(i).getLanesBlocked());
            logger.info("eventType: "+dtos.get(i).getEventType());
            logger.info("type: "+dtos.get(i).getType());
            logger.info("message: "+dtos.get(i).getMessage());
            logger.info("roadName: "+dtos.get(i).getRoadName());
            logger.info("roadDrcType: "+dtos.get(i).getRoadDrcType());
            logger.info("linkId: "+dtos.get(i).getLinkId());
            logger.info("roadNo: "+dtos.get(i).getRoadNo());
            logger.info("lanesBlockType: "+dtos.get(i).getLanesBlockType());
            logger.info("startDate: "+dtos.get(i).getStartDate());
        }

        return items.toString();
    }



}
*/
