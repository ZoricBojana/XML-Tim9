package tim9.xml.service;

public interface ReviewService {
	
	public String save(String review) throws Exception;
	
	public String findById(String id) throws Exception;

	public String findByIdHTML(String id) throws Exception;
}
