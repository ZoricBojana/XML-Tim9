package tim9.xml.service;

import tim9.xml.dto.ProcessDTO;

public interface BussinessProcessService {

	public String save(ProcessDTO dto) throws Exception;
	
	public String update(String id, String process) throws Exception;

	public String findById(String id) throws Exception;

	public void delete(String id) throws Exception;

	
}
