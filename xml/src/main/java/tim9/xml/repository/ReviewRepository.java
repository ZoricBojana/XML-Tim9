package tim9.xml.repository;

import static tim9.xml.util.template.XUpdateTemplate.TARGET_NAMESPACE;

import java.io.StringWriter;

import org.exist.xmldb.EXistResource;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import tim9.xml.dao.ReviewDAO;
import tim9.xml.util.RDF.RDFStore;
import tim9.xml.util.exist.RetriveData;
import tim9.xml.util.exist.StoreData;
import tim9.xml.util.exist.UpdateData;

@Repository
public class ReviewRepository {

	public static String reviewCollectionId = "/db/sample/reviews";
    //private String reviewCollectionId;
	
	public String save(String review) throws Exception {
		String ID = generateID();
		ReviewDAO.store(reviewCollectionId, ID, review);
		return ID;
	}
	
	public String findById(String id) throws Exception {
		
		return ReviewDAO.retrieve(reviewCollectionId, id);
	}

   /* public String findOne(String id) throws Exception {
        String xPathExp = String.format("//review[@id='%s']", id);
        ResourceSet resultSet = RetriveData.executeXPathExpression(reviewCollectionId, xPathExp, TARGET_NAMESPACE);
        if (resultSet == null)
            return null;

        ResourceIterator i = resultSet.getIterator();
        XMLResource res = null;
        String retVal = null;

        if (i.hasMoreResources()) {
            res = (XMLResource) i.nextResource();
            retVal = res.getContent().toString();
        }

        if (res != null)
            try {
                ((EXistResource) res).freeResources();
            } catch (XMLDBException exception) {
                exception.printStackTrace();
            }

        return retVal;
    }

    public String create(String id, String review) throws Exception {
        StoreData.store(reviewCollectionId, id, review);
        return id;
    }
    */
	
	// TODO testirati
    public String update(String id, String review) throws Exception {
        String oldReviewData = this.findById(id);
        if (oldReviewData == null) {
            throw new Exception("Review with id: " + id);
        }
        this.delete(id);
        StoreData.store(reviewCollectionId, id, review);
        return id;
    }

    
    public void delete(String id) throws Exception {
        String xPathExp = "/review";
        long mods = UpdateData.delete(reviewCollectionId, id, xPathExp);
        if (mods == 0) {
            throw new Exception(String.format("Review with documentId %s", id));
        }
    }
    
    // Metadata
    
    public void saveMetadata(StringWriter metadata, String reviewId) throws Exception {
		RDFStore.store(metadata, "/example/review/" + reviewId);
	}
    
    /// ID
    
    public String generateID() throws Exception{
    	// TODO popraviti
    	String retVal = "Review";
    	int i = 2;
    	String expression = "//review";
    	ResourceSet result = RetriveData.executeXPathExpression(
				reviewCollectionId,
				expression,
				TARGET_NAMESPACE);
    	
    	if (result == null) {
			return null;
		}
    	
    	ResourceIterator iterator = result.getIterator();
		XMLResource resource = null;
		
		while (iterator.hasMoreResources()) {
			try {
				resource = (XMLResource) iterator.nextResource();
				i++;
			} finally {
				// don't forget to cleanup resources
				try {
					((EXistResource) resource).freeResources();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}
		return retVal + i + ".xml";
    }
}
