package org.choongang.configs;

import jakarta.servlet.http.HttpServletResponse;
import org.choongang.member.service.LoginFailurdHandler;
import org.choongang.member.service.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    /**
     * 설정 무력화
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /* 인증설정 S - 로그인, 로그아웃 */
        http.formLogin(f -> {
            f.loginPage("/member/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler(new LoginSuccessHandler())
                    .failureHandler(new LoginFailurdHandler());
        });

        http.logout(c -> {
            c.logoutRequestMatcher(new AntPathRequestMatcher(".member/logout"))
                    .logoutSuccessUrl("/member/login");
        });
        /* 인증설정 E - 로그인, 로그아웃 */

        /* 인가설정 S - 접근 통제 */
        // hasAuthority(..), hasAnyAuthority(..), hasRole, hasAnyRole
        // ROLE_롤명칭
        // hasAuthority('ADMIN')
        // ROLE_ADMIN -> hasAuthority('ROLE_ADMIN')
        // hasRole('ADMIN')
        http.authorizeHttpRequests(c -> {
            c.requestMatchers("/mypage/**").authenticated()  // 회원전용
                    .requestMatchers("/admin.**").hasAnyAuthority("ADMIN", "MANAGER")
                    .anyRequest().permitAll();  // 그외 모든 페이지는 모두 접근 가능
        });

        http.exceptionHandling(c -> {
//           c.authenticationEntryPoint(new AuthenticationEntryPoint() {
//                @Override
//                public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//            String URL = req.getRequestURI();
//            if(URL.indexOf("/admin") != -1) {  // 관리자 페이지
//                res.sendError(HttpServletResponse.SC_UNAUTHORIZED);  //401
//            } else {  // 회원 전용 페이지
//                res.sendRedirect(req.getContextPath()+"/member/login");
//            }
//
//                }
             c.authenticationEntryPoint(( req,  res,  e) -> {
                 String URL = req.getRequestURI();
                 if(URL.indexOf("/admin") != -1) {  // 관리자 페이지
                     res.sendError(HttpServletResponse.SC_UNAUTHORIZED);  //401
                 } else {  // 회원 전용 페이지
                     res.sendRedirect(req.getContextPath()+"/member/login");
                 }
            });
        });
        /* 인가설정 E - 접근 통제 */

        return http.build();
    }

    /**
     * 비밀번호 해시화
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
