
package com.dinstone.uams.cache.local;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.dinstone.security.model.Authentication;
import com.dinstone.uams.cache.AuthenCacheService;

public class LocalAuthenCacheService implements AuthenCacheService {

    private Map<String, Authentication> tokenMap = new ConcurrentHashMap<String, Authentication>();

    private Map<String, Authentication> ticketMap = new ConcurrentHashMap<String, Authentication>();

    @Override
    public Authentication getAuthenToken(String authenToken) {
        return tokenMap.get(authenToken);
    }

    @Override
    public void addAuthenToken(String authenToken, Authentication authen) {
        tokenMap.put(authenToken, authen);
    }

    @Override
    public Authentication removeAuthenToken(String authenToken) {
        return tokenMap.remove(authenToken);
    }

    @Override
    public Authentication removeAuthenTicket(String authenTicket) {
        return ticketMap.remove(authenTicket);
    }

    @Override
    public void addAuthenTicket(String authenTicket, Authentication authen) {
        ticketMap.put(authenTicket, authen);
    }

}
