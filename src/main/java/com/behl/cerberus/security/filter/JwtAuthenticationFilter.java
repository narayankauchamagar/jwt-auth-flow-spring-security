package com.behl.cerberus.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.behl.cerberus.security.CustomUserDetailService;
import com.behl.cerberus.security.utility.JwtUtility;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtility jwtUtils;
	private final CustomUserDetailService customUserDetialService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final var authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader != null) {
			if (authorizationHeader.startsWith("Bearer ")) {
				final var token = authorizationHeader.substring(7);
				final var userId = jwtUtils.extractUserId(token);

				if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

					final UserDetails userDetails = customUserDetialService.loadUserByUsername(userId.toString());

					if (jwtUtils.validateToken(token, userDetails)) {
						UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
								userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
						usernamePasswordAuthenticationToken
								.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					}
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}