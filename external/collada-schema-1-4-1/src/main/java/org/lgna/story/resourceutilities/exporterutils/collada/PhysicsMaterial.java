
package org.lgna.story.resourceutilities.exporterutils.collada;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
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
 *       &lt;sequence>
 *         &lt;element ref="{http://www.collada.org/2005/11/COLLADASchema}asset" minOccurs="0"/>
 *         &lt;element name="technique_common">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="dynamic_friction" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/>
 *                   &lt;element name="restitution" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/>
 *                   &lt;element name="static_friction" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{http://www.collada.org/2005/11/COLLADASchema}technique" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.collada.org/2005/11/COLLADASchema}extra" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "asset",
    "techniqueCommon",
    "technique",
    "extra"
})
@XmlRootElement(name = "physics_material", namespace = "http://www.collada.org/2005/11/COLLADASchema")
public class PhysicsMaterial {

    @XmlElement(namespace = "http://www.collada.org/2005/11/COLLADASchema")
    protected Asset asset;
    @XmlElement(name = "technique_common", namespace = "http://www.collada.org/2005/11/COLLADASchema", required = true)
    protected PhysicsMaterial.TechniqueCommon techniqueCommon;
    @XmlElement(namespace = "http://www.collada.org/2005/11/COLLADASchema")
    protected List<Technique> technique;
    @XmlElement(namespace = "http://www.collada.org/2005/11/COLLADASchema")
    protected List<Extra> extra;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "name")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String name;

    /**
     * 
     * 							The physics_material element may contain an asset element.
     * 						
     * 
     * @return
     *     possible object is
     *     {@link Asset }
     *     
     */
    public Asset getAsset() {
        return asset;
    }

    /**
     * Sets the value of the asset property.
     * 
     * @param value
     *     allowed object is
     *     {@link Asset }
     *     
     */
    public void setAsset(Asset value) {
        this.asset = value;
    }

    /**
     * Gets the value of the techniqueCommon property.
     * 
     * @return
     *     possible object is
     *     {@link PhysicsMaterial.TechniqueCommon }
     *     
     */
    public PhysicsMaterial.TechniqueCommon getTechniqueCommon() {
        return techniqueCommon;
    }

    /**
     * Sets the value of the techniqueCommon property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhysicsMaterial.TechniqueCommon }
     *     
     */
    public void setTechniqueCommon(PhysicsMaterial.TechniqueCommon value) {
        this.techniqueCommon = value;
    }

    /**
     * 
     * 							This element may contain any number of non-common profile techniques.
     * 						Gets the value of the technique property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the technique property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTechnique().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Technique }
     * 
     * 
     */
    public List<Technique> getTechnique() {
        if (technique == null) {
            technique = new ArrayList<Technique>();
        }
        return this.technique;
    }

    /**
     * 
     * 							The extra element may appear any number of times.
     * 						Gets the value of the extra property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extra property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtra().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Extra }
     * 
     * 
     */
    public List<Extra> getExtra() {
        if (extra == null) {
            extra = new ArrayList<Extra>();
        }
        return this.extra;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
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
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="dynamic_friction" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/>
     *         &lt;element name="restitution" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/>
     *         &lt;element name="static_friction" type="{http://www.collada.org/2005/11/COLLADASchema}TargetableFloat" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "dynamicFriction",
        "restitution",
        "staticFriction"
    })
    public static class TechniqueCommon {

        @XmlElement(name = "dynamic_friction", namespace = "http://www.collada.org/2005/11/COLLADASchema")
        protected TargetableFloat dynamicFriction;
        @XmlElement(namespace = "http://www.collada.org/2005/11/COLLADASchema")
        protected TargetableFloat restitution;
        @XmlElement(name = "static_friction", namespace = "http://www.collada.org/2005/11/COLLADASchema")
        protected TargetableFloat staticFriction;

        /**
         * Gets the value of the dynamicFriction property.
         * 
         * @return
         *     possible object is
         *     {@link TargetableFloat }
         *     
         */
        public TargetableFloat getDynamicFriction() {
            return dynamicFriction;
        }

        /**
         * Sets the value of the dynamicFriction property.
         * 
         * @param value
         *     allowed object is
         *     {@link TargetableFloat }
         *     
         */
        public void setDynamicFriction(TargetableFloat value) {
            this.dynamicFriction = value;
        }

        /**
         * Gets the value of the restitution property.
         * 
         * @return
         *     possible object is
         *     {@link TargetableFloat }
         *     
         */
        public TargetableFloat getRestitution() {
            return restitution;
        }

        /**
         * Sets the value of the restitution property.
         * 
         * @param value
         *     allowed object is
         *     {@link TargetableFloat }
         *     
         */
        public void setRestitution(TargetableFloat value) {
            this.restitution = value;
        }

        /**
         * Gets the value of the staticFriction property.
         * 
         * @return
         *     possible object is
         *     {@link TargetableFloat }
         *     
         */
        public TargetableFloat getStaticFriction() {
            return staticFriction;
        }

        /**
         * Sets the value of the staticFriction property.
         * 
         * @param value
         *     allowed object is
         *     {@link TargetableFloat }
         *     
         */
        public void setStaticFriction(TargetableFloat value) {
            this.staticFriction = value;
        }

    }

}
