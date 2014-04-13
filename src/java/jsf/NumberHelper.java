/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf;

import ejb.CurrencyClientBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Rhayan
 */
@Named
@RequestScoped
public class NumberHelper implements Serializable {

    @EJB
    private CurrencyClientBean currencyService;
    
    public String formatBalance(BigDecimal balance, String currency) {
        Locale local;
        switch (currency) {
            case "USD":
                local = Locale.US;
                break;
            case "JPY":
                local = Locale.JAPAN;
                break;
            case "GBP":
                local = Locale.UK;
                break;
            case "EUR":
                local = Locale.ITALY;
                break;
            default:
                local = Locale.US;
                break;
        }
        
        BigDecimal balanceToLocal = currencyService.convert("USD", currency, balance);
        return NumberFormat.getCurrencyInstance(local).format(balanceToLocal);
    }    
    
}
