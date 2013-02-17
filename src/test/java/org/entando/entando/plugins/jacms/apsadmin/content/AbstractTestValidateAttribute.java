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
package org.entando.entando.plugins.jacms.apsadmin.content;

import com.agiletec.aps.system.SystemConstants;
import com.agiletec.aps.system.common.entity.model.AttributeTracer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.agiletec.plugins.jacms.apsadmin.content.util.AbstractBaseTestContentAction;

import com.agiletec.aps.system.common.entity.model.EntitySearchFilter;
import com.agiletec.aps.system.services.group.Group;
import com.agiletec.aps.system.services.lang.ILangManager;
import com.agiletec.plugins.jacms.aps.system.services.content.IContentManager;
import com.agiletec.plugins.jacms.aps.system.services.content.model.Content;
import com.opensymphony.xwork2.Action;

/**
 * @author E.Santoboni
 */
public abstract class AbstractTestValidateAttribute extends AbstractBaseTestContentAction {
	
	protected ILangManager getLangManager() {
		return (ILangManager) super.getService(SystemConstants.LANGUAGE_MANAGER);
	}
	
	protected AttributeTracer getTracer() {
		AttributeTracer tracer = new AttributeTracer();
		tracer.setLang(this.getLangManager().getDefaultLang());
		return tracer;
	}
	
	protected Content executeCreateNewContent() throws Throwable {
		String result = this.executeCreateNewVoid(TEST_CONTENT_TYPE_CODE, 
				TEST_CONTENT_DESCRIPTION, Content.STATUS_DRAFT, Group.FREE_GROUP_NAME, "admin");
		assertEquals(Action.SUCCESS, result);
		Content contentOnSession = this.getContentOnEdit();
		assertNotNull(contentOnSession);
		return contentOnSession;
	}
	
	protected void initSaveContentAction() throws Throwable {
		this.initAction("/do/jacms/Content", "save");
	}
	
	protected void executeAction(String expectedResult) throws Throwable {
		String result = super.executeAction();
		assertEquals(expectedResult, result);
	}
	
	protected void checkFieldErrors(int singleFieldErrors, String formFieldName) {
		Map<String, List<String>> fieldErrors = this.getAction().getFieldErrors();
		//System.out.println(fieldErrors);
		if (0 == singleFieldErrors) {
			assertEquals(1, fieldErrors.size());
		} else {
			assertEquals(2, fieldErrors.size());
		}
		assertTrue(fieldErrors.containsKey("MARKER"));
		List<String> titleFieldErrors = fieldErrors.get(formFieldName);
		if (0 == singleFieldErrors) {
			assertNull(titleFieldErrors);
		} else {
			assertNotNull(titleFieldErrors);
			//System.out.println(titleFieldErrors);
			assertEquals(singleFieldErrors, titleFieldErrors.size());
		}
	}
	
	protected void deleteTestContent() throws Throwable {
		EntitySearchFilter filter = new EntitySearchFilter(IContentManager.CONTENT_DESCR_FILTER_KEY, false, TEST_CONTENT_DESCRIPTION, true);
		EntitySearchFilter[] filters = {filter};
		List<String> contentIds = this.getContentManager().loadWorkContentsId(filters, new ArrayList<String>());
		for (int i=0; i<contentIds.size(); i++) {
			String contentId = (String) contentIds.get(i);
			Content content = this.getContentManager().loadContent(contentId, false);
			this.getContentManager().deleteContent(content);
		}
	}
	
	protected static final String TEST_CONTENT_TYPE_CODE = "ALL";
	protected static final String TEST_CONTENT_DESCRIPTION = "*** CONTENT TEST DESCRIPTION ***";
	
}