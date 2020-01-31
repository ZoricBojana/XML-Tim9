package tim9.xml.controller;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim9.xml.service.ScientificArticleService;

@RestController
@RequestMapping("/api")
public class ScientificArticleController {

	@Autowired
	private ScientificArticleService scientificArticleService;
	
	@PostMapping(value="/saveScientificArticle", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> save(@RequestBody String article) throws Exception {
		// ovo se koristi kada se generise sasvim novi xml fajl
		// SECURITIJEM PROVERITI DA LI JE U PITANJU KORISNIK
		String id = scientificArticleService.save(article);
		return new ResponseEntity<>("Cover letter is successfully saved! ID is: " + id, HttpStatus.OK);
	}
	
	@PutMapping(value="/updateScientificArticle/{id}", consumes = MediaType.APPLICATION_XML_VALUE,produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> update(@RequestBody String article, @PathVariable("id") String id) throws Exception {
		// ovo se koristi kada autor rada cuva izmene vec zapocetog rada ili ga publikuje 
		// PREUZETI AUTOROV ID SECURITIJEM I PREPRAVITI OVO
		String authorID = "aaa";
		String coverLetter_ = scientificArticleService.update(id, article, authorID);
		return new ResponseEntity<>(coverLetter_, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/deleteScientificArticle/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> delete(@PathVariable("id") String id) throws Exception{
		// implementirati da ovo moze raditi samo autor rada
		// ovo se koristi kada autor rada odbacuje svoj rad
		// PREUZETI AUTOROV ID SECURITIJEM I PREPRAVITI OVO
		String authorID = "aaa";
		scientificArticleService.delete(id, authorID);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value="/getScientificArticle/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> get(@PathVariable("id") String id) throws Exception{
		String coverLetter = scientificArticleService.findById(id);
		return new ResponseEntity<>(coverLetter, HttpStatus.OK);
	}

	@GetMapping(value="/getScientificArticle/HTML/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> getHTML(@PathVariable("id") String id) throws Exception{
		String coverLetter = scientificArticleService.findByIdHTML(id);
		return new ResponseEntity<>(coverLetter, HttpStatus.OK);
	}

	@GetMapping(value="/getScientificArticle/PDF/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> getPDF(@PathVariable("id") String id) throws Exception{
		ByteArrayOutputStream coverLetter = scientificArticleService.findByIdPDF(id);
		return new ResponseEntity<>(coverLetter.toByteArray(), HttpStatus.OK);
	}
}
