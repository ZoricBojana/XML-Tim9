package tim9.xml.exception;

public class EntityNotFound extends RuntimeException{

	/**
	 * 
	 */
	private String message;
	
	private static final long serialVersionUID = 1L;

	public EntityNotFound(String id){
		this.message = "Entity with ID "+ id+" could not be found";
	}

	public String getMessage() {
		return message;
	}
}
