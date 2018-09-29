package es.keensoft.alfresco.cmis;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * CMIS Client Implementation
 */
@Component
public class CmisClient {
	
	// The value of the parameters is coming from "resources/application.properties" file
	@Value("${cmis.server}")
	private String server;
	@Value("${cmis.user.name}")
	private String userName;
	@Value("${cmis.user.pass}")
	private String userPass;
	@Value("${alfresco.version}")
	private String alfrescoVersion;
	
	private Session session;
	
	public static CmisClient getInstance(CmisClient cmisClient) {
		CmisClient cc = new CmisClient();
		cc.server = cmisClient.server;
		cc.userName = cmisClient.userName;
		cc.userPass = cmisClient.userPass;
		cc.alfrescoVersion = cmisClient.alfrescoVersion;
		return cc;
	}
	
	public Session getSession() {
		if (session == null) { 			
			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put(SessionParameter.USER, userName);
			parameter.put(SessionParameter.PASSWORD, userPass);
			parameter.put(SessionParameter.BROWSER_URL, server + "/alfresco/api/-default-/public/cmis/versions/1.1/browser");
			parameter.put(SessionParameter.BINDING_TYPE, BindingType.BROWSER.value());
			SessionFactory factory = SessionFactoryImpl.newInstance();
			session = factory.getRepositories(parameter).get(0).createSession();
	    }
	    return session;
	}
	
	public ItemIterable<CmisObject> getRootFolderContents() {
		Folder rootFolder = (Folder) getSession().getObjectByPath("/");
		return rootFolder.getChildren();
	}

	public Document addAspect(Document document, String aspect, Map<String, Object> properties) {
		
		List<Object> aspects = document.getProperty(PropertyIds.SECONDARY_OBJECT_TYPE_IDS).getValues();
		
		if (!aspects.contains(aspect)) {
			aspects.add(aspect);
			properties.put(PropertyIds.SECONDARY_OBJECT_TYPE_IDS, aspects);
			document.updateProperties(properties);
		}
		
		return document;
		
	}
	
    public Document createDoc(Folder folder, String docName, String sourceFileName) throws Exception {
		
		String timeStamp = new Long(System.nanoTime()).toString();
		String fileName = docName.substring(0,  docName.lastIndexOf(".")) + 
				" (" + timeStamp + ")" +
				docName.substring(docName.lastIndexOf(".") , docName.length());
		
		Map <String, Object> properties = new HashMap<String, Object>();
	
		properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
		properties.put(PropertyIds.NAME, fileName);
		final ContentStream contentStream = 
				createContentStream(properties.get(PropertyIds.NAME).toString(), sourceFileName);
		Document doc = folder.createDocument(properties, contentStream, VersioningState.MAJOR);
		
		doc.updateProperties(properties);
		
		return doc;
		
	  }
    
	public ContentStream createContentStream(String fileName, String sourceFileName) throws Exception {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream fileInputStream = classloader.getResourceAsStream(sourceFileName);
		return session.getObjectFactory().createContentStream(fileName, fileInputStream.available(), null, fileInputStream);
	}
	
}
