package backend.spring.common;

public interface ResponseMessage {
	// HTTP Status 200
	String SUCCESS = "Success.";

	// HTTP Status 400
	String INVALID_FORMAT = "Invalid input format.";
	String OPENAI_LIMIT = "GPT API usage exceeded.";

	// HTTP Status 500
	String DATABASE_ERROR = "Datatbase error.";
}
