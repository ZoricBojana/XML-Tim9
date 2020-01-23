package tim9.xml.dao;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringReader;

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

import rs.ac.uns.msb.Review;
import tim9.xml.util.AuthenticationUtilities;
import tim9.xml.util.AuthenticationUtilities.ConnectionProperties;
import tim9.xml.util.NSPrefixMapper;

public class StoreReview {
	
	private static ConnectionProperties conn;

	/**
	 * conn XML DB connection properties collectionId Should be the collection ID to
	 * access documentId Should be the document ID to store in the collection
	 * review should be XML review
	 */
	public static void run( String collectionId, String documentId, String reviewString)
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
			res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);

			JAXBContext context = JAXBContext.newInstance("rs.ac.uns.msb");

			Unmarshaller unmarshaller = context.createUnmarshaller();

			StringReader sr = new StringReader(reviewString);
			
			Review review = (Review) unmarshaller.unmarshal(sr);

			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NSPrefixMapper());
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// marshal the contents to an output stream
			marshaller.marshal(review, os);

			// link the stream to the XML resource
			res.setContent(os);

			col.storeResource(res);

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

	private static Collection getOrCreateCollection(String collectionUri) throws XMLDBException {
		return getOrCreateCollection(collectionUri, 0);
	}

	private static Collection getOrCreateCollection(String collectionUri, int pathSegmentOffset) throws XMLDBException {

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
