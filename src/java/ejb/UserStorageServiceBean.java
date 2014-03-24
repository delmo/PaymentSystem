/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejb;

import entity.Group;
import entity.User;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rhayan
 */
@Stateless
public class UserStorageServiceBean implements UserStorageService{
    
    @PersistenceContext
    EntityManager em;

    @Override
    public void registerUser(String firstname, String lastname, String email, 
            String password, Timestamp registrationDate, Timestamp lastVisit) {
        try {
            User sys_user;
            Group sys_user_group;

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = password;
            md.update(passwd.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String paswdToStoreInDB = bigInt.toString(16);

            sys_user = new User(firstname, lastname, email, paswdToStoreInDB, registrationDate, lastVisit);
            sys_user_group = new Group(email, "users");

            em.persist(sys_user);
            em.persist(sys_user_group);
            
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UserStorageService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }

    @Override
    public List<User> getUserList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
//    @PostConstruct
//    public void postConstruct() {
//        System.out.println("UserStore: PostConstruct");
//    }
//
//    @PreDestroy
//    public void preDestroy() {
//        System.out.println("UserStore: PreDestroy");
//    }
//    
}
