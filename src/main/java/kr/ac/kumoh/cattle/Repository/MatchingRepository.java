package kr.ac.kumoh.cattle.Repository;

import kr.ac.kumoh.cattle.DTO.MatchingDTO;
import kr.ac.kumoh.cattle.Entity.Matching;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchingRepository extends JpaRepository<Matching, String> {
    List<Matching> findByUser(String user);

}
