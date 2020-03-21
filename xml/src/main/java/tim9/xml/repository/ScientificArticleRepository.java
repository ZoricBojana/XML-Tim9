package tim9.xml.repository;


import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import rs.ac.uns.msb.ScientificArticle;
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

	public String save(String article) throws Exception {
    	String ID = generateID();
    	ScientificArticleDAO.store(scientificArticleCollectionId, ID, article);
		return ID;
	}
    
    public String update(String id, String article, String authorID) throws Exception {
    	String oldArticle = this.findById(id);
        if (oldArticle == null) {
            throw new Exception("SA with id: " + id);
        }
        
        this.delete(id, authorID);
        ScientificArticleDAO.store(scientificArticleCollectionId, id, article);
        return id;
	}
    
    public void delete(String id, String authorID) throws Exception {
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
}
