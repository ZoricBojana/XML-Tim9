package tim9.xml.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Repository;
import org.xmldb.api.base.XMLDBException;

import rs.ac.uns.msb.BussinessProcess;
import rs.ac.uns.msb.BussinessProcess.Reviews.ReviewData;
import rs.ac.uns.msb.ScientificArticle;
import tim9.xml.dao.BussinessProcessDAO;
import tim9.xml.dao.ScientificArticleDAO;
import tim9.xml.exception.EntityNotFound;
import tim9.xml.util.exist.UpdateData;

@Repository
public class BussinessProcessRepository {

	public static String bussinessProcessCollectionId = "/db/sample/bussinessProcess";

	public static void main(String[] args) {
		BussinessProcessRepository rep = new BussinessProcessRepository();

		try {
			for (ScientificArticle string : rep.getForReviewer("reg")) {
				System.out.println(string.getArticleInfo().getTitle().getValue());
				System.out.println(string.getAuthors());
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IOException | XMLDBException
				| JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String save(BussinessProcess process) throws Exception {
		String ID = UUID.randomUUID().toString();
		process.setId(ID);
		BussinessProcessDAO.store(bussinessProcessCollectionId, ID, process);
		return ID;
	}

	public String update(String id, BussinessProcess process) throws Exception {
		String oldProcess = this.findById(id);
		if (oldProcess == null) {
			throw new Exception("Bussiness process with id: " + id);
		}
		this.delete(id);
		BussinessProcessDAO.store(bussinessProcessCollectionId, id, process);
		return id;
	}

	public void delete(String id) throws Exception {
		String xpathExp = "/bussinessProcess";
		long mods = UpdateData.delete(bussinessProcessCollectionId, id, xpathExp);
		if (mods == 0) {
			throw new EntityNotFound(id);
		}
		// deleteMetadata(id);
	}

	public String findById(String id) throws Exception {
		return BussinessProcessDAO.retrieve(bussinessProcessCollectionId, id);
	}

	public List<ScientificArticle> getForReviewer(String username) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, IOException, XMLDBException, JAXBException {

		List<BussinessProcess> processes = BussinessProcessDAO.getProcessessForReviewer(username);

		List<String> ids = new ArrayList<String>();

		for (BussinessProcess bussinessProcess : processes) {
			for (ReviewData data : bussinessProcess.getReviews().getReviewData()) {

				if (data.getReviewerId().equals(username)) {
					if (data.getReviewId() == null) {
						ids.add(bussinessProcess.getArticleId());
					}
				}

			}
		}

		for (String string : ids) {
			System.out.println(string);
		}

		List<ScientificArticle> articles = ScientificArticleDAO.findByIdList(ids);

		for (ScientificArticle scientificArticle : articles) {
			scientificArticle.setAuthors(null);
		}

		return articles;
	}

	public List<ScientificArticle> getReviewedForEditor(String username) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, IOException, XMLDBException, JAXBException {

		List<BussinessProcess> processes = BussinessProcessDAO.getDoneProcesses(username);

		List<String> ids = new ArrayList<String>();

		for (BussinessProcess bussinessProcess : processes) {
			ids.add(bussinessProcess.getArticleId());
		}

		for (String string : ids) {
			System.out.println(string);
		}

		List<ScientificArticle> articles = ScientificArticleDAO.findByIdList(ids);

		for (ScientificArticle scientificArticle : articles) {
			scientificArticle.setAuthors(null);
		}

		return articles;
	}

	public void rejectReview(String paperId, String username) throws Exception {
		BussinessProcess process = BussinessProcessDAO.getProcessByUsernameAndPaperId(username, paperId);

		for (ReviewData data : process.getReviews().getReviewData()) {
			if (data.getReviewerId().equals(username)) {
				data.setReviewId("-1");
				break;
			}
		}

		String processId = process.getId();
		this.delete(processId);
		BussinessProcessDAO.store(bussinessProcessCollectionId, processId, process);
	}

}
