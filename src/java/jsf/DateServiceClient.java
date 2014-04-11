/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jsf;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Rhayan
 */
@Named
@RequestScoped
public class DateServiceClient {
    private String timenow;
    private Date datenow;

    public Date getDatenow() {
        DateFormat formater = new SimpleDateFormat("MM/dd/YYYY HH:mm");
        Date today = null;
        try {
            today = formater.parse(getTimestamp());
        } catch (ParseException ex) {
            Logger.getLogger(DateServiceClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return today;
    }

    public void setDatenow(Date datenow) {
        this.datenow = datenow;
    }

    public String getTimenow() {
        return getTimestamp();
    }

    private static String getTimestamp() {
        controllers.TimestampWS_Service service = new controllers.TimestampWS_Service();
        controllers.TimestampWS port = service.getTimestampWSPort();
        return port.getTimestamp();
    }
    
    
}
