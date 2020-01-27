package tim9.xml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.msb.Person;
import tim9.xml.exception.RepositoryException;
import tim9.xml.exception.UsernameAlreadyExist;
import tim9.xml.service.PersonService;

@RestController
@RequestMapping("/api")
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@PostMapping(value = "/savePerson", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<Person> save(@RequestBody String person){
		
		Person saved = null;
		try {
			saved = personService.savePerson(person);
		} catch (UsernameAlreadyExist e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<Person>(saved, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/getPersonByUsername/{username}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<Person> getOneByUsername(@PathVariable("username") String username){
		Person person;
		try {
			person = personService.findByUsername(username);
		} catch (RepositoryException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		
		return new ResponseEntity<Person>(person, HttpStatus.OK);
	}
}
