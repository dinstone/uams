
package com.dinstone.uams.service;

import com.dinstone.security.model.Authentication;
import com.dinstone.security.service.AuthenticateService;
import com.dinstone.uams.model.LocalAccount;
import com.dinstone.uams.model.UserProfile;

public interface AuthenManageService extends AuthenticateService {

    public abstract Authentication authenticate(LocalAccount account);

    public abstract String generateAuthenTicket(Authentication authen);

    public abstract boolean exist(LocalAccount account);

    public abstract void addLocalAccount(LocalAccount account, UserProfile profile);

}