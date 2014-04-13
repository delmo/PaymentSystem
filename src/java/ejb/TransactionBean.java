/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.PaymentStatus;
import entities.PaymentTransaction;
import entities.PaymentType;
import entities.SystemUser;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import dao.TransactionServiceModel;
import dao.UserServiceModel;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Rhayan
 * TransactionBean class is used for business logic transaction.
 * This EJB is called by web layer everytime there is new or update
 * in the Payment System transaction.
 */
@Stateless
@DeclareRoles({"users", "admins"})
public class TransactionBean {

    @Inject
    UserServiceModel userService;

    @Inject
    TransactionServiceModel transactionService;

    @Inject
    private CurrencyClientBean forex;

    @Inject
    private TimestampClientBean timer;

    private String email;
    private String currency;
    private Long transactionId;
    private SystemUser payer;
    private SystemUser payee;
    private String paymentType;
    private String paymentStatus;
    private BigDecimal amount;    
    private Date date;
    private PaymentTransaction paymentTransaction;
    private List<PaymentTransaction> transactionList;

    private PaymentStatus completed;

    public PaymentStatus getCompleted() {
        completed = PaymentStatus.COMPLETED;
        return completed;
    }

    //Return all transactions. This is consume by admin only
    @RolesAllowed({"admins"})
    public List<PaymentTransaction> showAllTransactions() {
        transactionList = transactionService.getTransactions();
        return transactionList;
    }

    //Return list of all pending transactions by a certain user.
    @PermitAll
    public List<PaymentTransaction> getPendingTransactions(SystemUser user) {
        this.transactionList = transactionService.getTransactionByStatus(user.getId(), PaymentStatus.PENDING);
        return this.transactionList;
    }

    //Return list of all transactions by a certain user.
    @PermitAll
    public List<PaymentTransaction> getUserTransactions(SystemUser user) {
        this.transactionList = transactionService.getTransactionsByUser(user.getId());
        return this.transactionList;
    }

    //Return true on a succesful payment; otherwise, false if error occurs.
    @PermitAll
    public int submitPayment(String payer_email, String payee_email, BigDecimal amount) {

        //check if all emails are present in the system
        if (!(userService.getEmails().contains(payer_email) && userService.getEmails().contains(payee_email))) {
            return 0;
        }

        this.payee = userService.findUser(payee_email);
        this.payer = userService.findUser(payer_email);

        if (this.payee == null || this.payer == null) {
            return 0;
        }

        if (this.payee == this.payer) {
            return 0;
        }

        if (!(amount instanceof BigDecimal)) {
            return -1;
        }

        System.out.println("Payee email: " + payee.getEmail());
        System.out.println("Payer email: " + payer.getEmail());

        if (this.payee != null) {
            if (payer.getBalance().compareTo(amount) == 1) {
                //convert payer's money to dollar and save the transaction
                BigDecimal payerToDollar = forex.convert(this.payer.getCurrency(), "USD", amount);
                transactionService.sendPayment(this.payer, this.payee, PaymentType.DEBIT, PaymentStatus.COMPLETED, payerToDollar, timer.getDateTimeNow());

                //subtract the amount in payer's balance
                //convert the amount to payee's money and add it to payee's balance
                BigDecimal payer_new_balance = this.payer.getBalance().subtract(amount);
                BigDecimal payees_money = forex.convert(payer.getCurrency(), payee.getCurrency(), amount);
                BigDecimal payee_new_balance = this.payee.getBalance().add(payees_money);

                this.payer.setBalance(payer_new_balance);
                this.payee.setBalance(payee_new_balance);

                System.out.println("Payee new balance: " + payee.getBalance());
                System.out.println("Payer new balance: " + payer.getBalance());

                //update the account of both users
                userService.updateUser(this.payer);
                userService.updateUser(this.payee);
            }else{
                return -1;
            }
        }
        return 1;
    }

    @PermitAll
    public int requestPayment(String payer_email, String payee_email, BigDecimal amount) {

        if (!(userService.getEmails().contains(payer_email) && userService.getEmails().contains(payee_email))) {
            return 0;
        }

        this.payer = userService.findUser(payer_email);
        this.payee = userService.findUser(payee_email);

        if (this.payee == null || this.payer == null) {
            return 0;
        }

        if (this.payee == this.payer) {
            return 0;
        }

        if (!(amount instanceof BigDecimal)) {
            return -1;
        }

        System.out.println("Payee email: " + payee.getEmail());
        System.out.println("Payer email: " + payer.getEmail());

        //get payee's currency and convert it dollar before saving to transaction
        BigDecimal payeeToDollar = forex.convert(payee.getCurrency(), "USD", amount);
        transactionService.sendPayment(this.payer, this.payee, PaymentType.CREDIT, PaymentStatus.PENDING, payeeToDollar, timer.getDateTimeNow());

        return 1;
    }

    @PermitAll
    public boolean approvePendingTransaction(Long id) {
        paymentTransaction = transactionService.getTransaction(id);

        this.payer = paymentTransaction.getPayer();
        this.payee = paymentTransaction.getPayee();

        //convert dollar to payer's money and subtract from its balance
        BigDecimal payers_money = forex.convert("USD", payer.getCurrency(), paymentTransaction.getAmount());
        if(this.payer.getBalance().compareTo(payers_money) == -1){
            return false;
        }
        BigDecimal payer_new_balance = this.payer.getBalance().subtract(payers_money);

        //convert dollar to payee's money and add to its balance
        BigDecimal payees_money = forex.convert("USD", payee.getCurrency(), paymentTransaction.getAmount());
        BigDecimal payee_new_balance = this.payee.getBalance().add(payees_money);

        this.payer.setBalance(payer_new_balance);
        this.payee.setBalance(payee_new_balance);

        System.out.println("Payee new balance: " + payee.getBalance());
        System.out.println("Payer new balance: " + payer.getBalance());

        userService.updateUser(this.payer);
        userService.updateUser(this.payee);

        paymentTransaction.setPaymentStatus(PaymentStatus.COMPLETED);
        paymentTransaction.setDate(timer.getDateTimeNow());
        transactionService.saveTransaction(paymentTransaction);
        
        return true;
    }

    @PermitAll
    public void cancelTransaction(Long id) {
        paymentTransaction = transactionService.getTransaction(id);
        paymentTransaction.setPaymentStatus(PaymentStatus.REJECTED);
        transactionService.saveTransaction(paymentTransaction);
    }

    public UserServiceModel getUserService() {
        return userService;
    }

    public void setUserService(UserServiceModel userService) {
        this.userService = userService;
    }

    public TransactionServiceModel getTransactionService() {
        return transactionService;
    }

    public void setTransactionService(TransactionServiceModel transactionService) {
        this.transactionService = transactionService;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public SystemUser getPayer() {
        return payer;
    }

    public void setPayer(SystemUser payer) {
        this.payer = payer;
    }

    public SystemUser getPayee() {
        return payee;
    }

    public void setPayee(SystemUser payee) {
        this.payee = payee;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BigDecimal getAmount() {

        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public PaymentTransaction getPaymentTransaction() {
        return paymentTransaction;
    }

    public void setPaymentTransaction(PaymentTransaction paymentTransaction) {
        this.paymentTransaction = paymentTransaction;
    }

    @PermitAll
    public List<PaymentTransaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<PaymentTransaction> transactionList) {
        this.transactionList = transactionList;
    }

}
