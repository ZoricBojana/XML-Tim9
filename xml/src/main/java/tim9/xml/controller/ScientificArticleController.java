package tim9.xml.controller;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.msb.ScientificArticle;
import tim9.xml.dto.SearchDTO;
import tim9.xml.service.ScientificArticleService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ScientificArticleController {

	@Autowired
	private ScientificArticleService scientificArticleService;
	
	@PostMapping(value="/saveScientificArticle", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> save(@RequestBody String article) throws Exception {
		// ovo se koristi kada se generise sasvim novi xml fajl
		// SECURITIJEM PROVERITI DA LI JE U PITANJU KORISNIK
		String id = scientificArticleService.save(article);
		System.out.println("Cover letter is successfully saved! ID is: " + id);
		return new ResponseEntity<>(id, HttpStatus.OK);
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
	
	@PostMapping(value="/searchArticles", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ScientificArticle>> searchByText(@RequestBody String value){
		List<ScientificArticle> articles = null;
		try {
			articles = scientificArticleService.searchByText(value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<List<ScientificArticle>>(articles, HttpStatus.OK);
	}
	
	@PostMapping(value="/searchArticlesMetadata", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ScientificArticle>> searchByMetadata(@RequestBody SearchDTO dto){

		List<ScientificArticle> articles = null;
		try {
			articles = scientificArticleService.searchByMetadata(dto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<List<ScientificArticle>>(articles, HttpStatus.OK);
	}
	
	@PostMapping(value="/searchAuthorsArticles", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ScientificArticle>> searchByAuthorsUsername(@RequestBody String value){
		
		List<ScientificArticle> articles = null;
		try {
			articles = scientificArticleService.searchByAuthorsUsername(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<List<ScientificArticle>>(articles, HttpStatus.OK);
	}
	
	@GetMapping(value="/getAllForReview", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ScientificArticle>> getAllForReview(){
		
		List<ScientificArticle> articles = null;
		try {
			articles = scientificArticleService.getAllForReview();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<List<ScientificArticle>>(articles, HttpStatus.OK);
	}
	
	@GetMapping(value="/searchAllAuthorsArticles", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ScientificArticle>> searchAllAuthorsArticles(){
		
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<ScientificArticle> articles = null;
		try {
			articles = scientificArticleService.searchAuthorsPapers(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<ScientificArticle>>(articles, HttpStatus.OK);
	}
	
	@GetMapping(value="/getAllForReviewer", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ScientificArticle>> getAllForReviewer(){
		
		List<ScientificArticle> articles = null;
		try {
			articles = scientificArticleService.getAllForReviewer();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<List<ScientificArticle>>(articles, HttpStatus.OK);
	}
}
