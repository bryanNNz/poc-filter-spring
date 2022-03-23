package com.filter.config;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.ServletRequestPathUtils;

import com.filter.annotation.ActivateMobileFilterPolicy;

@Component
@Order(1)
public class MobileFilterPolicy implements Filter {

	private static final Logger LOG = LoggerFactory.getLogger(MobileFilterPolicy.class);

	RequestMappingHandlerMapping handlerMapping;

	public MobileFilterPolicy(RequestMappingHandlerMapping handlerMapping) {
		this.handlerMapping = handlerMapping;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {	
		LOG.info("INIT FILTER");

		if (hasMobileFilterPolicyAnnotation(request)) {// SE TRUE CHAMA O PROCESSAMENTO ESPECIFICO E LIBERA A REQUEST
			LOG.info("FILTER WITH ANNOTATION");
			chain.doFilter(request, response);
		} else {// SE FALSE LIBERA A REQUEST SEM ALTERACOES
			LOG.info("FILTER WITHOUT ANNOTATION");
			chain.doFilter(request, response);
		}

		LOG.info("FINISH FILTER");

	}

	public boolean hasMobileFilterPolicyAnnotation(ServletRequest request) {

		try {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;

			if (!ServletRequestPathUtils.hasParsedRequestPath(httpServletRequest)) {
				ServletRequestPathUtils.parseAndCache(httpServletRequest);
			}

			HandlerMethod handlerMethod = (HandlerMethod) handlerMapping.getHandler(httpServletRequest).getHandler();

			Method method = handlerMethod.getMethod();

			return method.isAnnotationPresent(ActivateMobileFilterPolicy.class);

		} catch (Exception e) { // TALVEZ FAZER UMA HANDLER ESPECIFICA?
			LOG.error("ERROR");
		}

		return false;

	}

}
