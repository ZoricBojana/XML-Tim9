package tim9.xml.repository;

import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.exist.xmldb.EXistResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import rs.ac.uns.msb.Person;
import tim9.xml.exception.RepositoryException;
import tim9.xml.util.exist.RetriveData;
import tim9.xml.util.exist.UpdateData;

import static tim9.xml.util.template.XUpdateTemplate.TARGET_NAMESPACE;
import static tim9.xml.util.template.XUpdateTemplate.INSERT_AFTER;
import static tim9.xml.util.template.XUpdateTemplate.UPDATE;

@Repository
public class PersonRepository {
	 @Value("${person-collection-id}")
	    private String personCollectionId;

	    public Person findOneByUsername(String username) throws RepositoryException {
	        try {
	            String xPathExp = String.format("persons/person[username='%s']", username);
	            ResourceSet resultSet = RetriveData.executeXPathExpression(personCollectionId, xPathExp, TARGET_NAMESPACE);

	            if (resultSet == null)
	                return null;

	            ResourceIterator i = resultSet.getIterator();
	            XMLResource res = null;
	            Person retVal = null;

	            if (i.hasMoreResources()) {
	                res = (XMLResource) i.nextResource();
	                retVal = (Person) res.getContent();
	            }

	            if (res != null)
	                try {
	                    ((EXistResource) res).freeResources();
	                } catch (XMLDBException exception) {
	                    exception.printStackTrace();
	                }

	            return retVal;
	        } catch (Exception e) {
	            throw new RepositoryException("Error in repository");
	        }
	    }

	    public Person save(Person person) throws RepositoryException {
	        try {
	            if (findOneByUsername(person.getUsername()) != null)
	                throw new RepositoryException("Person already exists");

	            String xmlElement = marshallUser(person);

	            if (UpdateData.update(personCollectionId, "persons", "persons", xmlElement, INSERT_AFTER) == 0L)
	                throw new RepositoryException("Failed to save person");
	            else
	                return findOneByUsername(person.getUsername());
	        } catch (Exception e) {
	            throw new RepositoryException(e.getMessage());
	        }
	    }

	    public Person update(Person person) throws RepositoryException {
	        try {
	            if (findOneByUsername(person.getUsername()) == null)
	                throw new RepositoryException("Person does not exist");

	            String xmlElement = marshallUser(person);

	            if (UpdateData.update(personCollectionId, "persons", "persons", xmlElement, UPDATE) == 0L)
	                throw new RepositoryException("Failed to update person");
	            else
	                return findOneByUsername(person.getUsername());
	        } catch (Exception e) {
	            throw new RepositoryException(e.getMessage());
	        }
	    }

	    private String marshallUser(Person person) throws Exception {
	        JAXBContext context = JAXBContext.newInstance(Person.class);
	        Marshaller marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

	        ByteArrayOutputStream stream = new ByteArrayOutputStream();
	        marshaller.marshal(person, stream);

	        return new String(stream.toByteArray());
	    }
}
