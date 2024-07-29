package com.tujuhsembilan.app.exception.classes;



public class MinioUploadException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -235831481930691230L;

	public MinioUploadException(String message) {
        super(message);
    }

    public MinioUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
