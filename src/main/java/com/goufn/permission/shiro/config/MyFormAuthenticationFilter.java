package com.goufn.permission.shiro.config;

import com.goufn.permission.entity.SysUser;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @Author 10319
 * @Date 2019/6/13 23:24
 * @Version 1.0
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {


    /*
     * 返回跳转到登录前的页面
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
        // TODO Auto-generated method stub

        SysUser activeUser = (SysUser) subject.getPrincipal();

        HttpServletRequest request2 = (HttpServletRequest) request;
        HttpServletResponse response2 = (HttpServletResponse) response;
        HttpSession session =  request2.getSession();
        // 把user放到session中
        session.setAttribute("user", activeUser);
        try {
            System.err.println("callbackUrl : " + WebUtils.getSavedRequest(request).getRequestURI());// 上个请求的请求url

            if (!"XMLHttpRequest".equalsIgnoreCase(
                    request2.getHeader("X-Requested-With"))) {
                // 不是ajax请求
                issueSuccessRedirect(request, response); // 跳转回去
            } else { // ajax請求直接返回callbackUrl
                response2.setCharacterEncoding("UTF-8");
                PrintWriter out = response2.getWriter();
                out.print(WebUtils.getSavedRequest(request).getRequestURI());
                out.flush();
                out.close();
            }
        } catch(Exception exception) {
            exception.printStackTrace();

        }
        // 若运行到这里，说明是ajax请求，所以直接return false
        return false;

    }
}
