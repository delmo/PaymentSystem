/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;


/**
 *
 * @author Rhayan
 */
@Named
@RequestScoped
public class TimestampClientBean {
    
    
    
    private String dateTimeNow;

    public String getDateTimeNow() {
        return getTimestamp();
    }
    
    public static String getTimestamp() {
        org.me.timestamp.TimestampWSApplication_Service service = new org.me.timestamp.TimestampWSApplication_Service();
        org.me.timestamp.TimestampWSApplication port = service.getTimestampWSApplicationPort();
        return port.getTimestamp();
    }
}
