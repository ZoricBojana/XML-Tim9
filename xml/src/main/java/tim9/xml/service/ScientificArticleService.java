package tim9.xml.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import rs.ac.uns.msb.ScientificArticle;
import tim9.xml.dto.SearchDTO;
import tim9.xml.exception.RepositoryException;

public interface ScientificArticleService {

	public String save(String article) throws Exception;
	public String findById(String id) throws Exception;
	public String findByIdHTML(String id) throws Exception;
	public ByteArrayOutputStream findByIdPDF(String id) throws Exception;
	public String update(String ID, String article, String authorID) throws Exception;
	public void delete(String id, String authorID) throws Exception;
	
	public List<ScientificArticle> searchByText(String value) throws Exception;
	public List<ScientificArticle> searchByMetadata(SearchDTO dto) throws Exception;
	public List<ScientificArticle> searchByAuthorsTitle(String value) throws Exception;
}
