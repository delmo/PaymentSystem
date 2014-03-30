/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejb;

import entity.PaymentAccount;
import entity.SystemUser;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Rhayan
 */
public interface AccountServiceModel {
    public List<PaymentAccount> getAllPaymentAccounts();
    public void addPaymentAccount(SystemUser user, BigDecimal amount, String currency);
    public void debit(SystemUser user, BigDecimal amount, String currency);
    public void credit(SystemUser user, BigDecimal amount, String currency);
}
