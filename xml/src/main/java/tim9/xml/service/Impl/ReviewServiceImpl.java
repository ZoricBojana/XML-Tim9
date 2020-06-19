package tim9.xml.service.Impl;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.msb.BussinessProcess;
import rs.ac.uns.msb.BussinessProcess.Reviews;
import rs.ac.uns.msb.BussinessProcess.Reviews.ReviewData;
import rs.ac.uns.msb.Review;
import tim9.xml.dao.BussinessProcessDAO;
import tim9.xml.dao.ReviewDAO;
import tim9.xml.repository.ReviewRepository;
import tim9.xml.service.ReviewService;
import tim9.xml.util.NSPrefixMapper;
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
	
	public static void main(String[] args) {
		ReviewService service = new ReviewServiceImpl();
		try {
			service.getReviewsForPaper("SA_1906202019583244");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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

	@Override
	public ByteArrayOutputStream getReviewsForPaper(String paperId) throws Exception {
		
		BussinessProcess processWithPaper = BussinessProcessDAO.getProcesByPaperId(paperId).get(0);
		
		List<String> reviewIds = new ArrayList<String>();
		
		for (ReviewData data : processWithPaper.getReviews().getReviewData()) {
			reviewIds.add(data.getReviewId());
		}
		
		List<Review> reviews = ReviewDAO.findByIdList(reviewIds);
		
		rs.ac.uns.msb.Reviews reviewWrapper = new rs.ac.uns.msb.Reviews();
		
		reviewWrapper.setReview(reviews);
		
		JAXBContext context = JAXBContext.newInstance("rs.ac.uns.msb");
		OutputStream os = new ByteArrayOutputStream();

		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NSPrefixMapper());
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		// marshal the contents to an output stream
		marshaller.marshal(reviewWrapper, os);
		
		String revString = os.toString();
		
		System.out.println(revString);
		
		ByteArrayOutputStream clPDF = xslfoTransformer.generatePDF(revString,
				"src/main/resources/data/xslt/reviews_fo.xsl");
		return clPDF;
	}
	
	

}
