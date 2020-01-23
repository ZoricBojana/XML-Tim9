package tim9.xml.repository;

import static tim9.xml.util.template.XUpdateTemplate.TARGET_NAMESPACE;

import org.exist.xmldb.EXistResource;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import tim9.xml.util.exist.RetriveData;
import tim9.xml.util.exist.StoreData;
import tim9.xml.util.exist.UpdateData;

@Repository
public class ScientificArticleRepository {

	public static String scientificArticleCollectionId = "/db/sample/scientificArticle";

	public static String scientificArticleSchemaPath = "src/main/resources/data/ScienceArticleSchema.xsd";
	public static String ScientificArticleXSLPath = "src/main/resources/data/xslt/scientificArticle.xsl";

	public String findOne(String id) throws Exception {
		String xPathExp = String.format("//scientificArticle[@id='%s']", id);
		ResourceSet resultSet = RetriveData.executeXPathExpression(scientificArticleCollectionId, xPathExp,
				TARGET_NAMESPACE);
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

	public String create(String id, String scientificPaper) throws Exception {
		StoreData.store(scientificArticleCollectionId, id, scientificPaper);
		return id;
	}

	public String update(String id, String scientificPaper) throws Exception {
		String oldScientificPaperData = this.findOne(id);
		if (oldScientificPaperData == null) {
			throw new Exception("Scientific Article with id: " + id);
		}
		this.delete(id);
		StoreData.store(scientificArticleCollectionId, id, scientificPaper);
		return id;
	}

	public void delete(String id) throws Exception {
		String xPathExp = "/scientificArticle";

		long mods = UpdateData.delete(scientificArticleCollectionId, id, xPathExp);
		if (mods == 0) {
			throw new Exception(String.format("Scientific Paper with documentId %s", id));
		}
	}
}
