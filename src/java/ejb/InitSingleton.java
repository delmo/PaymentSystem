/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.PaymentType;
import entities.SystemUser;
import entities.UserGroup;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rhayan
 */
@Startup
@Singleton
public class InitSingleton {

    @PersistenceContext(unitName = "PaymentSystemPU")
    EntityManager em;

    @PostConstruct
    public void dbInit() {
        System.out.println("At startup: Initialising Datbase with two users");
        // DB Primary Keys start at 1!
        String password = "password";
        MessageDigest md;
        String paswdToStoreInDB = password;
        try {
            md = MessageDigest.getInstance("SHA-256");

            String passwd = password;
            md.update(passwd.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            paswdToStoreInDB = bigInt.toString(16);

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(InitSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
        Date today = new Date();
        SystemUser admin = new SystemUser("Ryan", "Delmo", "ryan@delmo.com", paswdToStoreInDB, new BigDecimal("1000000"), "GBP", today, today);
        SystemUser user = new SystemUser("Jen", "Smith", "jen@example.com", paswdToStoreInDB, new BigDecimal("1000000"), "CAD", today, today);

        UserGroup admin_group = new UserGroup(admin.getEmail(), "admins");
        UserGroup user_group = new UserGroup(user.getEmail(), "users");
        
        em.persist(admin);
        em.persist(user);
        em.persist(admin_group);
        em.persist(user_group);
        em.flush();
    }
}
