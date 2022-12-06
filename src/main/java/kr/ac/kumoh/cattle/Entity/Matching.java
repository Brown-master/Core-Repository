package kr.ac.kumoh.cattle.Entity;

import kr.ac.kumoh.cattle.DTO.MatchingDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Getter
@Table(name = "matching")
public class Matching {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "matching_id", nullable = false)
    private Long matching_id;

    @Column(name = "user_id", nullable = false)
    private String user;

    @Column(name = "date_time", nullable = false)
    private String date_time;

    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "accident_id", nullable = false)
    private Accident accident;

    @Builder
    public Matching(String  user, Accident accident, String date_time){
        this.user = user;
        this.accident = accident;
        this.date_time = date_time;
    }

    public MatchingDTO makeDTO(){
        return MatchingDTO.builder().matching_id(this.getId()).
                user_id(this.getUser_id()).accident(this.getAccident().makeDTO())
                .date_time(this.getDate_time()).build();
    }

    public Long getId() {
        return matching_id;
    }

    public String getUser_id() {
        return user;
    }

    public Accident getAccident() {
        return accident;
    }

    public String getDateTime() {
        return date_time;
    }

}