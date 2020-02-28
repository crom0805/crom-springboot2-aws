package crom.accel.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
  @RunWith
  테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자(SpringRunner)를 실행시킨다.
  스프링부트 테스트와 JUnit 사이에 연결자 역할을 한다.

  @WebMvcTest
  스프링 어노테이션 중 Web(Spring mvc)에 집중할 수 있는 어노테이션
  선언할 경우 @controller, @ControllerAdvice등을 사용할수 있음
  단, @Service, @Component, @Repository등은 사욜할 수 없음
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {

    /*
      private MockMvc mvc
      웹 API를 테스트할 때 사용. 스프링MVC 테스트의 시작점
      이 클래스를 통해 HTTP GET, POST 등에 대한 API를 테스트할 수 있음
     */
    @Autowired
    private MockMvc mvc;

    @Test
    public void hellO가_리턴된다() throws Exception {
        String hello = "hello";

        /*
          mvc.perform(get("/hello"))
          MockMvc를 통해 /hello 주소로 HTTP GET 요청을 한다. 체이닝이 지원

          .andExpect(status().isOk())
          mvc.perform의 결과를 검증. HTTP Header의 Status를 검증

          .andExpect(content().string(hello))
          mvc.perform의 결과를 검증. 응답 본문의 내용을 검증.
          Controller 에서 "hello"를 리턴하기 때문에 이 값이 맞는지 검증
         */
        mvc.perform(get("/hello")).andExpect(status().isOk()).andExpect(content().string(hello));
    }

    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        /*
          .param
          API테스트할때 사용될 요청 파라미터. 값은 String만 허용됨(숫자/날짜 등의 데이터는 문자열로 변경)

          jsonpath
          JSON응답값을 필드별로 검증할 수 있는 메소드
          $를 기준으로 필드명을 명시
         */
        mvc.perform(get("/hello/dto").param("name", name).param("amount", String.valueOf(amount)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(name)))
        .andExpect(jsonPath("$.amount", is(amount)));
    }
}
