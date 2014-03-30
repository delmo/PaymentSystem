/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsf;

import ejb.UserServiceModel;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;


/**
 *
 * @author Rhayan
 */
@Named
@RequestScoped
public class UserBean {
    
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Timestamp registrationDate;
    final static Logger myLogger = Logger.getLogger("javax.enterprise.resource.webcontainer.jsf");
    
    @EJB
    private UserServiceModel userStore;
    
//    @EJB
//    private DateAndTime timestamp;

    public UserBean() {
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

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }
    
//     public String addUser() {
//        userStore.RegisterUser(firstname, lastname, email, password, timestamp.getCurrentDateTime());
//        myLogger.info(userStore.toString());
//        return "index";
//    }
    
//    public String goBack() {
//        return "index";
//    } 
    
    
    @PostConstruct
    public void postConstruct() {
        System.out.println("UserBean: PostConstruct");
    }
    
    @PreDestroy
    public void preDestroy() {
        System.out.println("UserBean: PreDestroy");
    }
}
