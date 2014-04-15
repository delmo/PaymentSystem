/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entities.UserGroup;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * DAO implementation for UserGroup entity.
 * @author Rhayan
 */
@Stateless
public class UserGroupModelBean implements UserGroupModel{

    @PersistenceContext(unitName = "PaymentSystemPU")
    EntityManager em;
    
    /**
     * Method for getting list of all admins.
     * @return list of emails.
     */        
    @Override
    public List<String> getAdmins() {
        return em.createNamedQuery("findAllAdmins").getResultList();
    }

    /**
     * Method for finding UserGroup based on its id.
     * @param id
     * @return UserGroup
     */
    @Override
    public UserGroup getUserGroup(Long id) {
        return em.find(UserGroup.class, id);
    }

    /**
     * Method for saving UserGroup.
     * @param ug UserGroup
     */
    @Override
    public void saveUserGroup(UserGroup ug) {
        if(ug.getId() == null){
            saveNewUserGroup(ug);
        }else{
            updateUserGroup(ug);
        }
    }

    /**
     * Method for saving new group
     * @param ug UserGroup
     */
    @Override
    public void saveNewUserGroup(UserGroup ug) {
        em.persist(ug);
    }

    /**
     * Method for updating existing group
     * @param ug UserGroup
     */
    @Override
    public void updateUserGroup(UserGroup ug) {
        em.merge(ug);
    }

    /**
     * Method for removing UserGroup.
     * @param ug UserGroup
     */
    @Override
    public void deleteUserGroup(UserGroup ug) {
        em.remove(ug);
    }
    
}
