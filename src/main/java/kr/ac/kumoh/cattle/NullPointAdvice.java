package kr.ac.kumoh.cattle;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NullPointAdvice {
    @ExceptionHandler(NullPointerException.class)
    public String NullPoint(){
        return "NULL_POINT_EXCEPTION";
    }
}
