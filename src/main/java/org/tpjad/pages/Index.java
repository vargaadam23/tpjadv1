package org.tpjad.pages;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.services.SecurityService;

import java.util.Collection;
import java.util.Map;

@Import(stylesheet="context:/index.css")
public class Index
{
    @Inject
    private SecurityService securityService;

    public String getStatus() {
        return securityService.isAuthenticated() ? "Authenticated" : "Not Authenticated";
    }
    public String getUsername() {
        Subject currentUser = SecurityUtils.getSubject();
        PrincipalCollection principalCollection = currentUser.getPrincipals();
        if (principalCollection != null && !principalCollection.isEmpty()) {
            Collection<Map> principalMaps = currentUser.getPrincipals().byType(Map.class);
            if (CollectionUtils.isEmpty(principalMaps)) {
                return currentUser.getPrincipal().toString();
            }
            else {
                return (String) principalMaps.iterator().next().get("username");
            }
        }
        return "";
    }
}
