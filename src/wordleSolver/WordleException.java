package wordleSolver;

public class WordleException extends Exception {

	public WordleException() {
		super();
	}

	public WordleException(String message) {
		super(message);
	}

	public WordleException(Throwable cause) {
		super(cause);
	}

	public WordleException(String message, Throwable cause) {
		super(message, cause);
	}

	public WordleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
