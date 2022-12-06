package kr.ac.kumoh.cattle.Controller;


import kr.ac.kumoh.cattle.DTO.MatchingDTO;
import kr.ac.kumoh.cattle.Entity.Accident;
import kr.ac.kumoh.cattle.Entity.Matching;
import kr.ac.kumoh.cattle.Repository.Data.Distance;
import kr.ac.kumoh.cattle.Repository.Data.Search;
import kr.ac.kumoh.cattle.Repository.MatchingRepository;
import kr.ac.kumoh.cattle.Repository.MemorySearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/match")
public class MatchingController {
    @Autowired
    MatchingRepository matchingRepository;


    @GetMapping("/clear")
    public Boolean clear(@RequestParam String user_id, @RequestParam Long accident_id,
                         @RequestParam double accident_latitude, @RequestParam double accident_longitude,
                         @RequestParam double user_latitude, @RequestParam double user_longitude){

        if(Distance.getDistance(accident_latitude, accident_longitude, user_latitude, user_longitude)<=0.05){
            matchingRepository.save(Matching.builder().user(user_id)
                    .accident(Accident.builder().accident_id(accident_id).build())
                    .date_time(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).build());
            return true;
        }

        return false;
    }

    @GetMapping("/records")
    public List<MatchingDTO> getRecords(@RequestParam String user_id){
        List<MatchingDTO> list = new ArrayList<MatchingDTO>();
        for(Matching item : matchingRepository.findByUser(user_id))
            list.add(item.makeDTO());

        log.info(list.toString());
        return list;
    }
}
