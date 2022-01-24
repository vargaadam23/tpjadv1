package org.tpjad.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Login
{
    private static final Logger logger = LogManager.getLogger(Login.class);

    @Inject
    private AlertManager alertManager;

    @InjectComponent
    private Form login;

    @InjectComponent("email")
    private TextField emailField;

    @InjectComponent("password")
    private PasswordField passwordField;

    @Property
    private String email;

    @Property
    private String password;

    void onValidateFromLogin()
    {
        if (email.length()<2)
            login.recordError(emailField, "Username too short");

        if (password.length()<2)
            login.recordError(passwordField, "Password too short");
    }

    Object onSuccessFromLogin()
    {

        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(email, password);

        //this is all you have to do to support 'remember me' (no config - built in!):
        token.setRememberMe(false);

        try {
            currentUser.login( token );
            logger.info("Login successful!");
            return Index.class;
        } catch ( UnknownAccountException uae ) {
            logger.info("username wasn't in the system, show them an error message?");//
            return Login.class;
        } catch ( IncorrectCredentialsException ice ) {
            logger.info("incorrect credentials?");//
            return Login.class;
        } catch ( LockedAccountException lae ) {
            logger.info("uac locked?");//
            return Login.class;
        } catch ( AuthenticationException ae ) {
            logger.info("Other exception?  :::::  "+ae.getMessage());//
            return Login.class;
        }

    }

    void onFailureFromLogin()
    {
        logger.warn("Login error!");
        alertManager.error("I'm sorry but I can't log you in!");
    }
}
