package tim9.xml.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.exist.xmldb.EXistResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import org.xmldb.api.modules.XUpdateQueryService;

import rs.ac.uns.msb.Person;
import rs.ac.uns.msb.Persons;
import tim9.xml.util.AuthenticationUtilities;
import tim9.xml.util.AuthenticationUtilities.ConnectionProperties;
import tim9.xml.util.NSPrefixMapper;
import tim9.xml.util.template.XUpdateTemplate;

public class PersonDAO {
	/*
	 * Methods store getByUsername getByID find reviewers
	 */
	
	private static final String TARGET_NAMESPACE = "http://www.uns.ac.rs/MSB";
	
	
	// za tstiranje - obrisati na kraju
	public static void main(String[] args) {
		try {
			for (Person p : PersonDAO.findAllReviewers()) {
				System.out.println(p.getUsername());
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IOException | XMLDBException
				| JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Person getByUsername(String username, String collectionId) throws Exception {

		ConnectionProperties conn = AuthenticationUtilities.loadProperties();

		// initialize database driver
		Class<?> cl = Class.forName(conn.driver);

		Database database = (Database) cl.newInstance();
		database.setProperty("create-database", "true");

		DatabaseManager.registerDatabase(database);

		XMLResource res = null;

		String xPathExp = String.format("doc(\"persons\")//person[@username='%s']", username);

		getOrCreateCollection(collectionId);
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

			if (person == null) {
				throw new Exception("Unmarshaling failed");
			}

			return person;
		} else {
			return null;
		}
	}

	public static List<Person> findAllReviewers() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, XMLDBException, JAXBException {
		
		String 		xqueryExpression = "let $col := collection(\"/db/sample/persons\")\r\n"
				+ "for $p in $col//persons/person\r\n"
				+ "for $role in $p/roles/role\r\n"
				+ "where $role/text()='REVIEWER_ROLE' " + "and 2=2 return $p";

		return executeXQueryExpression(xqueryExpression);
	}

	public static Person getByID(String ID, String collectionId) throws Exception {

		ConnectionProperties conn = AuthenticationUtilities.loadProperties();

		// initialize database driver
		Class<?> cl = Class.forName(conn.driver);

		Database database = (Database) cl.newInstance();
		database.setProperty("create-database", "true");

		DatabaseManager.registerDatabase(database);

		XMLResource res = null;

		String xPathExp = String.format("doc(\"persons\")//person[@id='%s']", ID);

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

			if (person == null) {
				throw new Exception("Unmarshaling failed");
			}

			return person;
		} else {
			return null;
		}
	}

	private static ConnectionProperties conn;

	/**
	 * conn XML DB connection properties collectionId Should be the collection ID to
	 * access documentId Should be the document ID to store in the collection review
	 * should be XML review
	 */
	public static void store(String collectionId, String documentId, String personString) throws Exception {

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
			// res = (XMLResource) col.createResource(documentId,
			// XMLResource.RESOURCE_TYPE);

			JAXBContext context = JAXBContext.newInstance("rs.ac.uns.msb");

			Unmarshaller unmarshaller = context.createUnmarshaller();

			StringReader sr = new StringReader(personString);

			Person person = (Person) unmarshaller.unmarshal(sr);
			person.addRole("USER_ROLE");

			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			person.setPassword(passwordEncoder.encode(person.getPassword()));

			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NSPrefixMapper());
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			// marshal the contents to an output stream
			marshaller.marshal(person, os);

			// link the stream to the XML resource
			// res.setContent(os);
			// if (UpdateData.update(collectionId, "persons", "persons", personString,
			// INSERT_AFTER) == 0L)
			// throw new RepositoryException("Failed to save person");
			col.setProperty("indent", "yes");

			XUpdateQueryService xupdateService = (XUpdateQueryService) col.getService("XUpdateQueryService", "1.0");
			xupdateService.setProperty("indent", "yes");

			// dodaj na kraj

			String contextXPath = "/persons";
			System.out.println(String.format(XUpdateTemplate.APPEND, contextXPath, os));
			System.out.println("[INFO] Appending fragments as last child of " + contextXPath + " node.");
			long mods = xupdateService.updateResource(documentId,
					String.format(XUpdateTemplate.APPEND, contextXPath, os));
			System.out.println("[INFO] " + mods + " modifications processed.");

			// col.storeResource(res);

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

	private static Collection getOrCreateCollection(String collectionUri)
			throws XMLDBException, JAXBException, IOException {
		return getOrCreateCollection(collectionUri, 0);
	}

	private static Collection getOrCreateCollection(String collectionUri, int pathSegmentOffset)
			throws XMLDBException, JAXBException, IOException {

		conn = AuthenticationUtilities.loadProperties();
		System.out.println(conn.uri);
		System.out.println(conn.user);
		System.out.println(conn.password);
		System.out.println(DatabaseManager.getDatabases());
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

	// pretraga
	private static List<Person> executeXQueryExpression(String xqueryExpression) throws IOException,
			ClassNotFoundException, XMLDBException, InstantiationException, IllegalAccessException, JAXBException {
		List<Person> people = new ArrayList<Person>();

		ConnectionProperties conn = AuthenticationUtilities.loadProperties();
		String collectionId = "/db/sample/persons";

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

					Person person = (Person) unmarshaller
							.unmarshal(((XMLResource) res).getContentAsDOM());

					if (person == null) {
						throw new InternalError("Unmarshaling failed");
					}
					people.add(person);

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

		return people;
	}
}
