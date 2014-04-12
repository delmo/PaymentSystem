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
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import dao.TransactionServiceModel;
import dao.UserServiceModel;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Rhayan
 */
@Stateless
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
    //private String parsedAmount;
    private Date date;
    private PaymentTransaction paymentTransaction;
    private List<PaymentTransaction> transactionList;

    private PaymentStatus completed;

    public PaymentStatus getCompleted() {
        completed = PaymentStatus.COMPLETED;
        return completed;
    }

    public List<PaymentTransaction> showAllTransactions() {
        transactionList = transactionService.getTransactions();
        return transactionList;
    }

    public List<PaymentTransaction> getPendingTransactions(SystemUser user) {
        this.transactionList = transactionService.getTransactionByStatus(user.getId(), PaymentStatus.PENDING);
        return this.transactionList;
    }

    public List<PaymentTransaction> getUserTransactions(SystemUser user) {
        this.transactionList = transactionService.getTransactionsByUser(user.getId());
        return this.transactionList;
    }

    public boolean submitPayment(String payer_email, String payee_email, BigDecimal amount) {

        if (!(userService.getEmails().contains(payer_email) && userService.getEmails().contains(payee_email))) {
            return false;
        }

        this.payee = userService.findUser(payee_email);
        this.payer = userService.findUser(payer_email);

        if (this.payee == null || this.payer == null) {
            return false;
        }

        if (this.payee == this.payer) {
            return false;
        }

        if (!(amount instanceof BigDecimal)) {
            return false;
        }

        System.out.println("Payee email: " + payee.getEmail());
        System.out.println("Payer email: " + payer.getEmail());

        if (this.payee != null) {
            if (payer.getBalance().compareTo(amount) == 1) {
                //convert payer's money to dollar and save the transaction
                BigDecimal payerToDollar = forex.convert(this.payer.getCurrency(), "USD", amount);
                transactionService.sendPayment(this.payer, this.payee, PaymentType.DEBIT, PaymentStatus.COMPLETED, payerToDollar, new Date());

                //subtract the amount in payer's balance
                //convert the amount to payee's money and add it to payee's balance
                BigDecimal payer_new_balance = this.payer.getBalance().subtract(amount);
                BigDecimal payees_money = forex.convert(payer.getCurrency(), payee.getCurrency(), amount);
                BigDecimal payee_new_balance = this.payee.getBalance().add(payees_money);

                this.payer.setBalance(payer_new_balance);
                this.payee.setBalance(payee_new_balance);

                System.out.println("Payee new balance: " + payee.getBalance());
                System.out.println("Payer new balance: " + payer.getBalance());

                userService.updateUser(this.payer);
                userService.updateUser(this.payee);
            }
        }
        return true;
    }

    public boolean requestPayment(String payer_email, String payee_email, BigDecimal amount) {

        if (!(userService.getEmails().contains(payer_email) && userService.getEmails().contains(payee_email))) {
            return false;
        }

        this.payer = userService.findUser(payer_email);
        this.payee = userService.findUser(payee_email);

        if (this.payee == null || this.payer == null) {
            return false;
        }

        if (this.payee == this.payer) {
            return false;
        }

        if (!(amount instanceof BigDecimal)) {
            return false;
        }

        System.out.println("Payee email: " + payee.getEmail());
        System.out.println("Payer email: " + payer.getEmail());

        //get payee's currency and convert it dollar before saving to transaction
        BigDecimal payeeToDollar = forex.convert(payee.getCurrency(), "USD", amount);
        transactionService.sendPayment(this.payer, this.payee, PaymentType.CREDIT, PaymentStatus.PENDING, payeeToDollar, new Date());

        return true;
    }

    public void approvePendingTransaction(Long id) {
        paymentTransaction = transactionService.getTransaction(id);

        this.payer = paymentTransaction.getPayer();
        this.payee = paymentTransaction.getPayee();

        //convert dollar to payer's money and subtract from its balance
        BigDecimal payers_money = forex.convert("USD", payer.getCurrency(), paymentTransaction.getAmount());
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
        transactionService.saveTransaction(paymentTransaction);
    }

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

    public List<PaymentTransaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<PaymentTransaction> transactionList) {
        this.transactionList = transactionList;
    }

//    public String getParsedAmount() {
//
//        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
//        double money = amount.doubleValue();
//        parsedAmount = n.format(money);
//        return parsedAmount;
//    }
//
//    public void setParsedAmount(String parsedAmount) {
//        this.parsedAmount = parsedAmount;
//    }   
}
