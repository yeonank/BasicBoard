package com.hab_day.springinit.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hab_day.springinit.config.auth.dto.OAuthAttributes;
import com.hab_day.springinit.config.auth.dto.SessionUser;
import com.hab_day.springinit.domain.user.User;
import com.hab_day.springinit.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("load user");
        OAuth2UserService<OAuth2UserRequest, OAuth2User>
                delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);//엑세스 토큰을 이용해 서드파티 서버로부터 사용자 정보 받아옴

        String registrationId = userRequest.getClientRegistration().getRegistrationId();//로그인 중인 서비스가 구글인지 네이버인지 등 확인
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();//로그인 진행시 키가 되는 필드값.

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        //OAuth2User의 attribbute 담을 클래스
        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));//세션에 사용자 정보를 저장하기 위한 dto

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes){
        System.out.println("save or update");
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());//entity 생성
        return userRepository.save(user);
    }


    /*public String getKakaoAccessToken (String code) throws IOException {
        String host = "https://kauth.kakao.com/oauth/token";
        URL url = new URL(host);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String token = "";
        try{
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true); // 데이터 기록 알려주기

            //post 요청 파라미터 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=c7807b7786b3066b37df59fbd4631d8e");
            sb.append("&redirect_uri=http://localhost:8080/oauth/kakao");
            sb.append("&code=" + code);

            bw.write(sb.toString());
            bw.flush();
            //결과 확인
            int responseCode = urlConnection.getResponseCode();
            System.out.println("responseCode get token= " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("result = " + result);

            // json parsing
            JsonParser parser = new JsonParser();
            JsonElement elem = parser.parse(result);

            String access_token = elem.getAsJsonObject().get("access_token").getAsString();
            String refresh_token = elem.getAsJsonObject().get("refresh_token").getAsString();
            System.out.println("refresh_token = " + refresh_token);
            System.out.println("access_token = " + access_token);

            token = access_token;

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }

    public Map<String, Object> getKakaoUserInfo(String token) throws IOException {
        String host = "https://kapi.kakao.com/v2/user/me";
        Map<String, Object> result = new HashMap<>();
        try {
            URL url = new URL(host);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            urlConnection.setRequestMethod("POST");

            int responseCode = urlConnection.getResponseCode();
            System.out.println("responseCode getuserinfo= " + responseCode);


            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            String res = "";
            while ((line = br.readLine()) != null) {
                res += line;
            }

            System.out.println("userInfo res = " + res);


            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(res);

            String id = element.getAsJsonObject().get("id").toString();
            JsonElement kakao_account = element.getAsJsonObject().get("kakao_account");
            //JsonElement properties = element.getAsJsonObject().get("properties");
            //String nickname = properties.getAsJsonObject().get("nickname").toString();
            String email = kakao_account.getAsJsonObject().get("email").toString();
            //String age_range = kakao_account.getAsJsonObject().get("age_range").toString();

            result.put("id", id);
            //result.put("nickname", nickname);
            //result.put("age_range", age_range);
            result.put("email", email);

            br.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void kakaoLogOut(String token) throws IOException{
        String host = "https://kapi.kakao.com/v2/user/me";
        try{
            URL url = new URL(host);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            urlConnection.setRequestMethod("POST");

            int responseCode = urlConnection.getResponseCode();
            System.out.println("responseCode logout= " + responseCode);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void kakoLeaveService(String token) throws IOException{
        String host = "https://kapi.kakao.com/v1/user/unlink";
        try{
            URL url = new URL(host);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            urlConnection.setRequestMethod("POST");

            int responseCode = urlConnection.getResponseCode();
            System.out.println("responseCode leave= " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            String res = "";
            while ((line = br.readLine()) != null) {
                res += line;
            }

            System.out.println("leave res = " + res);

        } catch (IOException e){
            e.printStackTrace();
        }
    }*/

}
