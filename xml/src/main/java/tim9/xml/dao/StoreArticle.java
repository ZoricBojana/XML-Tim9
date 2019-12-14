package tim9.xml.dao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.exist.xmldb.EXistResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import rs.ac.uns.msb.ScientificArticle;
import tim9.xml.util.AuthenticationUtilities;
import tim9.xml.util.AuthenticationUtilities.ConnectionProperties;
import tim9.xml.util.NSPrefixMapper;

public class StoreArticle {

	private static ConnectionProperties conn;
	
	public static void main(String[] args) throws Exception {
		StoreArticle.run(conn = AuthenticationUtilities.loadProperties(), "/db/sample/articles", "1.xml", "data/instance1.xml");
	}
	
    /**
     * conn XML DB connection properties
     * collectionId Should be the collection ID to access
     * documentId Should be the document ID to store in the collection
     * filePath Should be the document file path  
     */
	public static void run(ConnectionProperties conn, String collectionId, String documentId, String filePath) throws Exception {
	       
    	System.out.println("[INFO] " + StoreArticle.class.getSimpleName());
    	
    	// initialize collection and document identifiers
       /* String collectionId = null;
		String documentId = null; 
		String filePath = null;
        
        if (args.length == 3) {
        	
        	System.out.println("[INFO] Passing the arguments... ");
        	
        	collectionId = args[0];
        	documentId = args[1];
        	
        	filePath = args[2];
        } else {
        	
        	System.out.println("[INFO] Using defaults.");
        	
        	collectionId = "/db/sample/library";
        	documentId = "2.xml";
        	
        	filePath = "data/books.xml";
        	
        }*/

        System.out.println("\t- collection ID: " + collectionId);
    	System.out.println("\t- document ID: " + documentId);
    	System.out.println("\t- file path: " + filePath + "\n");
        
        // initialize database driver
    	System.out.println("[INFO] Loading driver class: " + conn.driver);
    	Class<?> cl = Class.forName(conn.driver);
        
        
        // encapsulation of the database driver functionality
    	Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        
        // entry point for the API which enables you to get the Collection reference
        DatabaseManager.registerDatabase(database);
        
        // a collection of Resources stored within an XML database
        Collection col = null;
        XMLResource res = null;
        OutputStream os = new ByteArrayOutputStream();
        
        try { 
        	
        	System.out.println("[INFO] Retrieving the collection: " + collectionId);
            col = getOrCreateCollection(collectionId);
            
            /*
             *  create new XMLResource with a given id
             *  an id is assigned to the new resource if left empty (null)
             */
            System.out.println("[INFO] Inserting the document: " + documentId);
            res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE); 
            
            System.out.println("[INFO] Unmarshalling XML document to an JAXB instance: ");
            JAXBContext context = JAXBContext.newInstance("rs.ac.uns.msb");
			
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			ScientificArticle article = (ScientificArticle) unmarshaller.unmarshal(new File("data/instance1.xml"));
			//bookstore.getBook().add(createTestBook());
			System.out.println(article);
			
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NSPrefixMapper());
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			
			// marshal the contents to an output stream
			marshaller.marshal(article, os);
			
			// link the stream to the XML resource
            res.setContent(os);
            System.out.println("[INFO] Storing the document: " + res.getId());
            
            col.storeResource(res);
            System.out.println("[INFO] Done.");
            
        } finally {
            
        	//don't forget to cleanup
            if(res != null) {
                try { 
                	((EXistResource)res).freeResources(); 
                } catch (XMLDBException xe) {
                	xe.printStackTrace();
                }
            }
            
            if(col != null) {
                try { 
                	col.close(); 
                } catch (XMLDBException xe) {
                	xe.printStackTrace();
                }
            }
        }
    }
    
    private static Collection getOrCreateCollection(String collectionUri) throws XMLDBException {
        return getOrCreateCollection(collectionUri, 0);
    }
    
    private static Collection getOrCreateCollection(String collectionUri, int pathSegmentOffset) throws XMLDBException {
        
        Collection col = DatabaseManager.getCollection(conn.uri + collectionUri, conn.user, conn.password);
        
        // create the collection if it does not exist
        if(col == null) {
        
         	if(collectionUri.startsWith("/")) {
                collectionUri = collectionUri.substring(1);
            }
            
        	String pathSegments[] = collectionUri.split("/");
            
        	if(pathSegments.length > 0) {
                StringBuilder path = new StringBuilder();
            
                for(int i = 0; i <= pathSegmentOffset; i++) {
                    path.append("/" + pathSegments[i]);
                }
                
                Collection startCol = DatabaseManager.getCollection(conn.uri + path, conn.user, conn.password);
                
                if (startCol == null) {
                	
                	// child collection does not exist
                    
                	String parentPath = path.substring(0, path.lastIndexOf("/"));
                    Collection parentCol = DatabaseManager.getCollection(conn.uri + parentPath, conn.user, conn.password);
                    
                    CollectionManagementService mgt = (CollectionManagementService) parentCol.getService("CollectionManagementService", "1.0");
                    
                    System.out.println("[INFO] Creating the collection: " + pathSegments[pathSegmentOffset]);
                    col = mgt.createCollection(pathSegments[pathSegmentOffset]);
                    
                    col.close();
                    parentCol.close();
                    
                } else {
                    startCol.close();
                }
            }
            return getOrCreateCollection(collectionUri, ++pathSegmentOffset);
        } else {
            return col;
        }
    }
}
