package com.friendsurance.impl.rules.model;

/**
 * the condition part/s of the rule
 * 
 * @author durrah
 *
 */
public class Condition {
	private long value;
	private Operation op;

	public Condition(long value, Operation op) {
		this.value = value;
		this.op = op;
	}

	public boolean applies(long num) {
		switch (this.op) {
		case EQ:
			return num == value;
		case GT:
			return num > value;
		case LT:
			return num < value;
		default:
			return false;
		}
	}

	@Override
	public String toString() {
		return "" + op + value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((op == null) ? 0 : op.hashCode());
		result = prime * result + (int) (value ^ (value >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Condition other = (Condition) obj;
		if (op != other.op)
			return false;
		if (value != other.value)
			return false;
		return true;
	}

}