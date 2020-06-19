package tim9.xml.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import rs.ac.uns.msb.Person;
import tim9.xml.dto.RegisterDTO;
import tim9.xml.exception.RepositoryException;

public interface PersonService extends UserDetailsService{

	public Person savePerson(RegisterDTO dto) throws Exception;
	public Person findByUsername(String username) throws RepositoryException;
	
	public List<Person> getAllReviewers();
	
}
