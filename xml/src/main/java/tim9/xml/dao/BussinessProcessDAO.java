package tim9.xml.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;

import org.exist.xmldb.EXistResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.CompiledExpression;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;

import rs.ac.uns.msb.BussinessProcess;
import tim9.xml.exception.EntityNotFound;
import tim9.xml.util.AuthenticationUtilities;
import tim9.xml.util.AuthenticationUtilities.ConnectionProperties;
import tim9.xml.util.NSPrefixMapper;

public class BussinessProcessDAO {

	private static ConnectionProperties conn;
	private static final String TARGET_NAMESPACE = "http://www.uns.ac.rs/MSB";

	public static void main(String[] args) {

		try {
			BussinessProcess bp = BussinessProcessDAO.getProcessByUsernameAndPaperId("reg", "SA_11062020203402335");
			System.out.println(bp.getArticleId());

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IOException | XMLDBException
				| JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String retrieve(String collectionId, String documentId) throws Exception {

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

			res = (XMLResource) col.getResource(documentId);

			if (res == null) {
				notFound = true;
			} else {

				JAXBContext context = JAXBContext.newInstance("rs.ac.uns.msb");

				Unmarshaller unmarshaller = context.createUnmarshaller();

				BussinessProcess bussinessProcess = (BussinessProcess) unmarshaller.unmarshal(res.getContentAsDOM());

				if (bussinessProcess == null) {
					throw new Exception("Unmarshaling failed");
				}

				return (String) res.getContent();
			}
		} finally {
			// don't forget to clean up!

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

		if (notFound) {
			throw new EntityNotFound(documentId);
		}
		return null;
	}

	/**
	 * conn XML DB connection properties collectionId Should be the collection ID to
	 * access documentId Should be the document ID to store in the collection review
	 * should be XML review
	 */
	public static void store(String collectionId, String documentId, BussinessProcess process) throws Exception {

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

			/*
			 * Unmarshaller unmarshaller = context.createUnmarshaller();
			 * 
			 * StringReader sr = new StringReader(bpString);
			 * 
			 * BussinessProcess cv = (BussinessProcess) unmarshaller.unmarshal(sr);
			 */

			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NSPrefixMapper());
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// marshal the contents to an output stream
			marshaller.marshal(process, os);

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

	public static List<BussinessProcess> getProcessessForReviewer(String username) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, IOException, XMLDBException, JAXBException {

		String xqueryExpression = null;

		xqueryExpression = "let $col := collection(\"/db/sample/bussinessProcess\")\r\n"
				+ "for $process in $col//bussiness_process\r\n" + "for $rev in $process/reviews/review_data "
				+ "where $rev/reviewer_id='" + username + "' " + "and $process[@phase='waiting'] " + "return $process";

		return BussinessProcessDAO.executeXQueryExpression(xqueryExpression);
	}

	public static List<BussinessProcess> getProcesByPaperId(String paperId) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, IOException, XMLDBException, JAXBException {

		String xqueryExpression = null;

		xqueryExpression = "let $col := collection(\"/db/sample/bussinessProcess\")\r\n"
				+ "for $process in $col//bussiness_process\r\n" 
				+ "where $process/article_id='" + paperId + "' " + "and 2=2 " + "return $process";

		return BussinessProcessDAO.executeXQueryExpression(xqueryExpression);
	}

	// id editora
	public static List<BussinessProcess> getDoneProcesses(String username) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, IOException, XMLDBException, JAXBException {

		String xqueryExpression = null;

		xqueryExpression = "let $col := collection(\"/db/sample/bussinessProcess\")\r\n"
				+ "for $process in $col//bussiness_process\r\n" + "where $process/editor_id='" + username + "' "
				+ "and $process[@phase='done'] " + "return $process";

		return BussinessProcessDAO.executeXQueryExpression(xqueryExpression);
	}

	public static BussinessProcess getProcessByUsernameAndPaperId(String username, String paperId)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, XMLDBException,
			JAXBException {

		String xqueryExpression = null;

		xqueryExpression = "let $col := collection(\"/db/sample/bussinessProcess\")\r\n"
				+ "for $process in $col//bussiness_process\r\n" + "for $rev in $process/reviews/review_data "
				+ "where $rev/reviewer_id='" + username + "' " + "and $process/article_id='" + paperId + "' "
				+ "return $process";

		return BussinessProcessDAO.executeXQueryExpression(xqueryExpression).get(0);
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

	private static List<BussinessProcess> executeXQueryExpression(String xqueryExpression) throws IOException,
			ClassNotFoundException, XMLDBException, InstantiationException, IllegalAccessException, JAXBException {
		List<BussinessProcess> processes = new ArrayList<BussinessProcess>();

		ConnectionProperties conn = AuthenticationUtilities.loadProperties();
		String collectionId = "/db/sample/bussinessProcess";

		Class<?> cl = Class.forName(conn.driver);

		Database database = (Database) cl.newInstance();
		database.setProperty("create-database", "true");

		DatabaseManager.registerDatabase(database);

		Collection col = null;

		try {

			col = DatabaseManager.getCollection(conn.uri + collectionId);

			// get an instance of xquery service
			XQueryService xqueryService = (XQueryService) col.getService("XQueryService", "1.0");
			xqueryService.setProperty("indent", "yes");

			// make the service aware of namespaces, using the default one
			xqueryService.setNamespace("", TARGET_NAMESPACE);

			// compile and execute the expression
			CompiledExpression compiledXquery = xqueryService.compile(xqueryExpression);
			ResourceSet result = xqueryService.execute(compiledXquery);

			// handle the results

			ResourceIterator i = result.getIterator();
			Resource res = null;

			while (i.hasMoreResources()) {
				try {
					res = i.nextResource();

					JAXBContext context = JAXBContext.newInstance("rs.ac.uns.msb");

					Unmarshaller unmarshaller = context.createUnmarshaller();

					BussinessProcess process = (BussinessProcess) unmarshaller
							.unmarshal(((XMLResource) res).getContentAsDOM());

					if (process == null) {
						throw new InternalError("Unmarshaling failed");
					}
					processes.add(process);

				} finally {

					// don't forget to cleanup resources
					try {
						((EXistResource) res).freeResources();
					} catch (XMLDBException xe) {
						xe.printStackTrace();
					}
				}
			}

		} finally {

			// don't forget to cleanup
			if (col != null) {
				try {
					col.close();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}

		return processes;
	}
}
