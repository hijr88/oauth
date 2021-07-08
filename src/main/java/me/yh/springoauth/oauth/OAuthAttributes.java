package me.yh.springoauth.oauth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.yh.springoauth.account.Account;

import java.util.Map;

@SuppressWarnings("unchecked")
@Getter @Setter
public class OAuthAttributes {
    private final Map<String, Object> attributes;
    private final String id;
    private final String uniqueFieldName;
    private final String platformType;
    private String nickname;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String id, String uniqueFieldName, String platformType, String nickname, String email, String picture) {
        this.attributes = attributes;
        this.id = id;
        this.uniqueFieldName = uniqueFieldName;
        this.platformType = platformType;
        this.nickname = nickname;
        this.email = email;
        this.picture = picture;
    }

    /**
     * @param registrationId        소셜서비스 이름
     * @param userNameAttributeName 유니크 필드 이름
     * @param attributes            소셜로그인에서 제공 해주는 값
     */
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        switch (registrationId) {
            case "google": return ofGoogle(userNameAttributeName, attributes);
            case "github": return ofGitHub(userNameAttributeName, attributes);
            case "naver": return ofNaver(userNameAttributeName, attributes);
            case "kakao": return ofKako(userNameAttributeName, attributes);
            default: throw new IllegalArgumentException("not found condition " + registrationId);
        }
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .uniqueFieldName(userNameAttributeName) //sub
                .platformType("google")
                .id((String) attributes.get(userNameAttributeName))
                .nickname((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .build();
    }

    private static OAuthAttributes ofGitHub(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .uniqueFieldName(userNameAttributeName) //id
                .platformType("github")
                .id(String.valueOf(attributes.get(userNameAttributeName)))
                .nickname((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("avatar_url"))
                .attributes(attributes)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {

        Map<String, Object> response = (Map<String, Object>) attributes.get(userNameAttributeName); //response

        return OAuthAttributes.builder()
                .uniqueFieldName("id")
                .platformType("naver")
                .id((String) response.get("id"))
                .nickname((String) response.get("nickname"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .build();
    }

    private static OAuthAttributes ofKako(String userNameAttributeName, Map<String, Object> attributes) {

        OAuthAttributes kakao = OAuthAttributes.builder()
                .uniqueFieldName(userNameAttributeName) //id
                .platformType("kakao")
                .id(String.valueOf(attributes.get(userNameAttributeName)))
                .attributes(attributes)
                .build();

        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        kakao.setEmail(String.valueOf(account.get("email")));
        kakao.setNickname(String.valueOf(profile.get("nickname")));
        kakao.setPicture(String.valueOf(profile.get("profile_image_url")));

        return kakao;
    }

    public Account toEntity() {
        return new Account(id,"oauth_login", nickname,email,picture,platformType);
    }
}