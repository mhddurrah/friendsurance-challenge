/**
 * 
 */
package com.friendsurance.impl.model;

import com.friendsurance.mail.EmailRecipient;

/**
 * the result of processing
 * 
 * @author durrah
 *
 */
public class PendingMessage {

	private EmailRecipient recipient;
	private int ruleOutput;

	public PendingMessage() {
	}

	public PendingMessage(EmailRecipient recipient, int ruleOutput) {
		super();
		this.recipient = recipient;
		this.ruleOutput = ruleOutput;
	}

	public EmailRecipient getRecipient() {
		return recipient;
	}

	public int getRuleOutput() {
		return ruleOutput;
	}

	public static class PoisonPillPendingMessage extends PendingMessage {
	}
}
