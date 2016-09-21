
package com.dinstone.uams.cache;

import com.dinstone.security.model.Authentication;

public interface AuthenCacheService {

    void addAuthenToken(String authenToken, Authentication authentication);

    Authentication getAuthenToken(String authenToken);

    Authentication removeAuthenToken(String authenToken);

    void addAuthenTicket(String ticket, Authentication authen);

    Authentication removeAuthenTicket(String authenTicket);

}
