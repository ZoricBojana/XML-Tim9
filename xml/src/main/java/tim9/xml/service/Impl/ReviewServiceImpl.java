package tim9.xml.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim9.xml.repository.ReviewRepository;
import tim9.xml.service.ReviewService;
import tim9.xml.util.XSLFOTransformer.XSLFOTransformer;

@Service
public class ReviewServiceImpl implements ReviewService {

	private final String XSLT_TEMPLATE_PATH = "src/main/resources/data/xslt/Review.xsl";
	
	@Autowired
	private XSLFOTransformer xslfoTransformer;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Override
	public String save(String review) throws Exception{
		// TODO save metadata!
		String ID = reviewRepository.save(review);
		return ID;
	}

	@Override
	public String findById(String id) throws Exception {
		
		return reviewRepository.findById(id);
	}

	@Override
	public String findByIdHTML(String id) throws Exception {
		String review = reviewRepository.findById(id);
		
		String reviewHTML = xslfoTransformer.generateHTML(review, XSLT_TEMPLATE_PATH);
		
		return reviewHTML;
	}
	
	

}
