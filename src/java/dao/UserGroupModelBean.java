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
 *
 * @author Rhayan
 */
@Stateless
public class UserGroupModelBean implements UserGroupModel{

    @PersistenceContext(unitName = "PaymentSystemPU")
    EntityManager em;
            
    @Override
    public List<String> getAdmins() {
        return em.createNamedQuery("findAllAdmins").getResultList();
    }

    @Override
    public UserGroup getUserGroup(Long id) {
        return em.find(UserGroup.class, id);
    }

    @Override
    public void saveUserGroup(UserGroup ug) {
        if(ug.getId() == null){
            saveNewUserGroup(ug);
        }else{
            updateUserGroup(ug);
        }
    }

    @Override
    public void saveNewUserGroup(UserGroup ug) {
        em.persist(ug);
    }

    @Override
    public void updateUserGroup(UserGroup ug) {
        em.merge(ug);
    }

    @Override
    public void deleteUserGroup(UserGroup ug) {
        em.remove(ug);
    }
    
}
