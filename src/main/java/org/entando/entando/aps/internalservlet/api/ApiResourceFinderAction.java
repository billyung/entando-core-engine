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
package org.entando.entando.aps.internalservlet.api;

import org.entando.entando.aps.system.services.api.model.ApiMethod;
import org.entando.entando.apsadmin.api.*;
import org.entando.entando.aps.system.services.api.model.ApiResource;

/**
 * @author E.Santoboni
 */
public class ApiResourceFinderAction extends AbstractApiFinderAction implements IApiResourceFinderAction {
    
	protected boolean includeIntoMapping(ApiResource apiResource) {
		ApiMethod GETMethod = apiResource.getGetMethod();
		ApiMethod POSTMethod = apiResource.getPostMethod();
		ApiMethod PUTMethod = apiResource.getPutMethod();
		ApiMethod DELETEMethod = apiResource.getDeleteMethod();
		return (null != GETMethod && GETMethod.isActive()) || (null != POSTMethod && POSTMethod.isActive()) || 
				(null != PUTMethod && PUTMethod.isActive()) || (null != DELETEMethod && DELETEMethod.isActive());
	}
	
}