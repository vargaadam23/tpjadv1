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
import org.hibernate.Session;
import org.tpjad.entities.User;
import org.tpjad.services.interfaces.UserServiceInterface;

import java.util.HashSet;
import java.util.Set;

public class Register
{
    private static final Logger logger = LogManager.getLogger(Register.class);

    @Inject
    private AlertManager alertManager;

    @Inject
    private Session session;

    @Inject
    private UserServiceInterface userService;

    @InjectComponent
    private Form register;

    @InjectComponent("email")
    private TextField emailField;

    @InjectComponent("firstName")
    private TextField firstNameField;

    @InjectComponent("lastName")
    private TextField lastNameField;

    @InjectComponent("phone")
    private TextField phoneField;

    @InjectComponent("password")
    private PasswordField passwordField;

    @InjectComponent("username")
    private TextField usernameField;

    @Property
    private String email;

    @Property
    private String username;

    @Property
    private String firstName;

    @Property
    private String lastName;

    @Property
    private String phone;

    @Property
    private String password;

    void onValidateFromRegister()
    {
        if (email.length()<=2)
            register.recordError(emailField, "Length too short");
        if (username.length()<=2)
            register.recordError(usernameField, "Length too short");
        if (password.length()<=2)
            register.recordError(passwordField, "Length too short");
        if (phone.length()<=2)
            register.recordError(phoneField, "Length too short");
        if (firstName.length()<=2)
            register.recordError(firstNameField, "Length too short");
        if (lastName.length()<=2)
            register.recordError(lastNameField, "Length too short");
        if(userService.findByUsername(username)!=null){
            register.recordError(usernameField, "Username already exists");
        }
        
    }

    Object onSuccessFromRegister()
    {

        User user = new User();
        user.setAccountLocked(false);
        user.setCredentialsExpired(false);
        user.setEmailAddress(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setUsername(username);


        Set<User.Role> roles = new HashSet<User.Role>();
        roles.add(User.Role.user);
        roles.add(User.Role.manager);
        user.setRoles(roles);


        session.save(user);
        session.getTransaction().commit();
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);


        token.setRememberMe(false);

        try {
            currentUser.login( token );
            logger.info("Login successful!");
            return Index.class;
        } catch ( UnknownAccountException uae ) {
            logger.info("username wasn't in the system, show them an error message?");//
            return Register.class;
        } catch ( IncorrectCredentialsException ice ) {
            logger.info("incorrect credentials?");//
            return Register.class;
        } catch ( LockedAccountException lae ) {
            logger.info("uac locked?");//
            return Register.class;
        } catch ( AuthenticationException ae ) {
            logger.info("Other exception?");//
            return Register.class;
        }

    }

    void onFailureFromRegister()
    {
        logger.warn("Register error!");
        alertManager.error("I'm sorry but I can't register you!");
    }
}
