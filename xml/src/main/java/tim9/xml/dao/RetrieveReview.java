package tim9.xml.dao;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;

import org.exist.xmldb.EXistResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import rs.ac.uns.msb.Review;
import tim9.xml.exception.EntityNotFound;
import tim9.xml.util.AuthenticationUtilities;
import tim9.xml.util.AuthenticationUtilities.ConnectionProperties;


public class RetrieveReview {
    
    /**
     * args[0] Should be the collection ID to access
     * args[1] Should be the document ID to store in the collection
     */
    public static String run(String collectionId, String documentId) throws Exception {
       
    	ConnectionProperties conn = AuthenticationUtilities.loadProperties();
        
        // initialize database driver
        Class<?> cl = Class.forName(conn.driver);
        
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        
        DatabaseManager.registerDatabase(database);
        
        Collection col = null;
        XMLResource res = null;
        boolean notFound = false;
        try {    
            // get the collection
            col = DatabaseManager.getCollection(conn.uri + collectionId);
            col.setProperty(OutputKeys.INDENT, "yes");
            
            res = (XMLResource)col.getResource(documentId);
            
            if(res == null) {
                notFound = true;
            } else {
            	
                JAXBContext context = JAXBContext.newInstance("rs.ac.uns.msb");
    			
    			Unmarshaller unmarshaller = context.createUnmarshaller();
    			
    			Review review = (Review) unmarshaller.unmarshal(res.getContentAsDOM());
    			
    			if(review == null) {
    				throw new Exception("Unmarshaling failed");
    			}
    			
    			return (String)res.getContent();
            }
        } finally {
            //don't forget to clean up!
            
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
        
        if(notFound) {
        	throw new EntityNotFound(documentId);
        }
        return null;
    }
}
