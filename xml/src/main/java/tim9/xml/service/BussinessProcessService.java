package tim9.xml.service;

public interface BussinessProcessService {

	public String save(String process) throws Exception;
	
	public String update(String id, String process) throws Exception;

	public String findById(String id) throws Exception;

	public void delete(String id) throws Exception;
}
