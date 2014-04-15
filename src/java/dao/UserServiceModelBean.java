/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entities.SystemUser;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * DAO implementation for SystemUser entity.
 * @author Rhayan
 */
@Stateless
public class UserServiceModelBean implements UserServiceModel{

    @PersistenceContext(unitName = "PaymentSystemPU")
    EntityManager em;

    /**
     * Method for getting all SystemUser
     * @return list of SystemUser
     */
    @Override
    public List<SystemUser> getUserList() {
        List<SystemUser> users = em.createNamedQuery("findAllSystemUsers").getResultList();
        return users;
    }
    
    /**
     * Method for removing SystemUser.
     * @param user SystemUser
     */
    @Override
    public void removeUser(SystemUser user) {
        em.remove(user);
    }
    
    /**
     * Method for retrieving SystemUser
     * @param userId Long id of SystemUser
     * @return 
     */
    @Override
    public SystemUser getUser(Long userId) {
        SystemUser user;
        
        user = em.find(SystemUser.class, userId);
        
        return user;
    }
    
    /**
     * Method for getting SystemUser by email.
     * @param email
     * @return SystemUser 
     */
    @Override
    public SystemUser findUser(String email) {
        SystemUser user = (SystemUser)em.createNamedQuery("findAllSystemUsersWithEmail").setParameter("email", email).getSingleResult();
        return user;        
    }
    
    /**
     * Method for saving SystemUser
     * @param user 
     */
    @Override
    public void saveUser(SystemUser user) {
        if(user.getId() == null){
            saveNewUser(user);
        }else{
            updateUser(user);
        }
    }

    /**
     * Method for saving new SystemUser
     * @param user 
     */
    @Override
    public void saveNewUser(SystemUser user) {
        em.persist(user);
    }

    /**
     * Method for updating new SystemUser
     * @param user 
     */
    @Override
    public void updateUser(SystemUser user) {
        em.merge(user);
    }

    /**
     * Method for getting all email accounts in the system.
     * @return list of SystemUser
     */
    @Override
    public List<String> getEmails() {     
        return em.createNamedQuery("findAllEmails").getResultList();
    }
}
    
    