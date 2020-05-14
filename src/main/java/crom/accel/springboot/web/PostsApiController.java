package crom.accel.springboot.web;

import crom.accel.springboot.service.PostsService;
import crom.accel.springboot.web.dto.PostsResponseDto;
import crom.accel.springboot.web.dto.PostsSaveRequestDto;
import crom.accel.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/*
  @RequiredArgsConstructor
  스프링에서 Bean을 주입받는 방법은 @Autowired, setter, 생성자 생성의 방법이 있는데 가장 권장되는 방법은 생성자생성임
  final로 선언된 모든 필드를 인자값으로 하는 생성자를 롬복의 @RequiredArgsConstructor가 대신 생성해줌
  생성자를 직접 안 쓰고 롬복 어노테이션을 사용하는 이유는 해당 클래스의 의존성 관계가 변경될 때마다 생성자코드를 수정해주지 않아도 됨
 */
@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }
}
