package org.tpjad.components;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;

import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.tpjad.pages.Index;


/**
 * Layout component for pages of application test-project.
 */

@Import(stylesheet="context:/dashboard.css")
@RequiresAuthentication
public class AdminLayout
{
    @OnEvent(component="logout")
    public Object logout()
    {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return Index.class;
    }
}
