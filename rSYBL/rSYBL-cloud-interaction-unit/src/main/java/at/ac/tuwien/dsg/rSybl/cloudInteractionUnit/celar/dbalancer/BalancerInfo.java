//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.09.09 at 04:34:34 PM CEST 
//


package at.ac.tuwien.dsg.rSybl.cloudInteractionUnit.celar.dbalancer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for balancerInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="balancerInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="unbalancedRatio" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="balancedDatabases" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "balancerInfo", propOrder = {
    "unbalancedRatio",
    "balancedDatabases"
})
public class BalancerInfo {

    protected double unbalancedRatio;
    protected int balancedDatabases;

    /**
     * Gets the value of the unbalancedRatio property.
     * 
     */
    public double getUnbalancedRatio() {
        return unbalancedRatio;
    }

    /**
     * Sets the value of the unbalancedRatio property.
     * 
     */
    public void setUnbalancedRatio(double value) {
        this.unbalancedRatio = value;
    }

    /**
     * Gets the value of the balancedDatabases property.
     * 
     */
    public int getBalancedDatabases() {
        return balancedDatabases;
    }

    /**
     * Sets the value of the balancedDatabases property.
     * 
     */
    public void setBalancedDatabases(int value) {
        this.balancedDatabases = value;
    }

}
