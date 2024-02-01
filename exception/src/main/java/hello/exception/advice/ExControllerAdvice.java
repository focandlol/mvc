package hello.exception.advice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ControllerAdvice
 * 여러 controller에서 @ExceptionHandler 공통 사용 가능하도록 함
 * 대상 컨트롤러 지정 가능
 * 1. @ControllerAdvice(annotations = RestController.class) 해당 어노테이션 있는 컨트롤러만 지정
 * 2. @ControllerAdvice(basePackages = "org.exception.api) 해당 패키지와 그 하위에 있는 컨트롤러 대상
 * 3. @ControllerAdvice(assignableTypes = ApiExceptionController.class) 컨트롤러 직접 지정
 */
@Slf4j
@RestControllerAdvice(basePackages = "hello.exception.api")
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e){
        log.error("exceptionHandler ex",e);
        return new ErrorResult("BAD",e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e){
        log.error("exceptionHandler ex",e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult,HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e){
        log.error("exceptionHandler ex",e);
        return new ErrorResult("EX","내부 오류");
    }
}
