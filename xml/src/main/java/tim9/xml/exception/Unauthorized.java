package tim9.xml.exception;

public class Unauthorized extends RuntimeException{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;

	public Unauthorized(){
		this.message = "Unauthorized access denied.";
	}
	
	public String getMessage() {
		return message;
	}
}
