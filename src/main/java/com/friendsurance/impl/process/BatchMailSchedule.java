/**
 * 
 */
package com.friendsurance.impl.process;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

import com.friendsurance.impl.mail.BlockingQueueEmailService;
import com.friendsurance.impl.model.PendingMessage;

/**
 * @author durrah
 *
 */
public class BatchMailSchedule {
	final static Logger LOGGER = Logger.getLogger(BatchMailSchedule.class.getCanonicalName());

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		BlockingQueue<PendingMessage> messages = new LinkedBlockingQueue<PendingMessage>();

		BlockingQueueEmailService emailService = new BlockingQueueEmailService(messages);
		Thread emailServiceThread = new Thread(emailService, "EmailService Thread");
		emailServiceThread.start();

		Job job = new Job(messages);
		try {
			job.execute();
		} catch (JobExecutionException e1) {
			LOGGER.severe("error executing job, to retry");
			System.exit(-1);
		}

		try {
			emailServiceThread.join();
		} catch (InterruptedException e) {
			// ignore
		}
	}

}
