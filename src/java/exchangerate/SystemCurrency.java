/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package exchangerate;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rhayan
 * SystemCurrency class adapting the xml conversion
 */
@XmlRootElement(name="conversion")
public class SystemCurrency {
    String symbol;
    String name;
    BigDecimal aDollarToLocal;
    BigDecimal aLocalToDollar;

    
    
    public SystemCurrency(){
        
    }

    public SystemCurrency(String symbol, String name, BigDecimal aDollarToLocal, BigDecimal aLocalToDollar) {
        this.symbol = symbol;
        this.name = name;
        this.aDollarToLocal = aDollarToLocal;
        this.aLocalToDollar = aLocalToDollar;
    }
    
    

    @XmlAttribute
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public BigDecimal getaDollarToLocal() {
        return aDollarToLocal;
    }

    public void setaDollarToLocal(BigDecimal aDollarToLocal) {
        this.aDollarToLocal = aDollarToLocal;
    }
    
    @XmlAttribute
    public BigDecimal getaLocalToDollar() {
        return aLocalToDollar;
    }

    public void setaLocalToDollar(BigDecimal aLocalToDollar) {
        this.aLocalToDollar = aLocalToDollar;
    }
    
}