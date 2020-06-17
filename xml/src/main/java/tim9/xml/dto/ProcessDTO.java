package tim9.xml.dto;

public class ProcessDTO {

	private String articleId;
	private String[] reviewersId;

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String[] getReviewersId() {
		return reviewersId;
	}

	public void setReviewersId(String[] reviewersId) {
		this.reviewersId = reviewersId;
	}

}
