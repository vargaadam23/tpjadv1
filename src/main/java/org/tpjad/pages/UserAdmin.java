package org.tpjad.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.tpjad.entities.Book;
import org.tpjad.entities.User;

import java.util.List;

@RequiresRoles("manager")//TO DO
public class UserAdmin {
    private static final Logger logger = LogManager.getLogger(Login.class);

    @Inject
    private AlertManager alertManager;

    @Inject
    Session session;

    
    public List<Book> getUsers(){
       return session.createCriteria(User.class).list();
    }

}
