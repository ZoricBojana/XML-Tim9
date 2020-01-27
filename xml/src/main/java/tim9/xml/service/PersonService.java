package tim9.xml.service;

import rs.ac.uns.msb.Person;
import tim9.xml.exception.RepositoryException;

public interface PersonService {

	public Person savePerson(String personString) throws Exception;
	public Person findByUsername(String username) throws RepositoryException;
}
