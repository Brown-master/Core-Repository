package kr.ac.kumoh.cattle.Repository;

import kr.ac.kumoh.cattle.Entity.Accident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccidentRepository extends JpaRepository<Accident, Long> {
}
