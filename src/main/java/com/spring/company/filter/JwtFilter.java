package com.spring.company.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring.company.service.CustomUserDetailsService;
import com.spring.company.util.JwtUtil;

@Component
public class JwtFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		System.out.println("JwtFilter");
		String authorizationHeader = req.getHeader("Authorization");
		String token = "";
		if(authorizationHeader != null){
			token = authorizationHeader.substring(7);
		}
		
		String username = "";
		if(!token.equals("")){
			username = jwtUtil.getUsernameFromToken(token);
			System.out.println("username from token:"+username);
		}
		
		if(!username.equals("")){
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
			
			if(jwtUtil.validateToken(token, userDetails)){
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(req, res);
		
	}

}
