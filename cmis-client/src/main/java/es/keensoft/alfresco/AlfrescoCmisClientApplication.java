package es.keensoft.alfresco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;

/**
 * Main Class of the application, to be run from command line.
 * Accepting actions as parameters.
 */
@SpringBootApplication
public class AlfrescoCmisClientApplication implements CommandLineRunner {
	
	@Autowired
	CmisAction cmisAction;

	@Override
	public void run(String... args) throws Exception {
		
		String action = "";
		String criteria = "";
		
		PropertySource<?> ps = new SimpleCommandLinePropertySource(args);
		
		if (ps != null) {
			
			try {
				
				action = ps.getProperty("action").toString();
				if (action.equals("search")) {
					 criteria = ps.getProperty("criteria").toString();
				}
				
				switch (action) {
					case "upload": 
						cmisAction.uploadBooks();
						break;
					case "dump":
						cmisAction.dumpInfo();
						break;
					case "search":
						cmisAction.search(criteria);
						break;
					default: 
						System.err.println("Action (upload, dump, search) is required!");
				}

			} catch (Exception e) {
				System.err.println("SAMPLE PARAMS: --action=upload");
			}
		}
		
	}
	


	public static void main(String[] args) throws Exception {
		SpringApplication.run(AlfrescoCmisClientApplication.class, args);
	}

}
