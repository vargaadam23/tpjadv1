package org.tpjad.services.interfaces;

import org.tpjad.entities.User;

public interface UserServiceInterface {

    public void saveUsesr(User user);
    public User findByUsername(String username);
}
