/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsf;

import ejb.TransactionBean;
import ejb.UserBean;
import entities.PaymentTransaction;
import entities.SystemUser;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Rhayan
 */
@Named
@RequestScoped
public class AdminJSFBean {
    
    @EJB
    UserBean userBean;
    
    @EJB
    TransactionBean transactionBean;
    
    private List<SystemUser> userlist;
    
    private List<PaymentTransaction> transactions;

    public List<PaymentTransaction> getTransactions() {
        return transactions = transactionBean.showAllTransactions();
    }

    public List<SystemUser> getUserlist() {
        return userlist = userBean.getUserlist();
    }
    
    public String admin(){
        return "/faces/admins/index.xhtml";
    }
    
    public String userpage(){
        return "/faces/users/show.xhtml";
    }
    
    
    
}
