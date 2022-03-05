package com.example.oauth2.security;


import com.example.oauth2.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer; import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.oauth2.security.SocialType.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private CustomOAuth2UserService userDetailService;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                    .antMatchers("/", "/oauth2/**", "/login/**", "/css/**",
                            "/images/**", "/js/**", "/console/**", "/favicon.ico/**")
                    .permitAll()
                    .antMatchers("/google").hasAuthority(GOOGLE.getRoleType())
                    .antMatchers("/kakao").hasAuthority(KAKAO.getRoleType())
                    .antMatchers("/naver").hasAuthority(NAVER.getRoleType())
                    .anyRequest().authenticated()
                .and()
                    .oauth2Login()
                    .userInfoEndpoint().userService(new CustomOAuth2UserService())
                .and()
                    .defaultSuccessUrl("/loginSuccess")
                    .failureUrl("/loginFailure")
                .and()
                   .logout()
                        .logoutSuccessUrl("/")

                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));
    }

//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("clientId") // 클라이언트 아이디
//                .secret("{noop}secretKey")
//                .authorizedGrantTypes("authorization_code","password", "refresh_token") // 가능한 토큰 발행 타입
//                .scopes("read", "write") // 가능한 접근 범위
//                .accessTokenValiditySeconds(60) // 토큰 유효 시간 : 1분
//                .refreshTokenValiditySeconds(60*60) // 토큰 유효 시간 : 1시간
//                .redirectUris("http://localhost:8080/callback") // 가능한 redirect uri
//                .autoApprove(true);
//    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(
            OAuth2ClientProperties oAuth2ClientProperties,
                @Value("${custom.oauth2.kakao.client-id}") String kakaoClientId,
                @Value("${custom.oauth2.naver.client-id}") String naverClientId,
                @Value("${custom.oauth2.naver.client-secret}") String naverClientSecret){

            List<ClientRegistration> registrations = oAuth2ClientProperties
                    .getRegistration().keySet().stream()
                    .map(client -> getRegistration(oAuth2ClientProperties, client))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            registrations.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao")
                    .clientId(kakaoClientId)
                    .jwkSetUri("temp")
                    .build());

        registrations.add(CustomOAuth2Provider.NAVER.getBuilder("naver")
                .clientId(naverClientId)
                .clientSecret(naverClientSecret)
                .jwkSetUri("temp")
                .build());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String client) {
        if("google".equals(client)) {
            OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("google");
            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
                    .clientId(registration.getClientId())
                    .clientSecret(registration.getClientSecret())
                    .scope("email", "profile")
                    .build();
        }


        return null;
    }
}
