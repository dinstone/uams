
package com.dinstone.uams.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dinstone.security.BusinessException;
import com.dinstone.security.Constant;
import com.dinstone.uams.model.LocalAccount;
import com.dinstone.uams.model.UserProfile;
import com.dinstone.uams.service.AuthenManageService;

@Service
@RequestMapping("/manage")
public class ManageController {

    private static final Logger LOG = LoggerFactory.getLogger(ManageController.class);

    @Resource
    private AuthenManageService authenManageService;

    /**
     * 注册
     * 
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/signup/submit")
    public ModelAndView signupSubmit(LocalAccount account, UserProfile profile, String scu, String warn,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView mav = new ModelAndView();
        try {
            LOG.info("{} {}", account, profile);
            authenManageService.addLocalAccount(account, profile);

            mav.setViewName("signin");
            mav.addObject(Constant.AUTHEN_CALLBACK_PARAM, scu);
            mav.addObject(Constant.AUTHEN_WARN_PARAM, warn);
            return mav;
        } catch (BusinessException se) {
            mav.addObject("msg", "无效的用户名或密码");
        }

        mav.setViewName("signup");
        mav.addObject(Constant.AUTHEN_CALLBACK_PARAM, scu);
        mav.addObject(Constant.AUTHEN_WARN_PARAM, warn);
        return mav;
    }

    @RequestMapping(value = "/signup/init")
    public ModelAndView signupInit(String scu, String warn, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("signup");
        mav.addObject(Constant.AUTHEN_CALLBACK_PARAM, scu);
        mav.addObject(Constant.AUTHEN_WARN_PARAM, warn);
        return mav;
    }

}
