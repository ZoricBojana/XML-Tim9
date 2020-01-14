package tim9.xml.service.Impl;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim9.xml.exception.EntityNotFound;
import tim9.xml.repository.CoverLetterRepository;
import tim9.xml.service.CoverLetterService;
import tim9.xml.util.RDF.MetadataExtractor;
import tim9.xml.util.XSLFOTransformer.XSLFOTransformer;

@Service
public class CoverLetterServiceImpl implements CoverLetterService{

	@Autowired
	private XSLFOTransformer xslFoTransformer;

	@Autowired
	private MetadataExtractor metadataExtractor;
	
	@Autowired
	private CoverLetterRepository coverLetterRepository;
	
	

	@Override
	public String save(String coverLetter) throws Exception {
		// extract metadata
		StringWriter out = new StringWriter(); 
		StringReader in = new StringReader(coverLetter); 
		metadataExtractor.extractMetadata(in, out);
		
		String ID = coverLetterRepository.save(coverLetter);
		coverLetterRepository.saveMetadata(out, ID);
		return ID;
	}
	
	@Override
	public String update(String coverLetter) throws Exception {
		// extract metadata
		StringWriter out = new StringWriter(); 
		StringReader in = new StringReader(coverLetter); 
		metadataExtractor.extractMetadata(in, out);
		
		String ID = coverLetterRepository.update(coverLetter);
		coverLetterRepository.updateMetadata(out, ID);
		return ID;
	}

	@Override
	public void delete(String id) {
		try {
			coverLetterRepository.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String findById(String id) throws Exception {
		String coverLetter = coverLetterRepository.findById(id);
		if(coverLetter == null){
			throw new EntityNotFound(id);
		}
		return coverLetter;
	}

	@Override
	public String findByIdHTML(String id) throws Exception {
		String coverLetter = coverLetterRepository.findById(id);
		if(coverLetter == null){
			throw new EntityNotFound(id);
		}
		String clHTML = xslFoTransformer.generateHTML(coverLetter, "src/main/resources/data/xslt/coverLetter.xsl");
		return clHTML;
	}

	@Override
	public ByteArrayOutputStream findByIdPDF(String id) throws Exception {
		String coverLetter = coverLetterRepository.findById(id);
		if(coverLetter == null){
			throw new EntityNotFound(id);
		}
		ByteArrayOutputStream clPDF = xslFoTransformer.generatePDF(coverLetter,
				"src/main/resources/data/xsl-fo/coverLetter_fo.xsl");
		return clPDF;
	}

}
