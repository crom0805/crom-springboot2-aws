package crom.accel.springboot.config.auth.dto;

import crom.accel.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

/*
   SessionUser 에서는 인증된 사용자의 정보만 필요함. 그외에 필요한 정보들은 없으니 name, email, picture 만 필드로 선언
   만약에 User 클래스를 그대로 사용했다면 직렬화를 구현관련 에러가 발생함.
   User클래스는 엔티티이기 때문에 다른 엔티티와 관계가 형성될지 모름.
   예를 들어 @OneToMany, @ManyToMany 등 자식엔티티를 가지고있다면 직렬화대상에 자식들까지 포함되니 성능이슈, 부하이슈가 발생할 확률이 높음.
   그래서 직렬화 기능을 가진 세션Dto를 별도로 생성함
 */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
