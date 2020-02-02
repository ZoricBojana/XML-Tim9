package rs.ac.uns.msb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "role" })
@XmlRootElement(name = "roles")
public class Roles {
	
	@XmlElement(name = "role", defaultValue = "ROLE_USER", required = true)
	protected List<String> role;

	
	public List<String> getRole() {
		if (role == null) {
			role = new ArrayList<String>();
		}
		
		return this.role;
	}
}
