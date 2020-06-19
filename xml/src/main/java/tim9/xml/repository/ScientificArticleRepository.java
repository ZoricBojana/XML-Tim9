package tim9.xml.repository;


import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Repository;
import org.xmldb.api.base.XMLDBException;

import rs.ac.uns.msb.Author;
import rs.ac.uns.msb.Person;
import rs.ac.uns.msb.ScientificArticle;
import tim9.xml.dao.PersonDAO;
import tim9.xml.dao.ScientificArticleDAO;
import tim9.xml.exception.EntityNotFound;
import tim9.xml.util.RDF.RDFStore;
import tim9.xml.util.RDF.RDFUpdate;
import tim9.xml.util.exist.UpdateData;

@Repository
public class ScientificArticleRepository {

	public static String scientificArticleCollectionId = "/db/sample/scientificArticle";

	public static String scientificArticleSchemaPath = "src/main/resources/data/ScienceArticleSchema.xsd";
	public static String ScientificArticleXSLPath = "src/main/resources/data/xslt/scientificArticle.xsl";

	public String save(String article, String username) throws Exception {
    	String ID = generateID();
    	Person person = PersonDAO.getByUsername(username, "/db/sample/persons");
    	
    	Author author = new Author();
    	
    	author.setUsername(username);
    	author.setFirstName(person.getFirstName());
    	author.setLastName(person.getLastName());
    	author.setEmailAddress(person.getEmailAddress());
    	author.setID(person.getID());
    	
    	ScientificArticleDAO.store(scientificArticleCollectionId, ID, article, author);
		return ID;
	}
    
    public String update(String id, String article, String username) throws Exception {
    	String oldArticle = this.findById(id);
        if (oldArticle == null) {
            throw new Exception("SA with id: " + id);
        }
        
        Person person = PersonDAO.getByUsername(username, "/db/sample/persons");
    	
    	Author author = new Author();
    	
    	author.setUsername(username);
    	author.setFirstName(person.getFirstName());
    	author.setLastName(person.getLastName());
    	author.setEmailAddress(person.getEmailAddress());
    	author.setID(person.getID());
        
        this.remove(id, username);
        ScientificArticleDAO.store(scientificArticleCollectionId, id, article, author);
        return id;
	}
    
    public void delete(String id, ScientificArticle article) throws Exception {
		
		ScientificArticleDAO.delete(scientificArticleCollectionId, id, article);
	}
    
    public void remove(String id, String username) throws Exception {
    	String xpathExp = "/scientificArticle";
		long mods = UpdateData.delete(scientificArticleCollectionId, id, xpathExp);
		if (mods == 0) {
			throw new EntityNotFound(id);
		}
		deleteMetadata(id);
    }
    
    public String findById(String id) throws Exception {
    	return ScientificArticleDAO.retrieve(scientificArticleCollectionId, id);
	}
    
    public void saveMetadata(StringWriter metadata, String id) throws Exception {
		RDFStore.store(metadata, "/example/scientificArticle/" + id);
	}

	public void deleteMetadata(String cvId) throws Exception {
		RDFUpdate.remove("/example/scientificArticle/" + cvId);
	}

	public void updateMetadata(StringWriter metadata, String id) throws Exception {
		String url = "/example/scientificArticle/" + id;
		deleteMetadata(id);
		RDFStore.store(metadata, url);
	}
    
    public String generateID() throws Exception{
    	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmssSS");
    	String ID = "SA_" + sdf.format(new Date());
    	return ID;
    }
    
    public List<ScientificArticle> searchByText(String value) throws Exception {
    	return ScientificArticleDAO.searchAllPublished(value);
    }
    
    public List<ScientificArticle> searchByMetadate(String title, String author, String keyWord, String publisher) throws Exception {
    	return ScientificArticleDAO.searchPublishedByMetadata(title, author, keyWord, publisher);
    }
    
    // pretraga autorovih objavljenih radova - kada korisnik pretrazuje
    public List<ScientificArticle> searchAuthorsArticles(String value) throws Exception{
    	return ScientificArticleDAO.searchByAuthorUsername(value, true);
    }
    
    // pretraga autorovih radova, kada autor pretrazuje svoje radove
    public List<ScientificArticle> searchAllAuthorsArticles(String value) throws Exception{
    	return ScientificArticleDAO.searchByAuthorUsername(value, false);
    }
    
    public List<ScientificArticle> getAllForReview() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, XMLDBException, JAXBException {
    	return ScientificArticleDAO.searchAllForReview();
    }
    
}
