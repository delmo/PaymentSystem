/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.xml.ws.WebServiceRef;


/**
 *
 * @author Rhayan
 */
@Named
@RequestScoped
public class TimestampClientBean {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/TimestampWS/TimestampWS.wsdl")
    private TimestampWS_Service service;
    
    
    
    private String dateTimeNow;

    public String getDateTimeNow() {
        return getTimestamp();
    }
//    
//    public static String getTimestamp() {
//        org.me.timestamp.TimestampWSApplication_Service service = new org.me.timestamp.TimestampWSApplication_Service();
//        timestamp.TimestampWS port = service.getTimestampWSApplicationPort();
//        return port.getTimestamp();
//    }

    private String getTimestamp() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        controllers.TimestampWS port = service.getTimestampWSPort();
        return port.getTimestamp();
    }
    
    
}
