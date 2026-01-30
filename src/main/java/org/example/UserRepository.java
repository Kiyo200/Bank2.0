package org.example;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    static Map<String, User> profil;

    public UserRepository() {
        profil = new HashMap<>();
        profil.put("admin", new User("admin", "admin", 1000000, true));
    }

    public User getUser(String name) {
        return profil.get(name);
    }



    public void remove(User u) {
        profil.remove(u.getUsername());
    }

    public  User register(String name, String pass) throws AlreadyRegisteredUserException {
        if(profil.containsKey(name)) {

            throw new AlreadyRegisteredUserException(name);
        } else {
            User user = new User(name,pass, 1000000, false);
            profil.put(name, user );

            System.out.println("Sikeresen Regisztráltál");
            return user;
        }
    }


    public  User login(String nev, String pass) throws AuthenticationException {
        User profile = profil.get(nev);
        if (profile == null) {
            throw new AuthenticationException("the login name is not valid");
        }

        if( !profile.getPassword().equals(pass)){
            throw new AuthenticationException("wring credential");
        }
        return profile;
    }
}
