package tim9.xml.dao;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.exist.xmldb.EXistResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XUpdateQueryService;

import rs.ac.uns.msb.Person;
import rs.ac.uns.msb.Persons;
import tim9.xml.util.AuthenticationUtilities;
import tim9.xml.util.AuthenticationUtilities.ConnectionProperties;
import tim9.xml.util.NSPrefixMapper;
import tim9.xml.util.template.XUpdateTemplate;

public class StorePerson {
	private static ConnectionProperties conn;

	/**
	 * conn XML DB connection properties collectionId Should be the collection ID to
	 * access documentId Should be the document ID to store in the collection
	 * review should be XML review
	 */
	public static void save( String collectionId, String documentId, String personString)
			throws Exception {
		
		conn = AuthenticationUtilities.loadProperties();

		// initialize database driver
		
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

			col = getOrCreateCollection(collectionId);

			/*
			 * create new XMLResource with a given id an id is assigned to the new resource
			 * if left empty (null)
			 */
			//res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);

			JAXBContext context = JAXBContext.newInstance("rs.ac.uns.msb");

			Unmarshaller unmarshaller = context.createUnmarshaller();

			StringReader sr = new StringReader(personString);
			
			Person person = (Person) unmarshaller.unmarshal(sr);

			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NSPrefixMapper());
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			// marshal the contents to an output stream
			marshaller.marshal(person, os);

			// link the stream to the XML resource
			//res.setContent(os);
			//if (UpdateData.update(collectionId, "persons", "persons", personString, INSERT_AFTER) == 0L)
                //throw new RepositoryException("Failed to save person");
            col.setProperty("indent", "yes");
            
            XUpdateQueryService xupdateService = (XUpdateQueryService) col.getService("XUpdateQueryService", "1.0");
            xupdateService.setProperty("indent", "yes");
            
            //dodaj na kraj
            
            String contextXPath = "/persons";
            System.out.println(String.format(XUpdateTemplate.APPEND, contextXPath, os));
            System.out.println("[INFO] Appending fragments as last child of " + contextXPath + " node.");
            long mods = xupdateService.updateResource(documentId, String.format(XUpdateTemplate.APPEND, contextXPath, os));
            System.out.println("[INFO] " + mods + " modifications processed.");

			//col.storeResource(res);

		} finally {

			// don't forget to cleanup
			if (res != null) {
				try {
					((EXistResource) res).freeResources();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}

			if (col != null) {
				try {
					col.close();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}
	}

	private static Collection getOrCreateCollection(String collectionUri) throws XMLDBException, JAXBException {
		return getOrCreateCollection(collectionUri, 0);
	}

	private static Collection getOrCreateCollection(String collectionUri, int pathSegmentOffset) throws XMLDBException, JAXBException {

		Collection col = DatabaseManager.getCollection(conn.uri + collectionUri, conn.user, conn.password);

		// create the collection if it does not exist
		if (col == null) {

			if (collectionUri.startsWith("/")) {
				collectionUri = collectionUri.substring(1);
			}

			String pathSegments[] = collectionUri.split("/");

			if (pathSegments.length > 0) {
				StringBuilder path = new StringBuilder();

				for (int i = 0; i <= pathSegmentOffset; i++) {
					path.append("/" + pathSegments[i]);
				}

				Collection startCol = DatabaseManager.getCollection(conn.uri + path, conn.user, conn.password);

				if (startCol == null) {

					// child collection does not exist

					String parentPath = path.substring(0, path.lastIndexOf("/"));
					Collection parentCol = DatabaseManager.getCollection(conn.uri + parentPath, conn.user,
							conn.password);

					CollectionManagementService mgt = (CollectionManagementService) parentCol
							.getService("CollectionManagementService", "1.0");

					col = mgt.createCollection(pathSegments[pathSegmentOffset]);

					// dokumnet
					
					OutputStream os = new ByteArrayOutputStream();
					
					JAXBContext context = JAXBContext.newInstance("rs.ac.uns.msb");
					Marshaller marshaller = context.createMarshaller();
					marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NSPrefixMapper());
					marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
					Persons persons = new Persons();
					// marshal the contents to an output stream
					marshaller.marshal(persons, os);
					
					XMLResource res = (XMLResource) col.createResource("persons", XMLResource.RESOURCE_TYPE);
					res.setContent(os);

					col.storeResource(res);
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
