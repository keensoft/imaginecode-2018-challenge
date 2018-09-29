package es.keensoft.alfresco;

import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.commons.data.PropertyData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.keensoft.alfresco.cmis.CmisClient;

/**
 * CMIS Implementation for the actions invoked from command line
 */
@Component
public class CmisAction {
	
	private static final Logger logger = LoggerFactory.getLogger(CmisAction.class);
	
	@Autowired
	CmisClient cmisClient;

	public void uploadBooks() throws Exception {
		
        Folder sharedFolder = (Folder) cmisClient.getSession().getObjectByPath("/Shared");
        
        Document document = cmisClient.createDoc(sharedFolder, "cryptography.pdf", "open-library/cryptography.pdf");
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("ksic:Author", "Frederick Edward Hulme");
        properties.put("ksic:Area", "Cryptography");
        properties.put("ksic:Year", 1898l);
        properties.put("ksic:Topic", "The history, principles, and practice of Cipher-Writing");
		cmisClient.addAspect(document, "P:ksic:Thesis", properties);
        logger.info("Book " + document.getName() + " uploaded!");
		
        document = cmisClient.createDoc(sharedFolder, "command.txt", "open-library/command.txt");
        properties = new HashMap<String, Object>();
        properties.put("ksic:Author", "Neal Stephenson");
        properties.put("ksic:Area", "Operative System");
        properties.put("ksic:Year", 1999l);
        properties.put("ksic:Topic", "In the Beginning was the Command Line");
		cmisClient.addAspect(document, "P:ksic:Thesis", properties);
        logger.info("Book " + document.getName() + " uploaded!");

        document = cmisClient.createDoc(sharedFolder, "evaluationai.pdf", "open-library/evaluationai.pdf");
        properties = new HashMap<String, Object>();
        properties.put("ksic:Author", "Kenneth Levin");
        properties.put("ksic:Area", "Artificial Intelligence");
        properties.put("ksic:Year", 1972l);
        properties.put("ksic:Topic", "The evaluation of air-to-air combat situations by navy fighter pilots with artificial intelligence applications");
		cmisClient.addAspect(document, "P:ksic:Thesis", properties);
        logger.info("Book " + document.getName() + " uploaded!");
		
        document = cmisClient.createDoc(sharedFolder, "introduction-cryptography.pdf", "open-library/introduction-cryptography.pdf");
        properties = new HashMap<String, Object>();
        properties.put("ksic:Author", "Kenneth H. Rosen");
        properties.put("ksic:Area", "Cryptography");
        properties.put("ksic:Year", 2007l);
        properties.put("ksic:Topic", "An introduction to Cryptography");
		cmisClient.addAspect(document, "P:ksic:Thesis", properties);
        logger.info("Book " + document.getName() + " uploaded!");
		
        document = cmisClient.createDoc(sharedFolder, "neuralnetworks.pdf", "open-library/neuralnetworks.pdf");
        properties = new HashMap<String, Object>();
        properties.put("ksic:Author", "William Christopher Pritchett");
        properties.put("ksic:Area", "Artificial Intelligence");
        properties.put("ksic:Year", 1998l);
        properties.put("ksic:Topic", "Neural Networks for Classification");
		cmisClient.addAspect(document, "P:ksic:Thesis", properties);
        logger.info("Book " + document.getName() + " uploaded!");
		
	}
	
	public void dumpInfo() throws Exception {
		
		ItemIterable<QueryResult> results = cmisClient.getSession().query("SELECT * FROM ksic:Thesis", false);

		for(QueryResult hit: results) {  
		    for(PropertyData<?> property: hit.getProperties()) {

		        String queryName = property.getQueryName();
		        Object value = property.getFirstValue();

		        logger.info(queryName + ": " + value);
		    }
		    logger.info("--------------------------------------");
		}
		
	}
	
	public void search(String criteria) throws Exception {
		
		ItemIterable<QueryResult> results = cmisClient.getSession().query("SELECT * FROM ksic:Thesis WHERE " + criteria, false);

		for(QueryResult hit: results) {  
		    for(PropertyData<?> property: hit.getProperties()) {

		        String queryName = property.getQueryName();
		        Object value = property.getFirstValue();

		        logger.info(queryName + ": " + value);
		    }
		    logger.info("--------------------------------------");
		}
		
	}
	
}
