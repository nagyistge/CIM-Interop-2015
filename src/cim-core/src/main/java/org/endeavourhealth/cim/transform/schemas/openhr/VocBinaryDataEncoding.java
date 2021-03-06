
package org.endeavourhealth.cim.transform.schemas.openhr;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for voc.BinaryDataEncoding.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="voc.BinaryDataEncoding">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="TXT"/>
 *     &lt;enumeration value="B64"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "voc.BinaryDataEncoding", namespace = "http://www.e-mis.com/emisopen")
@XmlEnum
public enum VocBinaryDataEncoding {


    /**
     * Text
     * 
     */
    TXT("TXT"),

    /**
     * Base 64
     * 
     */
    @XmlEnumValue("B64")
    B_64("B64");
    private final String value;

    VocBinaryDataEncoding(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VocBinaryDataEncoding fromValue(String v) {
        for (VocBinaryDataEncoding c: VocBinaryDataEncoding.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
