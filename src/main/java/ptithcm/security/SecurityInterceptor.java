package ptithcm.security;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;

import ptithcm.entity.Auth;
import ptithcm.entity.UserRole;
import ptithcm.entity.User;
import ptithcm.util.ConstantUtil;

public class SecurityInterceptor implements HandlerInterceptor {
	private static final Logger log = Logger.getLogger(SecurityInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		log.info("Request URI = " + request.getRequestURI());
		User user = (User) request.getSession().getAttribute(ConstantUtil.USER_INFO);

		if ((user == null && !request.getServletPath().equals("/resetpassword"))
				&& (user == null && !request.getServletPath().equals("/verifyid"))) {
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		}

		if (user != null) {
			String url = request.getServletPath();
			if (!hasPermission(url, user)) {
				log.info("ACCESS DENIED URI = " + request.getRequestURI());
				response.sendRedirect(request.getContextPath() + "/access-denied");
				return false;
			}
		}

		return true;
	}

	private boolean hasPermission(String url, User user) {
		if (url.contains("/index") || url.contains("/access-denied") || url.contains("/logout")) {
			return true;
		}
		UserRole userRole = (UserRole) user.getUserRoles().iterator().next();
		Collection<Auth> auths = userRole.getRole().getAuths();
		for (Object obj : auths) {
			Auth auth = (Auth) obj;
			if (url.contains(auth.getMenu().getUrl())) {
				return auth.getPermission() == 1;
			}
		}
		return false;

	}
}
