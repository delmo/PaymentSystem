/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import entities.UserGroup;
import entities.SystemUser;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Rhayan
 */
@Stateless
public class UserServiceModelBean implements UserServiceModel{

    @PersistenceContext
    EntityManager em;

    @Override
    public void registerUser(String firstname, String lastname, String email, 
            String password, BigDecimal balance, String currency, Date registrationDate, Date lastVisit) {
        try {
            SystemUser sys_user;
            UserGroup sys_user_group;
            

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = password;
            md.update(passwd.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String paswdToStoreInDB = bigInt.toString(16);

            sys_user = new SystemUser(firstname, lastname, email, paswdToStoreInDB, balance, currency, registrationDate, lastVisit);
            sys_user_group = new UserGroup(email, "users");            
            
            em.persist(sys_user);
            em.persist(sys_user_group);            
            
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UserServiceModelBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }

    @Override
    public List<SystemUser> getUserList() {
        List<SystemUser> users = em.createNamedQuery("findAllSystemUsers").getResultList();
        return users;
    }
    
    @Override
    public void removeUser(SystemUser user) {
        em.remove(user);
    }
    
    @Override
    public SystemUser getUser(Long userId) {
        SystemUser user;
        
        user = em.find(SystemUser.class, userId);
        
        return user;
    }
    
    @Override
    public SystemUser findUser(String email) {
        SystemUser user;
        Query q = em.createQuery("SELECT u FROM SystemUser u WHERE u.email = :email", SystemUser.class);
        
        user = (SystemUser) q.setParameter("email", email).getSingleResult();
        
        return user;
    }
    
    @Override
    public void saveUser(SystemUser user) {
        if(user.getId() == null){
            saveNewUser(user);
        }else{
            updateUser(user);
        }
    }

    @Override
    public void saveNewUser(SystemUser user) {
        em.persist(user);
    }

    @Override
    public void updateUser(SystemUser user) {
        em.merge(user);
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
  
}



    
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
    
    