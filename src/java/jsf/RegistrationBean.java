/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsf;

import ejb.UserServiceModel;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Rhayan
 */
@Named
@RequestScoped
public class RegistrationBean {
    
    @EJB
    UserServiceModel usrSrv;
    
    String email;
    String password;
    String firstname;
    String lastname;
    BigDecimal balance;
    String currency;
    Date registrationDate;
    Date lastVisit;    

    public RegistrationBean() {
    }
    
    //call the injected EJB
    public String register() {        
        registrationDate = new Date();
        balance = new BigDecimal(1000000000);
        usrSrv.registerUser(firstname, lastname, email, password, balance, currency, registrationDate, registrationDate);
        return "index";
    }
    
    public UserServiceModel getUsrSrv() {
        return usrSrv;
    }

    public void setUsrSrv(UserServiceModel usrSrv) {
        this.usrSrv = usrSrv;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Date lastVisit) {
        this.lastVisit = lastVisit;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
       
}
