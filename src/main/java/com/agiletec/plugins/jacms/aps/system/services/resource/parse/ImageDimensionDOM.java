/*
*
* Copyright 2012 Entando S.r.l. (http://www.entando.com) All rights reserved.
*
* This file is part of Entando software.
* Entando is a free software; 
* you can redistribute it and/or modify it
* under the terms of the GNU General Public License (GPL) as published by the Free Software Foundation; version 2.
* 
* See the file License for the specific language governing permissions   
* and limitations under the License
* 
* 
* 
* Copyright 2012 Entando S.r.l. (http://www.entando.com) All rights reserved.
*
*/
package com.agiletec.plugins.jacms.aps.system.services.resource.parse;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.exception.ApsSystemException;
import com.agiletec.plugins.jacms.aps.system.services.resource.model.ImageResourceDimension;

/**
 * Questa classe opera per caricare le diverse dimensioni di resize 
 * delle immagini che compongono le risorse immagini.
 * Il risultato è una mappa delle previste dimensioni di resize.
 * @author E.Santoboni
 */
public class ImageDimensionDOM {
	
	/**
	 * Costruttore della classe.
	 * @param xmlText La stringa xml da interpretare.
	 * @throws ApsSystemException In caso di errore 
	 * nell'interpretazione dell'xml di configurazione.
	 */
	public ImageDimensionDOM(String xmlText) throws ApsSystemException {
		this.decodeDOM(xmlText);
	}
	
	/**
	 * Restitusce la mappa delle dimensioni di resize previste.
	 * @return La mappa delle dimensioni di resize previste.
	 */
	public Map<Integer, ImageResourceDimension> getDimensions() {
		Map<Integer, ImageResourceDimension> dimensions = new HashMap<Integer, ImageResourceDimension>();
		List<Element> dimensionElements = _doc.getRootElement().getChildren(TAB_DIMENSION);
		if (null != dimensionElements && dimensionElements.size() > 0) {
			Iterator<Element> dimensionElementsIter = dimensionElements.iterator();
			while (dimensionElementsIter.hasNext()) {
				Element currentElement = (Element) dimensionElementsIter.next();
				ImageResourceDimension dimension = new ImageResourceDimension();
				Element idElement = currentElement.getChild(TAB_ID);
				if (null != idElement) {
					String id = idElement.getText();
					dimension.setIdDim(Integer.parseInt(id));
				}
				Element dimxElement = currentElement.getChild(TAB_DIMX);
				if (null != dimxElement) {
					String dimx = dimxElement.getText();
					dimension.setDimx(Integer.parseInt(dimx));
				}
				Element dimyElement = currentElement.getChild(TAB_DIMY);
				if (null != dimyElement) {
					String dimy = dimyElement.getText();
					dimension.setDimy(Integer.parseInt(dimy));
				}
				dimensions.put(new Integer(dimension.getIdDim()), dimension);
				ApsSystemUtils.getLogger().finest("Definita dimensione di resize: " + dimension.getIdDim());
			}
		}
		return dimensions;
	}
	
	private void decodeDOM(String xmlText) throws ApsSystemException {
		SAXBuilder builder = new SAXBuilder();
		builder.setValidation(false);
		StringReader reader = new StringReader(xmlText);
		try {
			_doc = builder.build(reader);
		} catch (Throwable t) {
			ApsSystemUtils.getLogger().severe("Errore nel parsing: " + t.getMessage());
			throw new ApsSystemException("Errore nel parsing della configurazione Dimensioni di resize", t);
		}
	}
	
	private Document _doc;
	private final String TAB_DIMENSION = "Dimension";
	private final String TAB_ID = "id";
	private final String TAB_DIMX = "dimx";
	private final String TAB_DIMY = "dimy";

}
