package crom.accel.springboot.domain.posts;

import crom.accel.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
  @Entity
  테이블과 링크될 클래스. 기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍으로 테이블 이름을 매칭
 */
@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    /*
      @Id
      해당 테이블의 PK필드

      @GeneratedValue
      PK생성규칙. 스프링부트 2.0에서는 GenerationType.IDENTITY옵션을 추가해야함 auto_increment가 됨
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
      @Column
      테이블의 컬럼을 나타내며 굳이 선언하지 않아도 해당 클래스의 필드는 모두 컬럼이 됨
      사용하는 이유는 기본값 외에 추가로 필요한 옵션이 있을경우 사용함
     */
    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
