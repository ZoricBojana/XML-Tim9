package tim9.xml.dao;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XMLResource;

import rs.ac.uns.msb.Person;
import tim9.xml.util.AuthenticationUtilities;
import tim9.xml.util.AuthenticationUtilities.ConnectionProperties;

public class RetrievePerson {

    public static Person getByUsername(String username, String collectionId) throws Exception {
        
    	ConnectionProperties conn = AuthenticationUtilities.loadProperties();
        
        // initialize database driver
        Class<?> cl = Class.forName(conn.driver);
        
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        
        DatabaseManager.registerDatabase(database);
        
        XMLResource res = null;
        
        String xPathExp = String.format("doc(\"persons\")//person[@username='%s']", username);
        //ResourceSet resultSet = RetriveData.executeXPathExpression(personCollectionId, xPathExp, TARGET_NAMESPACE);
        ResourceSet resultSet = XPath.run(AuthenticationUtilities.loadProperties(), collectionId, xPathExp);
        
        if (resultSet == null) {
            return null;
        }

        ResourceIterator i = resultSet.getIterator();

        if (i.hasMoreResources()) {
            res = (XMLResource) i.nextResource();
            
            JAXBContext context = JAXBContext.newInstance("rs.ac.uns.msb");
			
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			Person person = (Person) unmarshaller.unmarshal(res.getContentAsDOM());
			
			if(person == null) {
				throw new Exception("Unmarshaling failed");
			}
			
			return person;
        }else {
        	return null;
        }
       
       /* try {    
            // get the collection
            col = DatabaseManager.getCollection(conn.uri + collectionId);
            col.setProperty(OutputKeys.INDENT, "yes");
            
            res = (XMLResource)col.getResource(documentId);
            
            if(res == null) {
                notFound = true;
            } else {
            	
                JAXBContext context = JAXBContext.newInstance("rs.ac.uns.msb");
    			
    			Unmarshaller unmarshaller = context.createUnmarshaller();
    			
    			Person person = (Person) unmarshaller.unmarshal(res.getContentAsDOM());
    			
    			if(person == null) {
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
        }*/
       // return null;
    }
}
