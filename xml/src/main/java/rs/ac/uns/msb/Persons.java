package rs.ac.uns.msb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "person"
})
@XmlRootElement(name = "persons")
public class Persons {
	
	@XmlElement(required = true)
    protected List<Person> person;

    public List<Person> getPerson() {
        if (person == null) {
            person = new ArrayList<Person>();
        }
        return this.person;
    }
}
