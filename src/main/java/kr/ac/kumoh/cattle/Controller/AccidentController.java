package kr.ac.kumoh.cattle.Controller;

import kr.ac.kumoh.cattle.DTO.AccidentDTO;
import kr.ac.kumoh.cattle.Service.AccidentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/accident")
@Slf4j
public class AccidentController {
    @Autowired
    AccidentService accidentService;

    //단일 반환
    /*
    @GetMapping("/search")
    public AccidentDTO search(@RequestParam double latitude, @RequestParam double longitude) throws IOException {

        AccidentDTO result = accidentService.SearchAccident(latitude, longitude);
        return result != null ? result: AccidentDTO.builder().accident_id(0L).message("no searched accident").build();
    }
    */

    @GetMapping("/search")
    public List<AccidentDTO> search(@RequestParam double latitude, @RequestParam double longitude) throws IOException {
        log.info("latitude: "+latitude);
        log.info("longitude: "+longitude);
        return accidentService.searchAccident(latitude, longitude);
    }

    @GetMapping("/accept")
    public boolean accept(@RequestParam Long accident_id){
        log.info("accident_id"+accident_id);
        return accidentService.tryMatching(accident_id);
    }

    @GetMapping("/cancel")
    public void cancel(@RequestParam Long accident_id){
        accidentService.cancelMatching(accident_id);
    }
}
