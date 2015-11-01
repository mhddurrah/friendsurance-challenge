/**
 * 
 */
package com.friendsurance.impl.model;

/**
 * @author durrah
 *
 */
public class InvalidMemberSyntaxException extends Exception {

	/**
	 * 
	 */
	public InvalidMemberSyntaxException() {
	}

	/**
	 * @param message
	 */
	public InvalidMemberSyntaxException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public InvalidMemberSyntaxException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidMemberSyntaxException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public InvalidMemberSyntaxException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
