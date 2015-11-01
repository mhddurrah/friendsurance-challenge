package com.friendsurance.impl.rules.model;

/**
 * comparison operation in {@link Condition}
 * 
 * @author durrah
 *
 */
public enum Operation {
	GT(">"), LT("<"), EQ("=");
	private String op;

	private Operation(String op) {
		this.op = op;
	}

	public String getOp() {
		return op;
	}

	@Override
	public String toString() {
		return op;
	}

	/**
	 * get an instance of {@link Operation} using a String
	 * 
	 * @param cond
	 * @return
	 * @throws IllegalArgumentException
	 *             if the input string does not have a related {@link Operation}
	 *             value
	 */
	public static Operation fromString(String cond) {
		for (Operation tp : values())
			if (tp.getOp().equals(cond))
				return tp;
		throw new IllegalArgumentException("Condition " + cond + " not found");
	}
}