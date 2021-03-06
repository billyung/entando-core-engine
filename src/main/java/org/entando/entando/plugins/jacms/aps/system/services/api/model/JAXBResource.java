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
package org.entando.entando.plugins.jacms.aps.system.services.api.model;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.exception.ApsSystemException;
import com.agiletec.aps.system.services.category.Category;
import com.agiletec.aps.system.services.category.ICategoryManager;
import com.agiletec.plugins.jacms.aps.system.services.resource.model.BaseResourceDataBean;
import com.agiletec.plugins.jacms.aps.system.services.resource.model.ResourceInterface;

import java.io.*;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author E.Santoboni
 */
@XmlRootElement(name = "resource")
@XmlType(propOrder = {"id", "typeCode", "description", "mainGroup", "fileName", "categories", "base64"})
public class JAXBResource {
	
	public JAXBResource() {}
	
	public JAXBResource(ResourceInterface resource) throws ApsSystemException {
		try {
			this.setDescription(resource.getDescr());
			this.setFileName(resource.getMasterFileName());
			this.setId(resource.getId());
			this.setMainGroup(resource.getMainGroup());
			this.setTypeCode(resource.getType());
			List<Category> resourceCategories = resource.getCategories();
			if (null != resourceCategories && !resourceCategories.isEmpty()) {
				List<String> categories = new ArrayList<String>();
				for (int i = 0; i < resourceCategories.size(); i++) {
					Category category = resourceCategories.get(i);
					if (null != category) {
						categories.add(category.getCode());
					}
				}
				this.setCategories(categories);
			}
			File file = resource.getFile();
			if (null != file && file.exists()) {
				byte[] bytes = this.fileToByteArray(file);
				this.setBase64(bytes);
			}
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "JAXBResource");
			throw new ApsSystemException("Error creating jaxb resource", t);
		}
	}
	
	private byte[] fileToByteArray(File file) throws Throwable {
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		try {
			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				bos.write(buf, 0, readNum);
			}
		} catch (IOException ex) {
			ApsSystemUtils.logThrowable(ex, this, "fileToByteArray");
			throw new ApsSystemException("Error creating byte array", ex);
		}
		return bos.toByteArray();
	}
	
	public BaseResourceDataBean createBataBean(ICategoryManager categoryManager) throws Throwable {
		BaseResourceDataBean bean = new BaseResourceDataBean();
		if (null != this.getCategories()) {
			List<Category> categories = new ArrayList<Category>();
			for (int i = 0; i < this.getCategories().size(); i++) {
				String categoryCode = this.getCategories().get(i);
				Category category = categoryManager.getCategory(categoryCode);
				if (null != category) {
					categories.add(category);
				}
			}
			bean.setCategories(categories);
		}
		bean.setDescr(this.getDescription());
		bean.setFileName(this.getFileName());
		bean.setMainGroup(this.getMainGroup());
		FileNameMap fileNameMap = URLConnection.getFileNameMap();
		String mimeType = fileNameMap.getContentTypeFor(this.getFileName());
		bean.setMimeType(mimeType);
		bean.setResourceType(this.getTypeCode());
		bean.setResourceId(this.getId());
		if (null != this.getBase64()) {
			File file = this.byteArrayToFile();
			bean.setFile(file);
		}
		return bean;
	}
	
	private File byteArrayToFile() throws Throwable {
		String tempDir = System.getProperty("java.io.tmpdir");
		File file = new File(tempDir + File.separator + this.getFileName());
		InputStream inputStream = new ByteArrayInputStream(this.getBase64());
		try {
			OutputStream out = new FileOutputStream(file);
			byte buf[] = new byte[1024];
			int len;
			while ((len = inputStream.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			inputStream.close();
		} catch (IOException ex) {
			ApsSystemUtils.logThrowable(ex, this, "byteArrayToFile");
			throw new ApsSystemException("Error creating file from byte array", ex);
		}
		return file;
	}
	
	@XmlElement(name = "id", required = true)
	public String getId() {
		return _id;
	}
	public void setId(String id) {
		this._id = id;
	}
	
	@XmlElement(name = "typeCode", required = true)
	public String getTypeCode() {
		return _typeCode;
	}
	public void setTypeCode(String typeCode) {
		this._typeCode = typeCode;
	}
	
	@XmlElement(name = "description", required = true)
	public String getDescription() {
		return _description;
	}
	public void setDescription(String description) {
		this._description = description;
	}
	
	@XmlElement(name = "mainGroup", required = true)
	public String getMainGroup() {
		return _mainGroup;
	}
	public void setMainGroup(String mainGroup) {
		this._mainGroup = mainGroup;
	}
	
	@XmlElement(name = "fileName", required = true)
	public String getFileName() {
		return _fileName;
	}
	public void setFileName(String fileName) {
		this._fileName = fileName;
	}
	
	@XmlElement(name = "category", required = true)
    @XmlElementWrapper(name = "categories")
	public List<String> getCategories() {
		return _categories;
	}
	public void setCategories(List<String> categories) {
		this._categories = categories;
	}
	
	@XmlElement(name = "base64", required = true)
	public byte[] getBase64() {
		return _base64;
	}
	public void setBase64(byte[] base64) {
		this._base64 = base64;
	}
	
	private String _id;
	private String _typeCode;
	private String _description;
	private String _mainGroup;
	private String _fileName;
	private List<String> _categories;
	private byte[] _base64;
	
}