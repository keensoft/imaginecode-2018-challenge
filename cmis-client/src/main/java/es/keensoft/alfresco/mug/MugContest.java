/*
 * By C++Tangana:
 * - Alex Sanz
 * - Raul Logroño
 * - Eduardo Sanchez
 * - Pablo Orduna
 */
package es.keensoft.alfresco.mug;

import java.util.ArrayList;
import java.util.List;

import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.commons.data.PropertyData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.keensoft.alfresco.cmis.CmisClient;

/**
 * Template to be used as base Class to complete Mug Contest
 */
@Component
public class MugContest {
	
	@Autowired
	CmisClient cmisClient;
	
	/**
	 * Method to be implemented according to TestMugContest.getPdfDocuments() test conditions
	 */
	public List<String> getPdfDocuments() {
		
		List<String> pdfDocuments = new ArrayList<String>();
		
		// Hint 1: Use cmisClient.getSession().query(..) method
		// Hint 2: Documents are identified with the property cmis:name in CMIS
	//  OR (UPPER(cmis:name) LIKE UPPER('%cryptography%') OR (UPPER(cmis:name) LIKE UPPER('%neuralnetworks%') OR (UPPER(cmis:name) LIKE UPPER('%introduction%'))", false);	
		ItemIterable<QueryResult> results = cmisClient.getSession().query("SELECT cmis:name FROM ksic:Thesis",false);

		for(QueryResult hit: results) {  
		    for(PropertyData<?> property: hit.getProperties()) {
		    	String queryName = property.getFirstValue().toString();
		        //String queryName = property.getFirstValue();
		        char[] splitted = queryName.toCharArray();
		        int i = splitted.length;
		        if (splitted[i-1] == 'f' && splitted[i-2] == 'd' && splitted[i-3] == 'p') {
		        	pdfDocuments.add(queryName);
		        }
		    }
		}

		return pdfDocuments;
	}
}
