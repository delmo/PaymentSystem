/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsf;

import ejb.UserBean;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Rhayan
 * This class is not used. For testing purposes only.
 */
@Named
@RequestScoped
public class UserRefresh {
    
    @EJB
    private UserBean userServ;
    
    //not in use
    public BigDecimal getLatestBalance(String email){
        return userServ.getUser(email).getBalance();
    }
    
}
