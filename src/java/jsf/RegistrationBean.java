/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsf;

import ejb.UserBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Rhayan
 * Managed-bean for holding temporary data coming from the registration form.
 */
@Named
@RequestScoped
public class RegistrationBean {
    
    @EJB
    UserBean usrSrv;
    
    String email;
    String password;
    String firstname;
    String lastname;    
    String currency;    

    public RegistrationBean() {
    }
    
    //call the injected EJB
    public String register() {        
        usrSrv.registerUser(firstname, lastname, email, password, currency);
        return "index";
    }
    
    public UserBean getUsrSrv() {
        return usrSrv;
    }

    public void setUsrSrv(UserBean usrSrv) {
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
       
}
