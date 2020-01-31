package tim9.xml.service;

import java.io.ByteArrayOutputStream;

public interface CoverLetterService {

	public String save(String coverLetter) throws Exception;
	public String findById(String id) throws Exception;
	public String findByIdHTML(String id) throws Exception;
	public ByteArrayOutputStream findByIdPDF(String id) throws Exception;
	public String update(String ID, String coverLette, String personID) throws Exception;
	public void delete(String id, String personID) throws Exception;
	
}
