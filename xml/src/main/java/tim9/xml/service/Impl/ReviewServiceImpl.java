package tim9.xml.service.Impl;

import java.io.StringReader;
import java.io.StringWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim9.xml.repository.ReviewRepository;
import tim9.xml.service.ReviewService;
import tim9.xml.util.RDF.MetadataExtractor;
import tim9.xml.util.XSLFOTransformer.XSLFOTransformer;

@Service
public class ReviewServiceImpl implements ReviewService {

	private final String XSLT_TEMPLATE_PATH = "src/main/resources/data/xslt/Review.xsl";
	
	@Autowired
	private XSLFOTransformer xslfoTransformer;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private MetadataExtractor metadataExtractor;
	
	@Override
	public String save(String review, String paperId) throws Exception{
		// TODO save metadata!
		
		StringWriter out = new StringWriter();
		StringReader in = new StringReader(review);
		
		metadataExtractor.extractMetadata(in, out);
		
		String ID = reviewRepository.save(review, paperId);
		//reviewRepository.saveMetadata(out, ID);
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

	@Override
	public void delete(String id) throws Exception {
		reviewRepository.delete(id);
	}
	
	

}
