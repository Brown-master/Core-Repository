package kr.ac.kumoh.cattle.Controller;


import kr.ac.kumoh.cattle.DTO.AccidentDTO;
import kr.ac.kumoh.cattle.Service.AccidentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/accident")
@Slf4j
public class AccidentController {
    @Autowired
    AccidentService accidentService;

    @GetMapping("/search")
    public AccidentDTO search(@RequestParam double latitude, @RequestParam double longitude) throws IOException, NoSuchFieldException, IllegalAccessException {

        AccidentDTO result = accidentService.SearchAccident(latitude, longitude);
        log.info(result.toString());
        return result != null ? result : AccidentDTO.builder().accident_id(0L).message("no searched accident").build();
    }

    @GetMapping("")
    public String showAll() throws IOException {
        return accidentService.apiConnection().toString();
    }

    @PostMapping("/accept")
    public boolean accept(@RequestParam Long accident_id) {
        return accidentService.tryMatching(accident_id);
    }

    @PostMapping("/cancel")
    public void cancel(@RequestParam Long accident_id) {
        accidentService.cancelMatching(accident_id);
    }
}
