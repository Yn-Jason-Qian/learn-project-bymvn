package algorithm.Exception;

public class OverFlowException extends RuntimeException{

	private static final long serialVersionUID = -8418604425666482230L;

	public OverFlowException() {
		super();
	}

	public OverFlowException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public OverFlowException(String message, Throwable cause) {
		super(message, cause);
	}

	public OverFlowException(String message) {
		super(message);
	}

	public OverFlowException(Throwable cause) {
		super(cause);
	}

	
	
}
