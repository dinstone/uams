
package com.dinstone.uams.api;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Service;

import com.dinstone.security.model.Authentication;
import com.dinstone.security.service.AuthenticateService;

@Service
@Path("/authen")
@Produces(MediaType.APPLICATION_JSON)
public class AuthenResource {

    @Resource
    private AuthenticateService authenticateService;

    @GET
    @Path("/ticket/{ticket}")
    public Authentication checkAuthenTicket(@PathParam("ticket") String tikeck) {
        return authenticateService.checkAuthenTicket(tikeck);
    }

    @GET
    @Path("/token/{token}")
    public Authentication checkAuthenToken(@PathParam("token") String token) {
        return authenticateService.checkAuthenToken(token);
    }

}
