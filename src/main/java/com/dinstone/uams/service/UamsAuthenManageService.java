
package com.dinstone.uams.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dinstone.security.AccessControlExceptionType;
import com.dinstone.security.BusinessException;
import com.dinstone.security.model.Authentication;
import com.dinstone.security.model.Subject;
import com.dinstone.uams.cache.AuthenCacheService;
import com.dinstone.uams.dao.UamsDao;
import com.dinstone.uams.model.LocalAccount;
import com.dinstone.uams.model.UserProfile;

@Service
public class UamsAuthenManageService implements AuthenManageService {

    @Resource
    private UamsDao uamsDao;

    @Resource
    private AuthenCacheService authenCacheService;

    /**
     * {@inheritDoc}
     * 
     * @see com.dinstone.uams.service.AuthenManageService#authenticate(com.dinstone.uams.model.LocalAccount)
     */
    @Override
    public Authentication authenticate(LocalAccount account) {
        LocalAccount acc = uamsDao.findLocalAccount(account.getUsername());
        if (acc != null) {
            if (acc.getPassword().equals(account.getPassword())) {
                return createAuthentication(acc);
            }
        }

        throw new BusinessException(AccessControlExceptionType.INVALID_ACCOUNT);
    }

    protected Authentication createAuthentication(LocalAccount account) {
        Subject subject = createSubject(account);

        String accountId = account.getUsername();
        String authenToken = accountId + ":" + subject.getId();

        Authentication authentication = new Authentication();
        authentication.setAccountId(accountId);
        authentication.setAccountType(LocalAccount.class.getSimpleName());
        authentication.setSubject(subject);

        authentication.setAuthenToken(authenToken);
        authentication.setExpiration((int) (new Date().getTime() + 180000));

        authenCacheService.addAuthenToken(authenToken, authentication);

        return authentication;
    }

    @Override
    public Authentication checkAuthenToken(String authenToken) {
        Authentication authen = authenCacheService.getAuthenToken(authenToken);
        if (authen == null || isExpired(authen)) {
            authenCacheService.removeAuthenToken(authenToken);
            return null;
        }
        return authen;
    }

    public Authentication checkAuthenTicket(String authenTicket) {
        return authenCacheService.removeAuthenTicket(authenTicket);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.dinstone.uams.service.AuthenManageService#generateAuthenTicket(com.dinstone.security.model.Authentication)
     */
    @Override
    public String generateAuthenTicket(Authentication authen) {
        String ticket = authen.getAuthenToken() + ":" + System.currentTimeMillis();
        authenCacheService.addAuthenTicket(ticket, authen);
        return ticket;
    }

    private boolean isExpired(Authentication authen) {
        return (new Date().getTime() - authen.getExpiration()) > 0 ? false : true;
    }

    private Subject createSubject(LocalAccount account) {
        UserProfile profile = uamsDao.findUserProfile(account.getUserId());
        Subject subject = new Subject();
        subject.setId("" + profile.getId());
        subject.setName(profile.getNickname());
        return subject;
    }

    @Override
    public void addLocalAccount(LocalAccount account, UserProfile profile) {
        if (account == null || profile == null) {
            throw new BusinessException(null, "无效的账号");
        }

        if (exist(account)) {
            throw new BusinessException(null, "账号已存在");
        }

        Date nowTime = new Date();
        profile.setCreateTime(nowTime);
        profile.setUpdateTime(nowTime);
        int userId = uamsDao.insertUserProfile(profile);

        account.setCreateTime(nowTime);
        account.setUpdateTime(nowTime);
        uamsDao.insertUserAccount(userId, account);
    }

    @Override
    public boolean exist(LocalAccount account) {
        LocalAccount ta = uamsDao.findLocalAccount(account.getUsername());
        return ta == null ? false : true;
    }

}
