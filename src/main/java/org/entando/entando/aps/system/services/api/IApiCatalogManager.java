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
package org.entando.entando.aps.system.services.api;

import java.util.List;
import java.util.Map;

import org.entando.entando.aps.system.services.api.model.ApiMethod;
import org.entando.entando.aps.system.services.api.model.ApiService;

import com.agiletec.aps.system.exception.ApsSystemException;

/**
 * @author E.Santoboni
 */
public interface IApiCatalogManager {
    
    /**
     * Return the api related whith the given showlet, if exist.
     * @param showletCode The showlet code.
     * @return The api method related.
     * @throws ApsSystemException In case of error.
     */
    public ApiMethod getRelatedMethod(String showletCode) throws ApsSystemException;
    
    public Map<String, ApiMethod> getRelatedShowletMethods() throws ApsSystemException;
    
    /**
     * Return the map of GET methods indexed by api method name.
     * @return The map of GET methods indexed by api method name.
     * @throws ApsSystemException In case of error
     * @deprecated use getMethods(ApiMethod.HttpMethod) method
     */
    public Map<String, ApiMethod> getMethods() throws ApsSystemException;
    
    public List<ApiMethod> getMethods(ApiMethod.HttpMethod httpMethod) throws ApsSystemException;
    
    public Map<ApiMethod.HttpMethod, List<ApiMethod>> getApiRestFulMethods() throws ApsSystemException;
    
    /**
     * Return a GET methods by name.
     * @return a GET methods.
     * @throws ApsSystemException In case of error
     * @deprecated use getMethod(ApiMethod.HttpMethod, methodName, namespace) method
     */
    public ApiMethod getMethod(String resourceName) throws ApsSystemException;
    
    public ApiMethod getMethod(ApiMethod.HttpMethod httpMethod, String resourceName) throws ApsSystemException;
    
    public Map<String, ApiService> getApiServices() throws ApsSystemException;

    public Map<String, ApiService> getApiServices(String tag, Boolean myentando) throws ApsSystemException;

    public ApiService getApiService(String key) throws ApsSystemException;

    public void updateApiStatus(ApiMethod apiMethod) throws ApsSystemException;

    public void saveService(ApiService service) throws ApsSystemException;

    public void deleteService(String key) throws ApsSystemException;

    public void updateApiServiceStatus(ApiService service) throws ApsSystemException;
    
}