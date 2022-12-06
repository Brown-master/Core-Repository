package kr.ac.kumoh.cattle.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class AccidentInfoControllerTest {
    Logger logger= LoggerFactory.getLogger(AccidentInfoControllerTest.class);
    @Test
    @DisplayName("api_테스트")
    void  apiTest() {
        // given
        JSONArray items=new JSONArray("[{\"coordX\":\"126.5968\",\"coordY\":\"36.8369\",\"eventDetailType\":\"\uc0ac\uace0\",\"lanesBlocked\":\"1\ucc28\ub85c \ucc28\ub2e8\",\"eventType\":\"\uad50\ud1b5\uc0ac\uace0\",\"type\":\"\uace0\uc18d\ub3c4\ub85c\",\"message\":\"(1\ucc28\ub85c)\uc2b9\uc6a9\ucc28\uad00\ub828\uc0ac\uace0\ucc98\ub9ac\uc911\",\"roadName\":\"\uc11c\ud574\uc548\uc120\",\"roadDrcType\":\"\uc885\uc810\",\"linkId\":\"3000464600\",\"roadNo\":\"15\",\"lanesBlockType\":\"\",\"startDate\":\"20221121142631\"},{\"coordX\":\"127.1026\",\"coordY\":\"36.0819\",\"eventDetailType\":\"\uc0ac\uace0\",\"lanesBlocked\":\"\",\"eventType\":\"\uad50\ud1b5\uc0ac\uace0\",\"type\":\"\uace0\uc18d\ub3c4\ub85c\",\"message\":\"(1\ucc28\ub85c)\uc2b9\uc6a9\ucc28\uad00\ub828\uc0ac\uace0\ucc98\ub9ac\uc911\",\"roadName\":\"\ub17c\uc0b0\ucc9c\uc548\uc120\",\"roadDrcType\":\"\uc885\uc810\",\"linkId\":\"\",\"roadNo\":\"25\",\"lanesBlockType\":\"\",\"startDate\":\"20221121144900\"}]");
        // when
        for (int i = 0; i < items.length(); i++) {
            logger.info(items.getJSONObject(i).toString());
        }
        // then
    }
}