package org.tpjad.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.tpjad.entities.User;

import org.tpjad.services.interfaces.UserServiceInterface;

import java.util.List;

public class UserService implements UserServiceInterface {

    private Session session;

    private static final Logger logger = LogManager.getLogger(UserService.class);

    public UserService(Session session) {
        this.session=session;
    }

    public User findByUsername(String uname){
        if(uname == null)
            return null;
        Criteria crit = session.createCriteria(User.class);
        crit.add(Restrictions.eq("username",uname));
        List<User> results = crit.list();
        if(results.size()<=0){
            logger.info("Null");
            return null;
        }else{
            logger.info("Got object "+results.get(0).getUsername());
            return results.get(0);
        }
    }

    public void saveUsesr(User user){
        session.save(user);
        session.getTransaction().commit();
    }

}
