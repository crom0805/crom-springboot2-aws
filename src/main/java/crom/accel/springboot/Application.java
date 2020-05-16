package crom.accel.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/*
  @SpringBootApplication
  스프링부트의 자동설정, 스프링Bean 읽기와 생성을 모두 자동으로 생성
  @SpringBootApplication이 있는 위치부터 설정을 읽어가기 때문에 이 클래스는 항상 프로젝트의 최상단에 위치해야만 함.
 */
//@EnableJpaAuditing // Test 수행시 에러발생으로 인한 주석처리.
/*
   @WebMvcTest에서 스캔하게 되는데 @EnableJpaAuditing을 사용하기 위해서는 최소 하나의 @Entity 클래스가 필요함.
   테스트는 당연히 없음. @EnableJpaAuditing은 config패키지로 분리
*/
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        /*
          SpringApplication.run으로 인해 내장 WAS를 실행
         */
        SpringApplication.run(Application.class, args);
    }
}
