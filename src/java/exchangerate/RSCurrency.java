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
import javax.ws.rs.core.Response;

/**
 *
 * @author Rhayan
 * RESTful service implementing System Currency application
 */
@Singleton
@Path("/")
public class RSCurrency {

    private final HashMap<String, SystemCurrency> currencies;

    public RSCurrency() {
        currencies = new HashMap<>();
        setup();
    }

    /**
     * HTTP get request for converting one currency to another
     * @param currency1 String source currency
     * @param currency2 String which currency to convert
     * @param amount_of_currency1 String amount
     * @return JSON object {currency1}/{currency2}/{amount_of_currency1}
     */
    @GET
    @Path("{currency1}/{currency2}/{amount_of_currency1}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConversion(@PathParam("currency1") String currency1,
            @PathParam("currency2") String currency2,
            @PathParam("amount_of_currency1") String amount_of_currency1) {

        if ((currency1.isEmpty()) || (currency2.isEmpty()) || (amount_of_currency1.isEmpty())){
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity("Please follow the correct format. e.g. /USD/GBP/10").build();
        }
        
        if (!currencies.containsKey(currency1)) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(currency1 + " not supported currency.").build();
        }
        
        if (!currencies.containsKey(currency2)) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(currency2 + " not supported currency.").build();
        }
        
        BigDecimal amount = BigDecimal.ONE;
        
        try {
            amount = new BigDecimal(amount_of_currency1.replaceAll(",", ""));
        } catch(NumberFormatException e) {
            String error = e.getMessage();
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(amount_of_currency1 + " not a correct number format.").build();
        }
         

        BigDecimal currencyTo = amount.multiply(new BigDecimal(1));

        if (currency1 == "USD") {
            if (currency2 != "USD") {
                currencyTo = amount.multiply(currencies.get(currency2).aDollarToLocal);
            }
        } else if (currency1 != "USD") {
            if (currency2 == "USD") {
                currencyTo = amount.multiply(currencies.get(currency2).aLocalToDollar);
            } else {
                currencyTo = (amount.multiply(currencies.get(currency1).aLocalToDollar))
                        .multiply(currencies.get(currency2).aDollarToLocal);
            }
        } else {
            //currency not recognize
        }

        String json = amount.toPlainString() + " " + currency1 + " = " + currencyTo.toPlainString() + " " + currency2;
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    /**
     * Get request to return all available currencies.
     * @return JSON object list of currencies.
     */
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, SystemCurrency> getCurrencies() {
        return currencies;
    }

    @PostConstruct
    public void init() {
        System.out.println("Singleton Object for this RESTfull Web Service has been created!");
    }

    @PreDestroy
    public void clean() {
        System.out.println("Singleton Object for this RESTfull Web Service has been cleaned!");
    }

    /**
     * Method for setting up 4 different currencies and its conversion to US dollar.
     */
    private void setup() {

        SystemCurrency usd = new SystemCurrency("USD", "US Dollar", new BigDecimal("1"), new BigDecimal("1"));
        SystemCurrency eur = new SystemCurrency("EUR", "Euro", new BigDecimal("0.727981"), new BigDecimal("1.373662"));
        SystemCurrency gbp = new SystemCurrency("GBP", "US Dollar", new BigDecimal("0.597783"), new BigDecimal("1.672849"));        
        SystemCurrency jpy = new SystemCurrency("JPY", "Japanese Yen", new BigDecimal("102.28542"), new BigDecimal("0.009777"));

        currencies.put("USD", usd);
        currencies.put("EUR", eur);
        currencies.put("GBP", gbp);        
        currencies.put("JPY", jpy);        

    }

}
