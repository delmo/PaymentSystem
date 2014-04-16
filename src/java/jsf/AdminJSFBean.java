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
 * Managed bean that could be access by admin only.
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

    /**
     * Method for getting list of transactions
     * @return all transactions in the database.
     */
    public List<PaymentTransaction> getTransactions() {
        return transactions = transactionBean.showAllTransactions();
    }

    /**
     * Method for getting all users in the system.
     * @return all users of the system.
     */
    public List<SystemUser> getUserlist() {
        return userlist = userBean.getUserlist();
    }
    
    /**
     * For redirection to admin section
     * @return String link to admin home page
     */
    public String admin(){
        return "/faces/admins/index.xhtml";
    }
    
    /**
     * For redirection to user's home page.
     * @return String link to user's home page.
     */
    public String userpage(){
        return "/faces/users/show.xhtml";
    }
    
    
    
}
