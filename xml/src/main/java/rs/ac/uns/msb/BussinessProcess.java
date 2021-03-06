//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.03 at 12:54:50 PM CEST 
//

package rs.ac.uns.msb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="article_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="author_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reviews">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="review_data" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="reviewer_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="review_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="phase" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "id", "articleId", "editorId", "reviews" })
@XmlRootElement(name = "bussiness_process")
public class BussinessProcess {

	@XmlElement(required = true)
	protected String id;
	@XmlElement(name = "article_id", required = true)
	protected String articleId;
	@XmlElement(name = "editor_id", required = true)
	protected String editorId;
	@XmlElement(required = true)
	protected BussinessProcess.Reviews reviews;
	@XmlAttribute(name = "phase")
	protected String phase;

	/**
	 * Gets the value of the id property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setId(String value) {
		this.id = value;
	}

	/**
	 * Gets the value of the articleId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getArticleId() {
		return articleId;
	}

	/**
	 * Sets the value of the articleId property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setArticleId(String value) {
		this.articleId = value;
	}

	/**
	 * Gets the value of the authorId property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEditorId() {
		return editorId;
	}

	/**
	 * Sets the value of the authorId property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setEditorId(String value) {
		this.editorId = value;
	}

	/**
	 * Gets the value of the reviews property.
	 * 
	 * @return possible object is {@link BussinessProcess.Reviews }
	 * 
	 */
	public BussinessProcess.Reviews getReviews() {
		return reviews;
	}

	/**
	 * Sets the value of the reviews property.
	 * 
	 * @param value allowed object is {@link BussinessProcess.Reviews }
	 * 
	 */
	public void setReviews(BussinessProcess.Reviews value) {
		this.reviews = value;
	}

	/**
	 * Gets the value of the phase property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPhase() {
		return phase;
	}

	/**
	 * Sets the value of the phase property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setPhase(String value) {
		this.phase = value;
	}

	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained within
	 * this class.
	 * 
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;element name="review_data" maxOccurs="unbounded" minOccurs="0">
	 *           &lt;complexType>
	 *             &lt;complexContent>
	 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *                 &lt;sequence>
	 *                   &lt;element name="reviewer_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                   &lt;element name="review_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *                 &lt;/sequence>
	 *               &lt;/restriction>
	 *             &lt;/complexContent>
	 *           &lt;/complexType>
	 *         &lt;/element>
	 *       &lt;/sequence>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "reviewData" })
	public static class Reviews {

		@XmlElement(name = "review_data")
		protected List<BussinessProcess.Reviews.ReviewData> reviewData;

		/**
		 * Gets the value of the reviewData property.
		 * 
		 * <p>
		 * This accessor method returns a reference to the live list, not a snapshot.
		 * Therefore any modification you make to the returned list will be present
		 * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
		 * for the reviewData property.
		 * 
		 * <p>
		 * For example, to add a new item, do as follows:
		 * 
		 * <pre>
		 * getReviewData().add(newItem);
		 * </pre>
		 * 
		 * 
		 * <p>
		 * Objects of the following type(s) are allowed in the list
		 * {@link BussinessProcess.Reviews.ReviewData }
		 * 
		 * 
		 */
		public List<BussinessProcess.Reviews.ReviewData> getReviewData() {
			if (reviewData == null) {
				reviewData = new ArrayList<BussinessProcess.Reviews.ReviewData>();
			}
			return this.reviewData;
		}

		/**
		 * <p>
		 * Java class for anonymous complex type.
		 * 
		 * <p>
		 * The following schema fragment specifies the expected content contained within
		 * this class.
		 * 
		 * <pre>
		 * &lt;complexType>
		 *   &lt;complexContent>
		 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
		 *       &lt;sequence>
		 *         &lt;element name="reviewer_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *         &lt;element name="review_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *       &lt;/sequence>
		 *     &lt;/restriction>
		 *   &lt;/complexContent>
		 * &lt;/complexType>
		 * </pre>
		 * 
		 * 
		 */
		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(name = "", propOrder = { "reviewerId", "reviewId" })
		public static class ReviewData {

			@XmlElement(name = "reviewer_id", required = true)
			protected String reviewerId;
			@XmlElement(name = "review_id", required = true)
			protected String reviewId;

			/**
			 * Gets the value of the reviewerId property.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getReviewerId() {
				return reviewerId;
			}

			/**
			 * Sets the value of the reviewerId property.
			 * 
			 * @param value allowed object is {@link String }
			 * 
			 */
			public void setReviewerId(String value) {
				this.reviewerId = value;
			}

			/**
			 * Gets the value of the reviewId property.
			 * 
			 * @return possible object is {@link String }
			 * 
			 */
			public String getReviewId() {
				return reviewId;
			}

			/**
			 * Sets the value of the reviewId property.
			 * 
			 * @param value allowed object is {@link String }
			 * 
			 */
			public void setReviewId(String value) {
				this.reviewId = value;
			}

		}

	}

}
