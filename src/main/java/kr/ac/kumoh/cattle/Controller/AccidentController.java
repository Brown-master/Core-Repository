package kr.ac.kumoh.cattle.Controller;

import kr.ac.kumoh.cattle.DTO.AccidentDTO;
import kr.ac.kumoh.cattle.Service.AccidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/accident")
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
        return accidentService.SearchAccident(latitude, longitude);
    }

    @PostMapping("/accept")
    public boolean accept(@RequestParam Long accident_id){
        return accidentService.tryMatching(accident_id);
    }

    @PostMapping("/cancel")
    public void cancel(@RequestParam Long accident_id){
        accidentService.cancelMatching(accident_id);
    }
}
