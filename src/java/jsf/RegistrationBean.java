/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsf;

import ejb.UserStorageServiceBean;
import java.sql.Timestamp;
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
    UserStorageServiceBean usrSrv;
    
    String email;
    String password;
    String firstname;
    String lastname;
    Timestamp registrationDate;
    Timestamp lastVisit;

    //call the injected EJB
    public String register() {
        Date current = new Date();
        registrationDate = new Timestamp(current.getTime());
        usrSrv.registerUser(firstname, lastname, email, password, registrationDate, registrationDate);
        return "index";
    }
    
    public UserStorageServiceBean getUsrSrv() {
        return usrSrv;
    }

    public void setUsrSrv(UserStorageServiceBean usrSrv) {
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

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Timestamp getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Timestamp lastVisit) {
        this.lastVisit = lastVisit;
    }
    
     
}
