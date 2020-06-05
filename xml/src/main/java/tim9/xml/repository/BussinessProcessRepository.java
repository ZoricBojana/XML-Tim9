package tim9.xml.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import tim9.xml.dao.BussinessProcessDAO;
import tim9.xml.exception.EntityNotFound;
import tim9.xml.util.exist.UpdateData;

@Repository
public class BussinessProcessRepository {
	
public static String bussinessProcessCollectionId = "/db/sample/bussinessProcess";
    
    
    public String save(String process) throws Exception {
    	String ID = UUID.randomUUID().toString();
		BussinessProcessDAO.store(bussinessProcessCollectionId, ID, process);
		return ID;
	}
    
    public String update(String id, String process) throws Exception {
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
		//deleteMetadata(id);
	}
    
    public String findById(String id) throws Exception {
    	return BussinessProcessDAO.retrieve(bussinessProcessCollectionId, id);
	}

}
