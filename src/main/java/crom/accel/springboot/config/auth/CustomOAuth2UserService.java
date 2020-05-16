package crom.accel.springboot.config.auth;

import crom.accel.springboot.config.auth.dto.OAuthAttributes;
import crom.accel.springboot.config.auth.dto.SessionUser;
import crom.accel.springboot.domain.user.User;
import crom.accel.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /*
           registrationId : 현재 로그인 진행중인 서비스를 구분하는 코드. 현재는 구글만 사용하고 있어서 불필요한 값이지만 이후 네이버 등 다른 서비스가 추가되면 구분하기위해 필요함

           userNameAttributeName
           OAuth2 로그인 진행시 키가되는 필드값.(PK같은 값)
           구글의 경우 기본적으로 코드를 지원하지만, 네이버, 카카오 등은 기본지원하지 않음. 구글의 기본코드는 'sub'

           OAuthAttributes : OAuth2UserService를 통해 가져온 OAuth2User의 attribure를 담은 클래스
           SessionUser : 세션에 사용자 정보를 저장하기 위한 DTO클래스.
         */
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionUser(user));    // 기존의 User클래스를 사용하지 않고 SessionUser를 생성하는 이유는 SessionUser 클래스 파일에서 확인해볼것.

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())), attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    /*
      구글의 사용자 정보가 업데이트 되었을 때를 대비한 기능. 사용자의 이름이나 프로필사진이 변경되면 User 엔티티에도 반영됨.
     */
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail()).map(entity -> entity.update(attributes.getName(), attributes.getPicture())).orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
