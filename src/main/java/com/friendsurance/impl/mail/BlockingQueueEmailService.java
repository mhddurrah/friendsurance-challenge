/**
 * 
 */
package com.friendsurance.impl.mail;

import java.util.concurrent.BlockingQueue;

import com.friendsurance.impl.model.PendingMessage;
import com.friendsurance.impl.model.PendingMessage.PoisonPillPendingMessage;
import com.friendsurance.mail.EmailRecipient;
import com.friendsurance.mail.EmailService;

/**
 * @author durrah
 *
 */
public class BlockingQueueEmailService implements EmailService, Runnable {

	BlockingQueue<PendingMessage> messages;

	/**
	 * 
	 */
	public BlockingQueueEmailService(BlockingQueue<PendingMessage> messages) {
		this.messages = messages;
	}

	@Override
	public void sendMail(EmailRecipient recipient, MailType mailType) {
		System.out.println("send email to '" + recipient.getEmail() + "' with type: " + mailType);
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				processPendingMessage(messages.take());
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	private void processPendingMessage(PendingMessage message) throws InterruptedException {
		if (message instanceof PoisonPillPendingMessage)
			throw new InterruptedException("Posion Pill.. stop");
		MailType type = MailType.values()[message.getRuleOutput()];
		sendMail(message.getRecipient(), type);
	}

}
