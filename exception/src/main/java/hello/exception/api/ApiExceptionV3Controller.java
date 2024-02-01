package hello.exception.api;

import hello.exception.exception.UserException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @ExceptionHandler 컨트롤러 내부에서 발생한 예외를 잡고 처리
 * TypeMisMatchException도 처리 가능
 */
@Slf4j
@RestController
public class ApiExceptionV3Controller {

    /**
     * @ExceptionHandler 처리 부분
     * @ControllerAdvice에서 전부 처리 가능
     */
    /*ResponseStatus(HttpStatus.BAD_REQUEST)
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
    }*/

    @GetMapping("/api3/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
        if(id.equals("ex")){
            throw new RuntimeException("잘못된 사용자");
        }
        if(id.equals("bad")){
            throw new IllegalArgumentException("잘못된 입력 값");
            //response.sendError(404,"404오류!");
        }
        if(id.equals("user-ex")){
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello " + id);
    }

    @GetMapping("/api3/default-handler-ex")
    public String defaultException(@RequestParam Integer data){
        return "ok";
    }
    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String memberId;
        private String name;
    }
}
