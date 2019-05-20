package cn.irua.demo.handler;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import cn.irua.demo.entity.Users;
import cn.irua.demo.util.AuthToken;
import cn.irua.demo.util.RedisUtil;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
	@Resource
	private RedisUtil redisUtil;
	// 存放鉴权信息的Header名称，默认是Authorization
	private String httpHeaderName = "token";

	// 鉴权失败后返回的错误信息，默认为401 unauthorized
	private String unauthorizedErrorMessage = "请登录";

	// 鉴权失败后返回的HTTP错误码，默认为401
	private int unauthorizedErrorCode = HttpServletResponse.SC_UNAUTHORIZED;

	// 存放登录用户模型Key的Request Key
	public static final String REQUEST_CURRENT_KEY = "REQUEST_CURRENT_KEY";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return true;
			
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		// 如果打上了AuthToken注解则需要验证token
		if (method.getAnnotation(AuthToken.class) != null || handlerMethod.getBeanType().getAnnotation(AuthToken.class) != null) {
			String token = request.getHeader(httpHeaderName);
			
			if (token != null && token.length() != 0) {
				Gson gson = new Gson();
				Users user = new Users();
				user = gson.fromJson(redisUtil.get(token), Users.class);
				if (user == null) {
					response.setStatus(unauthorizedErrorCode);
		            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		            response.sendError(401,unauthorizedErrorMessage);
		            return false;
				}else {
					request.setAttribute("user", user);
					return true;
				}
			}else {
				response.setStatus(unauthorizedErrorCode);
	            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	            response.sendError(401,unauthorizedErrorMessage);
	            return false;
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}
}
