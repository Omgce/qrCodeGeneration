//package com.login.ipValidationFilter;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.login.errorDto.ErrorDetails;
//
//@Component
//public class IpValidationFilter extends OncePerRequestFilter {
//
//	private final List<String> allowedIpAddresses = Arrays.asList("0:0:0:0:0:0:0:1");
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//			throws ServletException, IOException {
//		String clientIpAddress = request.getRemoteAddr();
//		if (!isIpAddressAllowed(clientIpAddress)) {
//
//			int customStatusCode = 1005;
//			String errorMessage = "Access denied. Your IP address is not allowed.";
//			ErrorDetails errorDetails = new ErrorDetails(new Date(), customStatusCode, "Bad Request", errorMessage);
//			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//			response.setContentType("application/json");
//			response.getWriter().write(new ObjectMapper().writeValueAsString(errorDetails));
//			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//			return;
//		}
//		chain.doFilter(request, response);
//	}
//
//	private boolean isIpAddressAllowed(String ipAddress) {
//		for (String allowedIp : allowedIpAddresses) {
//			if (ipAddress.matches(allowedIp.replace(".", "\\.").replace("*", ".*"))) {
//				return true;
//			}
//		}
//		return false;
//	}
//}