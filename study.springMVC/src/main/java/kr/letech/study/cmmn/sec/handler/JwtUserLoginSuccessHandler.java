/**
 * 
 */
package kr.letech.study.cmmn.sec.handler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * <pre>
 * 
 * </pre>
 * 
 * < 개정이력 >
 * 
 *  수정일			수정자			수정내용
 *  ------------------------------------------------
 *  2025-09-16		KCY				최초 생성
 */

 public class JwtUserLoginSuccessHandler implements AuthenticationSuccessHandler {

     private final String authServerUrl = "http://localhost:8081/sec-management/auth/users";

     @Override
     public void onAuthenticationSuccess(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Authentication authentication)
             throws IOException, ServletException {

         String username = request.getParameter("userId");
         String password = request.getParameter("userPw");

         Map<String, Object> loginResult = callAuthApi(username, password);

         if (loginResult != null && loginResult.get("token") != null) {
             // roles를 SecurityContext에 반영
             List<String> roles = (List<String>) loginResult.get("roles");
             List<SimpleGrantedAuthority> authorities =
                     roles.stream()
                          .map(SimpleGrantedAuthority::new)
                          .collect(Collectors.toList());

             UsernamePasswordAuthenticationToken authToken =
                     new UsernamePasswordAuthenticationToken(username, null, authorities);

             SecurityContextHolder.getContext().setAuthentication(authToken);

             response.sendRedirect("/");
         } else {
             response.sendRedirect("/cmmn/sec/loginForm?error=true");
         }
     }

     private Map<String, Object> callAuthApi(String username, String password) {
         try {
             URL url = new URL(authServerUrl);
             HttpURLConnection conn = (HttpURLConnection) url.openConnection();
             conn.setRequestMethod("POST");
             conn.setRequestProperty("Content-Type", "application/json");
             conn.setDoOutput(true);

             new ObjectMapper().writeValue(conn.getOutputStream(),
                     Map.of("username", username, "password", password));

             if (conn.getResponseCode() == 200) {
                 return new ObjectMapper().readValue(conn.getInputStream(), Map.class);
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
     }
 }
