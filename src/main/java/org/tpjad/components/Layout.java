package org.tpjad.components;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.tpjad.pages.Index;

import java.time.LocalDate;

/**
 * Layout component for pages of application test-project.
 */

@Import(stylesheet="context:/layout.css")
public class Layout
{

    @Inject
    private ComponentResources resources;

    /**
    * The page title, for the <title> element and the <h1> element.
    */
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;

    @Property
    private String pageName;

    @Property
    @Inject
    @Symbol(SymbolConstants.APPLICATION_VERSION)
    private String appVersion;

    public String getMontserrat(){
        return "https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap";
    }

    public String getClassForPageName()
    {
        return resources.getPageName().equalsIgnoreCase(pageName)
            ? "active"
            : null;
    }

    public String[] getPageNames()
    {
        return new String[]{"Index", "About"};
    }

    public int getYear()
    {
        return LocalDate.now().getYear();
    }

    @OnEvent(component="logout")
    public Object logout()
    {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return Index.class;
    }
}
