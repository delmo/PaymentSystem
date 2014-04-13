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
 */
@Named
@RequestScoped
public class UserRefresh {
    
    @EJB
    private UserBean userServ;
    
    public BigDecimal getLatestBalance(String email){
        return userServ.getUser(email).getBalance();
    }
    
}
