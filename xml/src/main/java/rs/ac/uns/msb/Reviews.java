package rs.ac.uns.msb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "review"
})
@XmlSeeAlso(ArrayList.class)
@XmlRootElement(name = "reviews")
public class Reviews {

	@XmlElement(required = true)
    protected List<Review> review;
	
	public List<Review> getReview() {
		if (review == null) {
			review = new ArrayList<Review>();
		}
		
		return this.review;
	}

	public void setReview(List<Review> review) {
		this.review = review;
	}
}
