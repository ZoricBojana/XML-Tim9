package tim9.xml.repository;

import static tim9.xml.util.template.XUpdateTemplate.TARGET_NAMESPACE;

import java.io.StringWriter;

import org.exist.xmldb.EXistResource;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import tim9.xml.exception.EntityNotFound;
import tim9.xml.util.DOMParser.DOMParser;
import tim9.xml.util.RDF.RDFStore;
import tim9.xml.util.RDF.RDFUpdate;
import tim9.xml.util.exist.RetriveData;
import tim9.xml.util.exist.StoreData;
import tim9.xml.util.exist.UpdateData;

@Repository
public class CoverLetterRepository {
	
	public static final String ScienceArticleID = "href";
	public static String coverLetterCollectionId = "/db/sample/coverLetters";
    
    
    public String save(String coverLetter) throws Exception {
    	String ID = generateID();
		Document document = DOMParser.buildDocument(coverLetter, "src/main/resources/data/coverLetter.xsd");
		document.getElementsByTagName("coverLetter").item(0).getAttributes().getNamedItem("ID").setTextContent(ID);
		String toSave = DOMParser.parseDocument(document);
		StoreData.store(coverLetterCollectionId, ID, toSave);
		return ID;
	}
    
    public String update(String coverLetter) throws Exception {
		Document document = DOMParser.buildDocument(coverLetter, "src/main/resources/data/coverLetter.xsd");
		String id = document.getDocumentElement().getAttribute("id");

		String oldCoverLetterData = findById(id);
		if (oldCoverLetterData == null) {
			throw new EntityNotFound(id);
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
		String retVal = null;
		String expression = "//coverLetter[@id=\"" + id + "\"]";
		ResourceSet result = RetriveData.executeXPathExpression(
				coverLetterCollectionId,
				expression,
				TARGET_NAMESPACE);
		
		if (result == null) {
			return retVal;
		}
		
		ResourceIterator iterator = result.getIterator();
		XMLResource resource = null;
		
		while (iterator.hasMoreResources()) {
			try {
				resource = (XMLResource) iterator.nextResource();
				retVal = resource.getContent().toString();
				return retVal;
			} finally {
				// don't forget to cleanup resources
				try {
					((EXistResource) resource).freeResources();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}
		return null;
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
    	String retVal = "CL";
    	int i = 1;
    	String expression = "//coverLetter";
    	ResourceSet result = RetriveData.executeXPathExpression(
				coverLetterCollectionId,
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
		return retVal + i;
    }

	
}
