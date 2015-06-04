package org.endeavourhealth.cim.transform;

import org.endeavourhealth.cim.Registry;

public class TransformerFactory {
    public static Transformer getTransformerForService(String odsCode) throws Exception {
        String serviceTransformerTypeName = Registry.getTransformerTypeNameForService(odsCode);

        if (serviceTransformerTypeName == null)
            return null;

        try {
            return (Transformer)Class.forName(serviceTransformerTypeName).newInstance();
        }
        catch (Exception e) {
            throw new Exception("Could not load data transformer", e);
        }
    }
}
