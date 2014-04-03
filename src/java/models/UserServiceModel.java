/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import entities.SystemUser;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rhayan
 */
public interface UserServiceModel {
    public void registerUser(String firstname, String lastname, String email, String password, 
            BigDecimal balance, String currency, Date registrationDate, Date lastVisit);
          
    public List<SystemUser> getUserList();
    
    public void saveUser(SystemUser user);
    
    public void saveNewUser(SystemUser user);
    
    public void updateUser(SystemUser user);
    
    public void removeUser(SystemUser user);
        
    public SystemUser getUser(Long userId);
    
    public SystemUser findUser(String email);
}
