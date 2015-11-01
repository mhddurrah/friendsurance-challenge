/**
 * 
 */
package com.friendsurance.impl.rules.model;

import java.util.Comparator;

import com.friendsurance.backend.User;

/**
 * the rule of determining the type of the email to be sent.
 * 
 * @author durrah
 *
 */
public class Rule {
	/**
	 * the result application result, i.e. the priority
	 */
	private int applyResult;

	private boolean isContract;

	private Condition friendsCondition;
	private Condition invitationsCondition;

	/**
	 * is this rule applies for the given user.
	 * 
	 * @param user
	 * @return
	 */
	public boolean applies(User user) {
		boolean result = isContract == user.hasContract() && friendsCondition.applies(user.getFriendsNumber());
		if (invitationsCondition != null)
			result &= invitationsCondition.applies(user.getSentInvitationsNumber());
		return result;
	}

	public int getApplyResult() {
		return applyResult;
	}

	public void setApplyResult(int applyResult) {
		this.applyResult = applyResult;
	}

	public boolean isContract() {
		return isContract;
	}

	public void setContract(boolean isContract) {
		this.isContract = isContract;
	}

	public Condition getFriendsCondition() {
		return friendsCondition;
	}

	public void setFriendsCondition(Condition friendsCondition) {
		this.friendsCondition = friendsCondition;
	}

	public Condition getInvitationsCondition() {
		return invitationsCondition;
	}

	public void setInvitationsCondition(Condition invitationsCondition) {
		this.invitationsCondition = invitationsCondition;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + applyResult;
		result = prime * result + ((friendsCondition == null) ? 0 : friendsCondition.hashCode());
		result = prime * result + ((invitationsCondition == null) ? 0 : invitationsCondition.hashCode());
		result = prime * result + (isContract ? 1231 : 1237);
		System.out.println(result);
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
		Rule other = (Rule) obj;
		if (applyResult != other.applyResult)
			return false;
		if (friendsCondition != other.friendsCondition)
			return false;
		if (invitationsCondition != other.invitationsCondition)
			return false;
		if (isContract != other.isContract)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return (isContract ? 1 : 0) + "&" + friendsCondition + "&" + invitationsCondition + "=>" + applyResult;
	}

	/**
	 * comparator to sort rules by result (priority) in descent order
	 * 
	 * @author durrah
	 *
	 */
	public static class RuleMaxPriorityComparator implements Comparator<Rule> {
		@Override
		public int compare(Rule o1, Rule o2) {
			return o1.getApplyResult() > o2.getApplyResult() ? -1 : o1.getApplyResult() < o2.getApplyResult() ? 1 : 0;
		}

	}

}
