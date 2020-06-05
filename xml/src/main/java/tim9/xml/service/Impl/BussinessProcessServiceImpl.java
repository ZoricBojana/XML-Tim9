package tim9.xml.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim9.xml.repository.BussinessProcessRepository;
import tim9.xml.service.BussinessProcessService;

@Service
public class BussinessProcessServiceImpl implements BussinessProcessService {

	@Autowired
	private BussinessProcessRepository bpRepository;

	@Override
	public String save(String process) throws Exception {

		String id = bpRepository.save(process);
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

		return bpRepository.update(id, process);
	}

}
