/*
 *
 * Copyright 2005 AgileTec s.r.l. (http://www.agiletec.it) All rights reserved.
 *
 * This file is part of jAPS software.
 * jAPS is a free software; 
 * you can redistribute it and/or modify it
 * under the terms of the GNU General Public License (GPL) as published by the Free Software Foundation; version 2.
 * 
 * See the file License for the specific language governing permissions   
 * and limitations under the License
 * 
 * 
 * 
 * Copyright 2005 AgileTec s.r.l. (http://www.agiletec.it) All rights reserved.
 *
 */
package com.agiletec.aps.system.common.entity.model.attribute;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.entando.entando.aps.system.services.api.model.CDataXmlTypeAdapter;

/**
 * @author E.Santoboni
 */
public class JAXBHypertextAttribute extends DefaultJAXBAttribute {
    
    @XmlElement(name = "value", required = false)
    public HashMap getValues() {
        Object value = super.getValue();
        if (value instanceof HashMap) {
            return (HashMap) value;
        }
        return null;
    }
    
    @XmlJavaTypeAdapter(CDataXmlTypeAdapter.class)
    @XmlElement(name = "value", required = false)
    public String getValue() {
        Object value = super.getValue();
        if (value instanceof String) {
            return (String) value;
        }
        return null;
    }
    
}