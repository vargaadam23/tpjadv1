package org.tpjad.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.tpjad.entities.User;

import javax.persistence.TypedQuery;

public class UserRealm extends AuthorizingRealm {
    
    @Inject
    public Session session;

    private static final Logger logger = LogManager.getLogger(UserRealm.class);

    public UserRealm() {
        super(new MemoryConstrainedCacheManager());
        setName("localaccounts");
        setAuthenticationTokenClass(UsernamePasswordToken.class);
        setCredentialsMatcher(new HashedCredentialsMatcher(Sha1Hash.ALGORITHM_NAME));
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) throw new AuthorizationException("PrincipalCollection was null, which should not happen");

        if (principals.isEmpty()) return null;

        if (principals.fromRealm(getName()).size() <= 0) return null;

        String username = (String) principals.fromRealm(getName()).iterator().next();
        if (username == null) return null;

        User user = findByUsername(username);
        if (user == null) return null;
        Set<String> roles = new HashSet<String>(user.getRoles().size());
        for (User.Role role : user.getRoles())
            roles.add(role.name());
        return new SimpleAuthorizationInfo(roles);
    }

    private User findByUsername(String uname) {
        if(uname == null)
            return null;
        Criteria crit = session.createCriteria(User.class);
        crit.add(Restrictions.eq("username",uname));
        logger.info("Username in findbyusername is "+uname);
        List<User> results = crit.list();
//        Query q = session.createQuery("from User u where u.username = :uname");
//        logger.info("Username in findbyusername is "+uname);
//        q.setParameter("uname",uname);
//        logger.info("Parameter set");
//        List<User> results = q.getResultList();
        if(results.size()<=0){
            logger.info("Null");
            return null;
        }else{
            logger.info("Got object "+results.get(0).getUsername());
            return results.get(0);
        }

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;

        String username = upToken.getUsername();
        logger.info("Username after extracting token is "+username);
        // Null username is invalid
        if (username == null) { throw new AccountException("Null usernames are not allowed by this realm."); }

        User user = findByUsername(username);
        logger.info("User email after find in auth is "+user.getEmailAddress());
        if(user == null) { throw new IncorrectCredentialsException("Null user"); }

        if (user.isAccountLocked()) { throw new LockedAccountException("Account [" + username + "] is locked."); }
        if (user.isCredentialsExpired()) {
            String msg = "The credentials for account [" + username + "] are expired";
            throw new ExpiredCredentialsException(msg);
        }
        return new SimpleAuthenticationInfo(username, user.getEncodedPassword(), new SimpleByteSource(user.getPasswordSalt()), getName());
    }

}