package services.exceptions;

public class TwitterMessagesInquiryException extends Exception{
	
	public TwitterMessagesInquiryException() {
		super();
	}
	
	public TwitterMessagesInquiryException(final String message) {
		super(message);
	}
	
	public TwitterMessagesInquiryException(final String message, final Throwable t) {
		super(message, t);
	}
	
	public TwitterMessagesInquiryException(final Throwable t) {
		super(t);
	}
}
