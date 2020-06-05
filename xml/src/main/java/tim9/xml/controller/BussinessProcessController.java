package tim9.xml.controller;

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

import tim9.xml.service.BussinessProcessService;

@RestController
@RequestMapping("/api")
public class BussinessProcessController {
	
	@Autowired
	private BussinessProcessService bpService;
	
	@PostMapping(value="/saveBussinessProcess", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> save(@RequestBody String bussinessProcess) throws Exception {

		String id = bpService.save(bussinessProcess);
		System.out.println("Bussiness process is successfully saved! ID is: " + id);
		return new ResponseEntity<>("Bussiness process is successfully saved! ID is: " + id, HttpStatus.OK);
	}
	
	@PutMapping(value="/updateBussinessProcess/{id}", consumes = MediaType.APPLICATION_XML_VALUE,produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> update(@RequestBody String bussinessProcess, @PathVariable("id") String id) throws Exception {

		String str = bpService.update(id, bussinessProcess);
		return new ResponseEntity<>("Bussiness process updated! ID is " + str, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/deleteBussinessProcess/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> delete(@PathVariable("id") String id) throws Exception{

		bpService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value="/getBussinessProcess/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> get(@PathVariable("id") String id) throws Exception{
		String bussinessProcess = bpService.findById(id);
		return new ResponseEntity<>(bussinessProcess, HttpStatus.OK);
	}

}
