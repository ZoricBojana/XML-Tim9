//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.12.13 at 05:53:01 PM CET 
//


package rs.ac.uns.msb;

import java.math.BigInteger;
import java.util.ArrayList;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{http://www.uns.ac.rs/MSB}b" minOccurs="0"/>
 *         &lt;element ref="{http://www.uns.ac.rs/MSB}i" minOccurs="0"/>
 *         &lt;element ref="{http://www.uns.ac.rs/MSB}u" minOccurs="0"/>
 *         &lt;element ref="{http://www.uns.ac.rs/MSB}list" minOccurs="0"/>
 *         &lt;element ref="{http://www.uns.ac.rs/MSB}new_lline" minOccurs="0"/>
 *       &lt;/choice>
 *       &lt;attribute ref="{http://www.uns.ac.rs/MSB}ID use="required""/>
 *       &lt;attribute ref="{http://www.uns.ac.rs/MSB}text_style"/>
 *       &lt;attribute name="text_size" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "content"
})
@XmlRootElement(name = "paragraph")
public class Paragraph {

    @XmlElementRefs({
        @XmlElementRef(name = "u", namespace = "http://www.uns.ac.rs/MSB", type = U.class),
        @XmlElementRef(name = "new_lline", namespace = "http://www.uns.ac.rs/MSB", type = JAXBElement.class),
        @XmlElementRef(name = "i", namespace = "http://www.uns.ac.rs/MSB", type = I.class),
        @XmlElementRef(name = "list", namespace = "http://www.uns.ac.rs/MSB", type = rs.ac.uns.msb.List.class),
        @XmlElementRef(name = "b", namespace = "http://www.uns.ac.rs/MSB", type = B.class)
    })
    @XmlMixed
    protected java.util.List<Object> content;
    @XmlAttribute(name = "ID", namespace = "http://www.uns.ac.rs/MSB", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "text_style", namespace = "http://www.uns.ac.rs/MSB")
    protected String textStyle;
    @XmlAttribute(name = "text_size")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger textSize;

    /**
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link U }
     * {@link rs.ac.uns.msb.List }
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link I }
     * {@link B }
     * {@link String }
     * 
     * 
     */
    public java.util.List<Object> getContent() {
        if (content == null) {
            content = new ArrayList<Object>();
        }
        return this.content;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the textStyle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTextStyle() {
        return textStyle;
    }

    /**
     * Sets the value of the textStyle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTextStyle(String value) {
        this.textStyle = value;
    }

    /**
     * Gets the value of the textSize property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTextSize() {
        return textSize;
    }

    /**
     * Sets the value of the textSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTextSize(BigInteger value) {
        this.textSize = value;
    }

}
