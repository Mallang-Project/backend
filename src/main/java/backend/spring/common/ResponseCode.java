package backend.spring.common;

public interface ResponseCode {
	// HTTP Status 200
	String SUCCESS = "SU";

	// HTTP Status 400
	String INVALID_FORMAT = "IF";
	String OPENAI_LIMIT = "OL";

	// HTTP Status 500
	String DATABASE_ERROR = "DBE";

}
