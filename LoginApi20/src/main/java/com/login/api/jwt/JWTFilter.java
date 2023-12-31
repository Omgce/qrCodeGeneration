package com.login.api.jwt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.login.errorDto.ErrorDetails;

import io.jsonwebtoken.ExpiredJwtException;

/**
 * Filters incoming requests and installs a Spring Security principal if a
 * header corresponding to a valid user is found.
 */
public class JWTFilter extends GenericFilterBean {

	private final Logger log = LoggerFactory.getLogger(JWTFilter.class);

	private TokenProvider tokenProvider;

	public JWTFilter(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	/**
	 * Method for filtering JWT Token.
	 *
	 * @param servletRequest  - Http request
	 * @param servletResponse - Http response
	 * @param filterChain     - filter chain
	 * @throws IOException      - Input/Output exception
	 * @throws ServletException - Servlet exception
	 */
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		try {
			HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
			String jwt = resolveToken(httpServletRequest);
			if (StringUtils.hasText(jwt)) {
				if (this.tokenProvider.validateToken(jwt)) {
					Authentication authentication = this.tokenProvider.getAuthentication(jwt);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
			filterChain.doFilter(servletRequest, servletResponse);
		} catch (ExpiredJwtException eje) {
			log.info("Security exception for user {} - {}", eje.getClaims().getSubject(), eje.getMessage());
//			((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);	
			int customStatusCode = 1005;
			String errorMessage = "Access denied. Token expired.";
			ErrorDetails errorDetails = new ErrorDetails(new Date(), customStatusCode, "Bad Request", errorMessage);
			((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			servletResponse.setContentType("application/json");
			servletResponse.getWriter().write(new ObjectMapper().writeValueAsString(errorDetails));
			((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
	}

	/**
	 * Method for resolving token
	 *
	 * @param request - Http request
	 * @return Token string | null
	 */
	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(JWTConfigurer.AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		String jwt = request.getParameter(JWTConfigurer.AUTHORIZATION_TOKEN);
		if (StringUtils.hasText(jwt)) {
			return jwt;
		}
		return null;
	}
}
