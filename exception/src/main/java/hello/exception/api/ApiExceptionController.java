package hello.exception.api;

import hello.exception.exception.BadRequestException;
import hello.exception.exception.UserException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@Slf4j
public class ApiExceptionController {

    @GetMapping("/api/members/{id}")
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

    /**
     * @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad")
     * @responseStatus 어노테이션 사용
     */
    @GetMapping("/api/response-status-ex1")
    public String responseStatusEx1(){
        throw new BadRequestException();
    }

    /**
     * 어노테이션은 외부 라이브러리 등에서 사용 불가
     * 대신 ResponseStatusException 사용
     */
    @GetMapping("/api/response-status-ex2")
    public String responseStatusEx2(){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"error.bad",new IllegalArgumentException());
    }

    @ResponseBody
    @GetMapping("/api/default-handler-ex")
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
