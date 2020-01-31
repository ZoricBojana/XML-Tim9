package tim9.xml.service;

import java.io.ByteArrayOutputStream;

import tim9.xml.exception.RepositoryException;

public interface ScientificArticleService {

	public String save(String article) throws Exception;
	public String findById(String id) throws Exception;
	public String findByIdHTML(String id) throws Exception;
	public ByteArrayOutputStream findByIdPDF(String id) throws Exception;
	public String update(String ID, String article, String authorID) throws Exception;
	public void delete(String id, String authorID) throws Exception;
}
