package com.goosvandenbekerom;

import com.goosvandenbekerom.domain.Group;
import com.goosvandenbekerom.domain.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.util.HashMap;
import java.util.Map;

@Startup
@Singleton
public class InitDatabase {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    @PostConstruct
    public void init() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
        parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
        parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
        passwordHash.initialize(parameters);

        Group groupTest = new Group("test");
        Group groupAdmin= new Group("admin");
        User test = new User("test", passwordHash.generate("test".toCharArray()));
        User goos = new User("goos", passwordHash.generate("test".toCharArray()));
        addUserToGroup(test, groupTest);
        addUserToGroup(goos, groupAdmin);
        em.persist(goos);
        em.persist(test);
    }

    private void addUserToGroup(User user, Group group) {
        user.getGroups().add(group);
        group.getUsers().add(user);
    }
}
