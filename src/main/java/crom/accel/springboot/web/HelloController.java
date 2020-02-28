package crom.accel.springboot.web;

import crom.accel.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
  @RestController
  컨트롤러를 JSON으로 반환하는 컨트롤러로 만들어준다.
  ASIS에는 @ResponseBody를 각 메소드마다 선언했었던 것을 클래스에 @RestController 한번만 사용하면 됨.
 */
@RestController
public class HelloController {

    /*
      @GetMapping
      HTTP Method인 Get의 요청을 받을수 있는 API를 만든다.
      ASIS에는 @RequestMapping(method = RequestMethod.GET)으로 사용됐었음
     */
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam int amount) {
        return new HelloResponseDto(name, amount);
    }
}
