package com.goosvandenbekerom;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.BasicAuthenticationCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;

import static javax.security.enterprise.identitystore.CredentialValidationResult.Status.VALID;

@ApplicationScoped
public class PocAuthenticationMechanism implements HttpAuthenticationMechanism {
    private static final String PREFIX = "Basic ";

    @Inject
    private IdentityStoreHandler identityStoreHandler;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {
        final String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (auth == null || !auth.startsWith(PREFIX))
            return httpMessageContext.doNothing();

        BasicAuthenticationCredential credentials = new BasicAuthenticationCredential(auth.replaceFirst(PREFIX, ""));
        CredentialValidationResult result = identityStoreHandler.validate(credentials);

        if (result.getStatus() == VALID)
            return httpMessageContext.notifyContainerAboutLogin(result.getCallerPrincipal(), result.getCallerGroups());
        else
            return httpMessageContext.responseUnauthorized();
    }
}
