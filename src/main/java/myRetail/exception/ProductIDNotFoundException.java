package myRetail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductIDNotFoundException extends RuntimeException {

	public ProductIDNotFoundException(String message) {
		super(message);
	}

}