package cn.hstc.trishop.interceptor;

import cn.hstc.trishop.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 拦截没有登录的访问，重定向到login
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String contextPath = session.getServletContext().getContextPath();
        String[] requireAuthPages = new String[] {
                "index",
        };

        String uri = request.getRequestURI();

        uri = StringUtils.remove(uri, contextPath+"/");
        String page = uri;

        if (beginWith(page, requireAuthPages)) {
            User user = (User) session.getAttribute("user");
            if (null == user) {
                response.sendRedirect("login");
                return false;
            }
        }

        return true;
    }

    private boolean beginWith(String page, String[] requiredAuthPages) {
        boolean result = false;
        for (String requiredAuthPage : requiredAuthPages) {
            if (StringUtils.startsWith(page, requiredAuthPage)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
