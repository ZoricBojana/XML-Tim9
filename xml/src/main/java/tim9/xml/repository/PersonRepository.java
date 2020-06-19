package tim9.xml.repository;

import static tim9.xml.util.template.XUpdateTemplate.UPDATE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Repository;
import org.xmldb.api.base.XMLDBException;

import rs.ac.uns.msb.Person;
import tim9.xml.dao.PersonDAO;
import tim9.xml.dto.RegisterDTO;
import tim9.xml.exception.RepositoryException;
import tim9.xml.exception.UsernameAlreadyExist;
import tim9.xml.util.exist.UpdateData;

@Repository
public class PersonRepository {
	//@Value("${person-collection-id}")
    private String personCollectionId = "/db/sample/persons";

    public Person findOneByUsername(String username) throws RepositoryException {
        try {
        	
        	Person retVal = null;
        	
        	retVal = PersonDAO.getByUsername(username, personCollectionId);
          
            return retVal;
        } catch (Exception e) {
        	e.printStackTrace();
            throw new RepositoryException("Error in repository");
        }
    }
    
    public Person findOneByID(String ID) throws RepositoryException {
        try {
        	
        	Person retVal = null;
        	
        	retVal = PersonDAO.getByID(ID, personCollectionId);
          
            return retVal;
        } catch (Exception e) {
        	e.printStackTrace();
            throw new RepositoryException("Error in repository");
        }
    }

    public Person save(RegisterDTO dto) throws Exception {
        //try {
        	JAXBContext context = JAXBContext.newInstance("rs.ac.uns.msb");

			Unmarshaller unmarshaller = context.createUnmarshaller();

			//StringReader sr = new StringReader(personString);
			
			Person person = null;
			//person = (Person) unmarshaller.unmarshal(sr);
			
			person = new Person();
			person.setUsername(dto.getUsername());
			person.setPassword(dto.getPassword());
			person.setFirstName(dto.getFirstname());
			person.setLastName(dto.getLastName());
			person.setEmailAddress(dto.getEmail());
			person.setEmail(dto.getEmail());
			
            if (findOneByUsername(person.getUsername()) != null)
                throw new UsernameAlreadyExist();

            
            PersonDAO.store(personCollectionId, "persons", person);

            return findOneByUsername(person.getUsername());

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
    
    public List<Person> getAllReviewers() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, XMLDBException, JAXBException {
    	
    	return PersonDAO.findAllReviewers();
    }
}
