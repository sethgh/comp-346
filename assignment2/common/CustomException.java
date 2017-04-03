package common;

public class CustomException extends Exception {
	public CustomException(String s) {
		super("CustomException: "+s);
	}
}
