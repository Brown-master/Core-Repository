package kr.ac.kumoh.cattle.Controller;


import kr.ac.kumoh.cattle.DTO.MatchingDTO;
import kr.ac.kumoh.cattle.Entity.Accident;
import kr.ac.kumoh.cattle.Entity.Matching;
import kr.ac.kumoh.cattle.Repository.MatchingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchingController {
    @Autowired
    MatchingRepository matchingRepository;

    @PostMapping("/clear")
    public void clear(@RequestParam Long user_id, @RequestParam Long accident_id){
        matchingRepository.save(Matching.builder().user(user_id)
                .accident(Accident.builder().accident_id(accident_id).build())
                .date_time(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build());
    }

    @GetMapping("/records")
    public List<MatchingDTO> getRecords(@RequestParam Long user_id){
        List<MatchingDTO> list = new ArrayList<MatchingDTO>();
        for(Matching item : matchingRepository.findByUser(user_id))
            list.add(item.makeDTO());

        return list;
    }
}
