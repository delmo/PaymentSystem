/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package exchangerate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Rhayan
 */
@Singleton
@Path("/")
public class RSCurrency {
    
    private final HashMap<String, SystemCurrency> currencies;    
    
    
    public RSCurrency(){
        currencies = new HashMap<>();
        setup();
    }
    
    @GET
    @Path("{currency1}/{currency2}/{amount_of_currency1}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getConversion(@PathParam("currency1") String currency1,
            @PathParam("currency2") String currency2, 
            @PathParam("amount_of_currency1") String amount_of_currency1){
        
        BigDecimal amount = new BigDecimal(amount_of_currency1.replaceAll(",", ""));
         
        BigDecimal currencyTo = amount.multiply(new BigDecimal(1));
        
        if (currency1 == "USD"){
            if (currency2 != "USD"){
                currencyTo = amount.multiply(currencies.get(currency2).aDollarToLocal);            
            }           
        }else if (currency1 != "USD"){
            if (currency2 == "USD"){
                currencyTo = amount.multiply(currencies.get(currency2).aLocalToDollar);
            }else{
                currencyTo = (amount.multiply(currencies.get(currency1).aLocalToDollar))
                                    .multiply(currencies.get(currency2).aDollarToLocal);
            }
        }else{
            //currency not recognize
        }
                
        
        return amount.toPlainString() + " " + currency1 + " = " + currencyTo.toPlainString() + " " + currency2;
        //return currencyTo.toPlainString();
    }

    @GET   
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, SystemCurrency> getCurrencies() {
        return currencies;
    }
    
           
    @PostConstruct
    public void init(){
        System.out.println("Singleton Object for this RESTfull Web Service has been created!");
    }
    
    @PreDestroy
    public void clean(){
        System.out.println("Singleton Object for this RESTfull Web Service has been cleaned!");
    }

    private void setup() {
        
        SystemCurrency usd = new SystemCurrency("USD", "US Dollar", new BigDecimal("1"), new BigDecimal("1"));
        SystemCurrency eur = new SystemCurrency("EUR", "Euro", new BigDecimal("0.73"), new BigDecimal("1.373"));
        SystemCurrency gbp = new SystemCurrency("GBP", "US Dollar", new BigDecimal("0.60"), new BigDecimal("1.67"));
        SystemCurrency php = new SystemCurrency("PHP", "Philippine Peso", new BigDecimal("44.77"), new BigDecimal("0.02"));
        SystemCurrency cad = new SystemCurrency("CAD", "Canadian Dollar", new BigDecimal("1.10"), new BigDecimal("0.90"));
        SystemCurrency jpy = new SystemCurrency("JPY", "Japanese Yen", new BigDecimal("102.28"), new BigDecimal("0.01"));
        
        currencies.put("USD", usd);
        currencies.put("EUR", eur);
        currencies.put("GBP", gbp);
        currencies.put("PHP", php);
        currencies.put("CAD", cad);
        currencies.put("JPY", jpy);
        
  }
 
}  

