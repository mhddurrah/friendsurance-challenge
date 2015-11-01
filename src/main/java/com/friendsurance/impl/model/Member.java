package com.friendsurance.impl.model;

import com.friendsurance.backend.User;
import com.friendsurance.mail.EmailRecipient;

/**
 * member is a class that combines {@link User} and {@link EmailRecipient}
 * functionalities
 * 
 * @author durrah
 *
 */
public class Member extends User implements EmailRecipient {

	public Member(String email, boolean hasContract, int friendsNumber, int sentInvitationsNumber) {
		super(email, hasContract, friendsNumber, sentInvitationsNumber);
	}

	/**
	 * create a member from a string input
	 * 
	 * @param line
	 * @return
	 * @throws Exception
	 *             either {@link InvalidMemberSyntaxException} or
	 *             {@link NumberFormatException}
	 */
	public static Member fromString(String line) throws Exception {
		String[] items = line.split(",");
		if (items.length < 4)
			throw new InvalidMemberSyntaxException();
		String email = items[0].trim();
		String contractString = items[1].trim();
		boolean hasContract = contractString.equalsIgnoreCase("true");
		String friendsNumberString = items[2].trim();
		int friendsNumber = Integer.parseInt(friendsNumberString);
		String sentInvitationsNumberString = items[2].trim();
		int sentInvitationsNumber = Integer.parseInt(sentInvitationsNumberString);
		return new Member(email, hasContract, friendsNumber, sentInvitationsNumber);
	}

	/**
	 * indicate that the input data read finished
	 * 
	 * @author durrah
	 *
	 */
	public static class NullMember extends Member {

		public NullMember(String email, boolean hasContract, int friendsNumber, int sentInvitationsNumber) {
			super(email, hasContract, friendsNumber, sentInvitationsNumber);
		}
	}

}
