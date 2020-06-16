package tim9.xml.controller;

import javax.xml.bind.UnmarshalException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tim9.xml.exception.EntityNotFound;
import tim9.xml.service.ReviewService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	// TODO update, delete, PDF
	
	@PostMapping(value = "/saveReview", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> save(@RequestBody String review) {
		String id = null;
		try {
			id = reviewService.save(review);
		} catch (UnmarshalException e) {
			return new ResponseEntity<String>("Review is not formatted well", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Something went wrong", HttpStatus.PRECONDITION_FAILED);
		}
		return new ResponseEntity<>("Review is successfully saved! ID is: " + id, HttpStatus.OK);

	}

	@GetMapping(value = "/getReview/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> findById(@PathVariable("id") String id) {

		String retVal;
		try {
			retVal = reviewService.findById(id);
		} catch (EntityNotFound e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.PRECONDITION_FAILED);
		} catch (UnmarshalException e) {
			return new ResponseEntity<String>("This document is not well formated", HttpStatus.EXPECTATION_FAILED);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Something went wrong", HttpStatus.PRECONDITION_FAILED);
		}

		return new ResponseEntity<String>(retVal, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getReviewHTML/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> findByIdHTML(@PathVariable("id") String id) {

		String retVal;
		try {
			retVal = reviewService.findByIdHTML(id);
		} catch (EntityNotFound e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.PRECONDITION_FAILED);
		} catch (UnmarshalException e) {
			return new ResponseEntity<String>("This document is not well formated", HttpStatus.EXPECTATION_FAILED);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Something went wrong", HttpStatus.PRECONDITION_FAILED);
		}

		return new ResponseEntity<String>(retVal, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/deleteReview/{id}")
	public ResponseEntity<String> deleteReview(@PathVariable("id") String id){
		
		try {
			reviewService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Something went wrong", HttpStatus.EXPECTATION_FAILED);
		}
		
		return new ResponseEntity<String>("Deleted", HttpStatus.OK);
	}
}
