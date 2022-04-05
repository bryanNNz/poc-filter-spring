package com.filter.config;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.ServletRequestPathUtils;

import com.filter.annotation.ActivateMobileFilterPolicy;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MobileFilterPolicy extends OncePerRequestFilter {

	private static final Logger LOG = LoggerFactory.getLogger(MobileFilterPolicy.class);

	private RequestMappingHandlerMapping handlerMapping;

	public MobileFilterPolicy(RequestMappingHandlerMapping handlerMapping) {
		this.handlerMapping = handlerMapping;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		filterChain.doFilter(request, response);

	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		Method method = getOriginMethod(request);
		return !(method.isAnnotationPresent(ActivateMobileFilterPolicy.class));
	}

	private Method getOriginMethod(HttpServletRequest request) {

		try {
			
			if (!ServletRequestPathUtils.hasParsedRequestPath(request)) { // SPRING BOOT > 2.6.X
				ServletRequestPathUtils.parseAndCache(request);
			}

			HandlerMethod handlerMethod = (HandlerMethod) handlerMapping.getHandler(request).getHandler();

			return handlerMethod.getMethod();
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new InternalError(e.getMessage());
		}

	}

}
