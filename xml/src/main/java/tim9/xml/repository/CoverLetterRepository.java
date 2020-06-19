package tim9.xml.repository;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Repository;

import tim9.xml.dao.CoverLetterDAO;
import tim9.xml.exception.EntityNotFound;
import tim9.xml.util.RDF.RDFStore;
import tim9.xml.util.RDF.RDFUpdate;
import tim9.xml.util.exist.StoreData;
import tim9.xml.util.exist.UpdateData;

@Repository
public class CoverLetterRepository {
	
	public static final String ScienceArticleID = "href";
	public static String coverLetterCollectionId = "/db/sample/coverLetters";
    
    
    public String save(String coverLetter, String paperId) throws Exception {
    	//String ID = generateID();
		CoverLetterDAO.store(coverLetterCollectionId, paperId, coverLetter, paperId);
		return paperId;
	}
    
    public String update(String id, String coverLetter) throws Exception {
    	String oldCV = this.findById(id);
        if (oldCV == null) {
            throw new Exception("CV with id: " + id);
        }
        this.delete(id);
        StoreData.store(coverLetterCollectionId, id, coverLetter);
        return id;
	}
    
    public void delete(String id) throws Exception {
		String xpathExp = "/coverLetter";
		long mods = UpdateData.delete(coverLetterCollectionId, id, xpathExp);
		if (mods == 0) {
			throw new EntityNotFound(id);
		}
		deleteMetadata(id);
	}
    
    public String findById(String id) throws Exception {
    	return CoverLetterDAO.retrieve(coverLetterCollectionId, id);
	}
    
    public void saveMetadata(StringWriter metadata, String cvId) throws Exception {
		RDFStore.store(metadata, "/example/coverLetter/" + cvId);
	}

	public void deleteMetadata(String cvId) throws Exception {
		RDFUpdate.remove("/example/coverLetter/" + cvId);
	}

	public void updateMetadata(StringWriter metadata, String cvId) throws Exception {
		String url = "/example/coverLetter/" + cvId;
		deleteMetadata(cvId);
		RDFStore.store(metadata, url);
	}
    
    public String generateID() throws Exception{
    	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmssSS");
    	String ID = "CV_" + sdf.format(new Date());
    	return ID;
    }
}
