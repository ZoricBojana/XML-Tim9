package tim9.xml.controller;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim9.xml.service.CoverLetterService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CoverLetterController {

	@Autowired
	private CoverLetterService coverLetterService;
	
	@PostMapping(value="/saveCoverLetter/{paperId}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> save(@RequestBody String coverLetter, @PathVariable("paperId") String paperId) throws Exception {
		// PREUZETI AUTOROV ID SECURITIJEM I PREPRAVITI OVO
		String authorID = "aaa";
		String id = coverLetterService.save(coverLetter, paperId);
		return new ResponseEntity<>("Cover letter is successfully saved! ID is: " + id, HttpStatus.OK);
	}
	
	@PutMapping(value="/updateCoverLetter/{id}", consumes = MediaType.APPLICATION_XML_VALUE,produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> update(@RequestBody String coverLetter, @PathVariable("id") String id) throws Exception {
		// PREUZETI AUTOROV ID SECURITIJEM I PREPRAVITI OVO
		String authorID = "aaa";
		String coverLetter_ = coverLetterService.update(id, coverLetter, authorID);
		return new ResponseEntity<>(coverLetter_, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/deleteCoverLetter/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> delete(@PathVariable("id") String id) throws Exception{
		// PREUZETI AUTOROV ID SECURITIJEM I PREPRAVITI OVO
		String authorID = "aaa";
		coverLetterService.delete(id, authorID);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value="/getCoverLetter/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> get(@PathVariable("id") String id) throws Exception{
		String coverLetter = coverLetterService.findById(id);
		return new ResponseEntity<>(coverLetter, HttpStatus.OK);
	}

	@GetMapping(value="/getCoverLetter/HTML/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> getHTML(@PathVariable("id") String id) throws Exception{
		String coverLetter = coverLetterService.findByIdHTML(id);
		return new ResponseEntity<>(coverLetter, HttpStatus.OK);
	}

	// id- paper id
	@GetMapping(value="/getCoverLetter/PDF/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> getPDF(@PathVariable("id") String id) throws Exception{
		ByteArrayOutputStream coverLetter = coverLetterService.findByIdPDF(id);
		return new ResponseEntity<>(coverLetter.toByteArray(), HttpStatus.OK);
	}
}
