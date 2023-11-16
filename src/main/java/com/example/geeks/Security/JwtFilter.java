package com.example.geeks.Security;


import com.example.geeks.service.MemberService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final MemberService memberService;
    private final String secretKey;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 헤더에서 토큰정보 가져오기
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 토큰 정보가 없다면
        if (authorization == null) {
            //log.info("authentication is null!", authorization);
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 정보가 잘못됨
        try {
            if (!authorization.startsWith("Bearer ") || authorization.split(" ")[1].equals("undefined")) {
                filterChain.doFilter(request, response);
                return;
            } else {
            }
        } catch (MalformedJwtException e) {
            System.out.println("토큰 값이 없거나 잘못되었습니다");
            response.getWriter().write("토큰 값이 없거나 잘못되었습니다");
            e.printStackTrace();
        }

        // Token 꺼내기 split은 저 문자 기준으로 분리하고, 그 중 첫번째 인덱스 값을 가져가기
        String token = authorization.split(" ")[1];
        long userId;

        try {
            Util.isExpired(token, secretKey);

            // Userid Token에서 꺼내기
            userId = Util.getUserId(token, secretKey);

            // 권한 부여
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority("USER")));

            // Detail을 넣어줌
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);

        }  catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            response.getWriter().write("Token이 만료되었습니다.");
            log.info("Token이 만료되었습니다..", authorization);
        }
    }
}