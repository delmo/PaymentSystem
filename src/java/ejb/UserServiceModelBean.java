/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejb;

import entity.PaymentAccount;
import entity.UserGroup;
import entity.SystemUser;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Currency;
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
public class UserServiceModelBean implements UserServiceModel{
    
//    @PersistenceUnit
//    EntityManagerFactory emf;
//    EntityManager em;
//    @Resource
//    UserTransaction utx;
//    ...
//em  = emf.createEntityManager();
//
//    
//        try {
//  utx.begin();
//        em.persist(SomeEntity);
//        em.merge(AnotherEntity);
//        em.remove(ThirdEntity);
//        utx.commit();
//    }
//    catch (Exception e
//
//    
//        ) {
//  utx.rollback();
//    }
    
    
    @PersistenceContext
    EntityManager em;

    @Override
    public void registerUser(String firstname, String lastname, String email, 
            String password, Date registrationDate, Date lastVisit) {
        try {
            SystemUser sys_user;
            UserGroup sys_user_group;
            PaymentAccount sys_user_account;

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = password;
            md.update(passwd.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String paswdToStoreInDB = bigInt.toString(16);

            sys_user = new SystemUser(firstname, lastname, email, paswdToStoreInDB, registrationDate, lastVisit);
            sys_user_group = new UserGroup(email, "users");
            sys_user_account = new PaymentAccount(new BigDecimal("1000000.00"), "USD", sys_user);
            
            em.persist(sys_user);
            em.persist(sys_user_group);
            em.persist(sys_user_account);
            
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UserServiceModelBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }

    @Override
    public List<SystemUser> getUserList() {
        List<SystemUser> users = em.createNamedQuery("findAllSystemUsers").getResultList();
        return users;
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
    
    
//    @PersistenceContext
//    EntityManager em;
//
//    public void enterOrder(int custID, Order newOrder) {
//        Customer cust = em.find(Customer.class, custID);
//        cust.getOrders().add(newOrder);
//        newOrder.setCustomer(cust);
//    }

    @Override
    public void removeUser(String email) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
