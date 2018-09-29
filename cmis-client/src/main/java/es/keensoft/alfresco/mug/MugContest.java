package es.keensoft.alfresco.mug;

import java.util.ArrayList;
import java.util.List;

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
		
		// Your code here!
		
		return pdfDocuments;
		
	}

}
