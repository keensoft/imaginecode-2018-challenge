package es.keensoft.alfresco.mug.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.keensoft.alfresco.AlfrescoCmisClientApplication;
import es.keensoft.alfresco.mug.MugContest;

/**
 * JUnit Test to verify results for Mug Contest
 * This class shouldn't be modified
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AlfrescoCmisClientApplication.class, loader=SpringApplicationContextLoader.class)
public class TestMugContest {

	@Autowired
	MugContest mugContest;
	
	/**
	 * Method to verify MugContest.getPdfDocuments() results according to Mug Contest rules
	 * DO NOT MODIFY THIS METHOD!
	 */
	@Test
	public void getPdfDocuments() {
		
		List<String> pdfDocuments = mugContest.getPdfDocuments();
		
		assertNotNull(pdfDocuments);
		
		assertEquals(4, pdfDocuments.size());
		
		assertEquals("evaluationai", pdfDocuments.get(1).substring(0, "evaluationai".length()));
		assertEquals("cryptography", pdfDocuments.get(0).substring(0, "cryptography".length()));
		assertEquals("neuralnetworks", pdfDocuments.get(3).substring(0, "neuralnetworks".length()));
		assertEquals("introduction", pdfDocuments.get(2).substring(0, "introduction".length()));
		
	}

}
