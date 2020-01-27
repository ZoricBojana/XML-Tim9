package tim9.xml.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.msb.Person;
import tim9.xml.exception.RepositoryException;
import tim9.xml.repository.PersonRepository;
import tim9.xml.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	@Override
	public Person savePerson(String personString) throws Exception {
		
		return personRepository.save(personString);
	
	}

	@Override
	public Person findByUsername(String username) throws RepositoryException {

		return personRepository.findOneByUsername(username);
	}

}
