package bao.xy.filter;

import bao.xy.model.Staff;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Interceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        Staff user = (Staff) request.getSession().getAttribute("user");
//        System.out.println(uri + "-" + user);
        if (user != null) {
            if (uri.equals("/staff.html")) {
                if ("管理员".equals(user.getWork())) {
                    return true;
                } else {
                    return false;
                }
            }
            return true;

        } else {
            System.out.println(uri + "*************");
            response.sendRedirect("index.html");
            return false;
        }

    }
}
