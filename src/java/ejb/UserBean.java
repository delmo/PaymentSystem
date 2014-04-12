/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import dao.UserGroupModel;
import dao.UserGroupModelBean;
import dao.UserServiceModel;
import entities.SystemUser;
import entities.UserGroup;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Rhayan
 */
@Stateless
public class UserBean{

    private Long id;
    private SystemUser user;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private BigDecimal balance;
    private String currency;
    private Date registrationDate;
    private Date updateDate;
    private List<SystemUser> userlist;
    
    final static Logger myLogger = Logger.getLogger("javax.enterprise.resource.webcontainer.jsf");
    
    @Inject
    private UserGroupModel groupStore;

    @Inject
    private UserServiceModel userStore;
    
    @Inject
    private TimestampClientBean timer;
    
    @Inject
    private CurrencyClientBean forex;   
    

    public UserBean() {
        user = new SystemUser();
    }

    public void registerUser(String firstname, String lastname, String email, 
            String password, String currency){
        try {
            SystemUser sys_user;
            UserGroup sys_user_group;
            
            //formater.parse(timer.getDateTimeNow())
            SimpleDateFormat formater = new SimpleDateFormat("MM/dd/YYYY HH:mm");
            //Date today = formater.parse(timer.getDateTimeNow());
            Date today = new Date();            
            
            BigDecimal initialDeposit = forex.convert(currency, "USD", new BigDecimal("1000000"));
            

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = password;
            md.update(passwd.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String paswdToStoreInDB = bigInt.toString(16);

            sys_user = new SystemUser(firstname, lastname, email, paswdToStoreInDB, initialDeposit, currency, 
                    today, today);
            sys_user_group = new UserGroup(email, "users");            
            
            userStore.saveUser(sys_user);
            groupStore.saveUserGroup(sys_user_group);      
            
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }
    
    public List<SystemUser> getUserlist() {
        return userlist = userStore.getUserList();
    }    
    
    public boolean isAdmin(String email){
        return groupStore.getAdmins().contains(email);       
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

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String searchByEmail() {
//        user = userStore.getUser(this.id);
        user = userStore.findUser(this.email);
        //return "showuser";
        return "/faces/users/show.xhtml";
    }

    public SystemUser getUser(String email) {        
        return userStore.findUser(email);
    }

    public void setUser(SystemUser user) {
        this.user = user;
    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        System.out.println("Username: " + email);
        System.out.println("Password: " + password);
        try {
            //this method will actually check in the realm for the provided credentials
            request.login(this.email, this.password);

        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Login failed."));
            return "error";
        }
        return searchByEmail();
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            //this method will disassociate the principal from the session (effectively logging him/her out)
            request.logout();
            context.addMessage(null, new FacesMessage("User is logged out"));
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Logout failed."));
        }
        return "/faces/index.xhtml";
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("UserBean: PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("UserBean: PreDestroy");
    }
}
