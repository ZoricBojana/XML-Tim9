package tim9.xml.service.Impl;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import rs.ac.uns.msb.Author;
import rs.ac.uns.msb.Person;
import rs.ac.uns.msb.ScientificArticle;
import tim9.xml.dto.SearchDTO;
import tim9.xml.exception.EntityNotFound;
import tim9.xml.exception.Unauthorized;
import tim9.xml.repository.BussinessProcessRepository;
import tim9.xml.repository.PersonRepository;
import tim9.xml.repository.ScientificArticleRepository;
import tim9.xml.service.ScientificArticleService;
import tim9.xml.util.RDF.MetadataExtractor;
import tim9.xml.util.XSLFOTransformer.XSLFOTransformer;

@Service
public class ScientificArticleServiceImpl implements ScientificArticleService{

	@Autowired
	private XSLFOTransformer xslFoTransformer;

	@Autowired
	private MetadataExtractor metadataExtractor;
	
	@Autowired
	private ScientificArticleRepository scientificArticleRepository;
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private BussinessProcessRepository processRepository;

	@Override
	public String save(String article) throws Exception {
		// extract metadata
		//StringWriter out = new StringWriter(); 
		//StringReader in = new StringReader(article); 
		//metadataExtractor.extractMetadata(in, out);
		
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		
		String ID = scientificArticleRepository.save(article, username);
		System.out.println("Sacuvan rad");
		//scientificArticleRepository.saveMetadata(out, ID);
		//System.out.println("Sacuvani metapodaci");
		return ID;
	}
	
	@Override
	public String update(String ID, String article, String authorUsername) throws Exception {
		// extract metadata
		Person person = personRepository.findOneByUsername(authorUsername);
		if(person == null){
			throw new EntityNotFound(authorUsername);
		}
		
		String oldArticle = scientificArticleRepository.findById(ID);
		JAXBContext jaxbContext;
        boolean allowed = false;
        try {
        	jaxbContext = JAXBContext.newInstance(ScientificArticle.class);
        	Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        	ScientificArticle _article = (ScientificArticle) jaxbUnmarshaller.unmarshal(new StringReader(oldArticle));
    		for(Author author : _article.getAuthors().getAuthor()){
    			if(author.getUsername().equals(authorUsername)){
    				allowed = true;
    			}
    		}
		} catch (Exception e) {
			// TODO: handle exception
		}
        if(!allowed){
			throw new Unauthorized();
		}
		
		StringWriter out = new StringWriter(); 
		StringReader in = new StringReader(article); 
		metadataExtractor.extractMetadata(in, out);
		
		String id = scientificArticleRepository.update(ID, article, authorUsername);
		scientificArticleRepository.updateMetadata(out, ID);
		return id;
	}

	@Override
	public void delete(String id, String authorID) throws Exception {
		Person person = personRepository.findOneByID(authorID);
		if(person == null){
			throw new EntityNotFound(authorID);
		}
		
		String article = scientificArticleRepository.findById(id);
		JAXBContext jaxbContext;
        boolean allowed = false;
        try {
        	jaxbContext = JAXBContext.newInstance(ScientificArticle.class);
        	Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        	ScientificArticle _article = (ScientificArticle) jaxbUnmarshaller.unmarshal(new StringReader(article));
    		for(Author author : _article.getAuthors().getAuthor()){
    			if(author.getID().equals(authorID)){
    				allowed = true;
    			}
    		}
		} catch (Exception e) {
			// TODO: handle exception
		}
        if(!allowed){
			throw new Unauthorized();
		}
		
		try {
			scientificArticleRepository.delete(id, authorID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String findById(String id) throws Exception {
		String coverLetter = scientificArticleRepository.findById(id);
		if(coverLetter == null){
			throw new EntityNotFound(id);
		}
		return coverLetter;
	}

	@Override
	public String findByIdHTML(String id) throws Exception {
		String article = scientificArticleRepository.findById(id);
		if(article == null){
			throw new EntityNotFound(id);
		}
		String clHTML = xslFoTransformer.generateHTML(article, "src/main/resources/data/xslt/rad_pismo_rec/radToHTML.xsl");
		return clHTML;
	}

	@Override
	public ByteArrayOutputStream findByIdPDF(String id) throws Exception {
		String coverLetter = scientificArticleRepository.findById(id);
		if(coverLetter == null){
			throw new EntityNotFound(id);
		}
		ByteArrayOutputStream clPDF = xslFoTransformer.generatePDF(coverLetter,
				"src/main/resources/data/xslt/rad_pismo_rec/radToHTML.xsl");
		return clPDF;
	}

	@Override
	public List<ScientificArticle> searchByText(String value) throws Exception {
	
		return scientificArticleRepository.searchByText(value);
	}

	@Override
	public List<ScientificArticle> searchByAuthorsUsername(String value) throws Exception {

		return scientificArticleRepository.searchAuthorsArticles(value);
	}
	
	@Override
	public List<ScientificArticle> searchAuthorsPapers(String value) throws Exception {

		return scientificArticleRepository.searchAllAuthorsArticles(value);
	}

	@Override
	public List<ScientificArticle> searchByMetadata(SearchDTO dto) throws Exception {

		return scientificArticleRepository.searchByMetadate(dto.getTitle(), dto.getAuthor(), dto.getKey_word(), dto.getPublisher());
	}

	@Override
	public List<ScientificArticle> getAllForReview() throws Exception {
		
		return scientificArticleRepository.getAllForReview();
	}

	@Override
	public List<ScientificArticle> getAllForReviewer() throws Exception {
		
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		
		return processRepository.getForReviewer(username);
	}
}
