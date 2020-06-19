package tim9.xml.service.Impl;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import rs.ac.uns.msb.Author;
import rs.ac.uns.msb.BussinessProcess;
import rs.ac.uns.msb.BussinessProcess.Reviews;
import rs.ac.uns.msb.BussinessProcess.Reviews.ReviewData;
import rs.ac.uns.msb.ScientificArticle;
import tim9.xml.dao.ScientificArticleDAO;
import tim9.xml.dto.ProcessDTO;
import tim9.xml.repository.BussinessProcessRepository;
import tim9.xml.repository.ScientificArticleRepository;
import tim9.xml.service.BussinessProcessService;

@Service
public class BussinessProcessServiceImpl implements BussinessProcessService {

	@Autowired
	private BussinessProcessRepository bpRepository;
	
	@Autowired
	private ScientificArticleRepository saRepository;

	@Override
	public String save(ProcessDTO dto) throws Exception {
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		BussinessProcess process = new BussinessProcess();
		Reviews revs = new Reviews();
		process.setReviews(revs);
		process.setArticleId(dto.getArticleId());
		process.setEditorId(username);
		process.setPhase("waiting");
		
		for (String reviewerUsername : dto.getReviewersId()) {
			ReviewData data = new ReviewData();
			data.setReviewerId(reviewerUsername);
			process.getReviews().getReviewData().add(data);
		}
		

		String id = bpRepository.save(process);
		
		String paperId = dto.getArticleId();
		
		String articleString = saRepository.findById(paperId);
		
		JAXBContext context;// = JAXBContext.newInstance("rs.ac.uns.msb");

		context = JAXBContext.newInstance(ScientificArticle.class);
    	Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
    	ScientificArticle article = (ScientificArticle) jaxbUnmarshaller.unmarshal(new StringReader(articleString));

		saRepository.remove(paperId);
		saRepository.changeStatur(paperId, article, "reviewing");
		
		return id;
	}

	@Override
	public String findById(String id) throws Exception {
		
		return bpRepository.findById(id);
	}

	@Override
	public void delete(String id) throws Exception {
		
		bpRepository.delete(id);
	}

	@Override
	public String update(String id, String process) throws Exception {

		//return bpRepository.update(id, process);
		return null;
	}

	@Override
	public void rejectReview(String paperId) throws Exception {
		
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
		bpRepository.rejectReview(paperId, username);
		
	}

}
