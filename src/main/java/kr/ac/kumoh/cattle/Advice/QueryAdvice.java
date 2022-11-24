package kr.ac.kumoh.cattle.Advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class QueryAdvice {
    private Logger logger= LoggerFactory.getLogger(QueryAdvice.class);
    @ExceptionHandler({NullPointerException.class,NoSuchElementException.class})
    public String QueryNotFoundedException(){
        logger.error("ELEMENT_NOT_FOUNDED!!");
        return "ELEMENT_NOT_FOUNDED";
    }
}
