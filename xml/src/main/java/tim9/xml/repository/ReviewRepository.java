package tim9.xml.repository;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import rs.ac.uns.msb.BussinessProcess;
import rs.ac.uns.msb.BussinessProcess.Reviews.ReviewData;
import tim9.xml.dao.BussinessProcessDAO;
import tim9.xml.dao.ReviewDAO;
import tim9.xml.util.RDF.RDFStore;
import tim9.xml.util.exist.UpdateData;

@Repository
public class ReviewRepository {

	public static String reviewCollectionId = "/db/sample/reviews";
    //private String reviewCollectionId;
	
	@Autowired
	private BussinessProcessRepository processRepository;
	
	public String save(String review, String paperId) throws Exception {
		String ID = generateID();
		ReviewDAO.store(reviewCollectionId, ID, review, paperId);
		
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		
		BussinessProcess process = BussinessProcessDAO.getProcessByUsernameAndPaperId(username, paperId);
		
		boolean done = true;
		
		for (ReviewData data : process.getReviews().getReviewData()) {
			if(data.getReviewerId().equals(username)) {
				data.setReviewId(ID);
				continue;
			}
			
			if(data.getReviewId() == null) {
				done = false;
			}
			
		}
		
		if(done) {
			process.setPhase("done");
		}
		
		this.processRepository.update(process.getId(), process);
		
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
    public String update(String id, String review, String paperId) throws Exception {
        String oldReviewData = this.findById(id);
        if (oldReviewData == null) {
            throw new Exception("Review with id: " + id);
        }
        this.delete(id);
        ReviewDAO.store(reviewCollectionId, id, review, paperId);
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
    
    public String generateID() throws Exception{
    	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmssSS");
    	String ID = "RW_" + sdf.format(new Date());
    	return ID;
    }
}
