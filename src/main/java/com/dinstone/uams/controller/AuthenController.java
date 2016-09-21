
package com.dinstone.uams.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dinstone.security.BusinessException;
import com.dinstone.security.Constant;
import com.dinstone.security.model.Authentication;
import com.dinstone.uams.model.LocalAccount;
import com.dinstone.uams.service.AuthenManageService;

@Service
@RequestMapping("/authen")
public class AuthenController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenController.class);

    @Resource
    private AuthenManageService authenManageService;

    /**
     * 登录
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // ATC & renew &service
        String renew = request.getParameter(Constant.AUTHEN_RENEW_PARAM);
        String scu = request.getParameter(Constant.AUTHEN_CALLBACK_PARAM);
        String warn = request.getParameter(Constant.AUTHEN_WARN_PARAM);
        String authenTicket = getCookieValue(request, Constant.AUTHEN_TOKEN_COOKIE);
        LOG.info("login with renew={} at={} service={} warn={}", renew, authenTicket, scu, warn);

        ModelAndView mav = new ModelAndView();
        // 强制登录或从未登录过
        if (renew != null || authenTicket == null) {
            // 清楚ATC
            Cookie cookie = new Cookie(Constant.AUTHEN_TOKEN_COOKIE, null);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            mav.setViewName("signin");
            mav.addObject(Constant.AUTHEN_CALLBACK_PARAM, scu);
            mav.addObject(Constant.AUTHEN_WARN_PARAM, warn);
            return mav;
        }

        // 检查ATC是否过期
        Authentication authen = authenManageService.checkAuthenToken(authenTicket);
        if (authen == null) {
            // 认证已经过期，跳到登录页面
            Cookie cookie = new Cookie(Constant.AUTHEN_TOKEN_COOKIE, null);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            mav.setViewName("signin");
            mav.addObject(Constant.AUTHEN_CALLBACK_PARAM, scu);
            return mav;
        } else {
            // 认证未过期，生成ST
            String ticket = authenManageService.generateAuthenTicket(authen);
            scu = addTicketParam(scu, ticket);

            mav.setViewName("prompt");
            mav.addObject(Constant.AUTHEN_CALLBACK_PARAM, scu);
            mav.addObject(Constant.AUTHEN_WARN_PARAM, warn);
            return mav;
        }
    }

    protected String addTicketParam(String scu, String ticket) throws MalformedURLException {
        if (scu != null && !scu.isEmpty()) {
            URL su = new URL(scu);
            String q = su.getQuery();
            if (q == null) {
                scu += "?" + Constant.AUTHEN_TICKET_PARAM + "=" + ticket;
            } else {
                scu += "&" + Constant.AUTHEN_TICKET_PARAM + "=" + ticket;
            }
        }
        return scu;
    }

    /**
     * 签到
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/signin")
    public ModelAndView signin(String username, String password, String scu, String warn, HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        ModelAndView mav = new ModelAndView();
        try {
            LOG.info("username={}&password={},signin for {}", username, password, scu);

            LocalAccount account = new LocalAccount(username, password);
            Authentication authen = authenManageService.authenticate(account);

            Cookie cookie = new Cookie(Constant.AUTHEN_TOKEN_COOKIE, authen.getAuthenToken());
            cookie.setPath("/");
            cookie.setMaxAge(30 * 60);
            response.addCookie(cookie);

            String ticket = authenManageService.generateAuthenTicket(authen);
            scu = addTicketParam(scu, ticket);

            mav.setViewName("prompt");
            mav.addObject(Constant.AUTHEN_CALLBACK_PARAM, scu);
            mav.addObject(Constant.AUTHEN_WARN_PARAM, warn);
            return mav;
        } catch (BusinessException se) {
            mav.addObject("msg", "无效的用户名或密码");
        }

        mav.setViewName("signin");
        mav.addObject(Constant.AUTHEN_CALLBACK_PARAM, scu);
        mav.addObject(Constant.AUTHEN_WARN_PARAM, warn);
        return mav;
    }

    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
