package tim9.xml.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.tools.ant.types.CommandlineJava.SysProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import rs.ac.uns.msb.Person;
import tim9.xml.exception.RepositoryException;
import tim9.xml.exception.UsernameAlreadyExist;
import tim9.xml.security.TokenUtils;
import tim9.xml.security.auth.JwtAuthenticationRequest;
import tim9.xml.service.PersonService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	//registracija
	@PostMapping(value = "/savePerson")
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
	
	@GetMapping(value = "/getReviewers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Person>> getReviewers() {
		
		List<Person> reviewers = null;
		
		reviewers = personService.getAllReviewers();
		
		return new ResponseEntity<List<Person>>(reviewers, HttpStatus.OK);
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response) throws AuthenticationException, IOException {
		
		System.out.println(authenticationRequest.getUsername() + " * " + authenticationRequest.getPassword());
		
		if (authenticationRequest.getUsername().trim().equals("") || authenticationRequest.getUsername() == null
				|| authenticationRequest.getPassword().trim().equals("")
				|| authenticationRequest.getPassword() == null) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		Authentication authentication = null;
		try {
			authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
							authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			//e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		// Ubaci username + password u kontext
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Kreiraj token
		Person user = (Person) authentication.getPrincipal();
		String jwt = tokenUtils.generateToken(user);

		//int expiresIn = tokenUtils.getExpiredIn();
		// Vrati token kao odgovor na uspesno autentifikaciju
		return ResponseEntity.ok(jwt);
	}
	
}
