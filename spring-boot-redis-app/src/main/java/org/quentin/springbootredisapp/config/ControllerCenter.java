package org.quentin.springbootredisapp.config;

import org.quentin.springbootredisapp.exception.WrongOperatingException;
import org.quentin.springbootredisapp.vo.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerCenter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerCenter.class);

    @ExceptionHandler(value = WrongOperatingException.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        Assert.notNull(e, "Exception is null!");
        LOGGER.trace("error class = {}", e.getClass());
        ExceptionResponse response = new ExceptionResponse();
        response.setMsg(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    private static class ExceptionResponse extends CommonResponse{
        private String msg;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
