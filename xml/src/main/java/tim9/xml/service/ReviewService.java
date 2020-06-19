package tim9.xml.service;

import java.io.ByteArrayOutputStream;

public interface ReviewService {
	
	
	public String findById(String id) throws Exception;

	public String findByIdHTML(String id) throws Exception;
	
	public void delete(String id) throws Exception;

	public String save(String review, String paperId) throws Exception;
	
	public ByteArrayOutputStream getReviewsForPaper(String paperId) throws Exception;
}
