package me.yh.springoauth.oauth;

import lombok.RequiredArgsConstructor;
import me.yh.springoauth.account.Account;
import me.yh.springoauth.account.AccountId;
import me.yh.springoauth.account.repository.AccountRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final AccountRepository accountRepository;
    private final HttpSession httpSession;

    /**
     * @param userRequest 여기에 요청 정보가 담겨있음.
     *        속성 clientRegistration 중요
     */
    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        //loadUser 메소드로 userRequest 의 속성에서 값을꺼내 세팅
        //userRequest.accessToken.scopes uri 로 요청
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //소셜 로그인 서비스 코드를 구분하기 위한 코드 ex) 구글 = google, 깃허브 = github, 네이버 = naver
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        //각 소셜서비스에서 제공하는 유니크 필드 이름, ex) 구글 = sub, 깃허브 = id, 네이버 = response.id
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        //OAuth2UserService.loadUser를 통해 가져온 OAuth2User의 attribute를 담을 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        Account account = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(account));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(account.getRoleKey())),
                attributes.getAttributes(),
                attributes.getUniqueFieldName());
    }

    /**
     * 소셜 로그인 name 이나 이미지가 수정 된경우 업데이트, 없는경우 신규 생성
     */
    private Account saveOrUpdate(OAuthAttributes attributes) {

        return accountRepository.findById(new AccountId(attributes.getId(), attributes.getPlatformType()))
                .map(entity -> entity.changeProfile(attributes.getNickname(),attributes.getPicture()))
                .orElseGet(() -> accountRepository.save(attributes.toEntity()));
    }
}