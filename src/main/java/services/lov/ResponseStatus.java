package services.lov;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ahmed
 *	An enum class to hold all the possible response status from the Twitter API track service
 */
public enum ResponseStatus {
	
	SUCCESS(200, "Success Response"),
	UNAUTHORIZED(400, "HTTP authentication failed"),
	FORBIDDEN(403, "The connecting account is not permitted to access this endpoint."),
	UNKOWN(404, "Unknown error. There is nothing at this URL, which means the resource does not exist."),
	NOT_ACCEPTABLE(406, "Not Acceptable. At least one request parameter is invalid. "),
	TOO_LONG(413, "A parameter list is too long. "),
	RANGE_UNACCEPTABLE(416, "Unacceptable Range "),
	RATE_LIMITED(420, "Rate limit exceeded. The client has connected too frequently."),
	SERVICE_UNAVAILABLE(503, "Service unavailable. A streaming server is temporarily overloaded.");
	
	private int code;
	private String message;
	private static Map<Integer, ResponseStatus> responseStatusMap;
	
	static {
		responseStatusMap = new HashMap<Integer, ResponseStatus>();
		for(ResponseStatus responseStatus : ResponseStatus.values()) {
			responseStatusMap.put(responseStatus.getCode(), responseStatus);
		}
	}
	private ResponseStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
	public static ResponseStatus getByCode(int code) {
		return responseStatusMap.get(code);
	}
}
