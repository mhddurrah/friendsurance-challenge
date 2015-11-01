/**
 * 
 */
package com.friendsurance.impl.process;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.BlockingQueue;

import com.friendsurance.impl.model.Member;
import com.friendsurance.impl.model.PendingMessage;
import com.friendsurance.processing.ItemReader;
import com.friendsurance.processing.ItemWriter;

/**
 * @author durrah
 *
 */
public class Job {
	private String rulesPath = BatchMailSchedule.class.getClassLoader().getResource("com/friendsurance/impl/rules.rls")
			.getPath();
	private String usersData = BatchMailSchedule.class.getClassLoader().getResource("com/friendsurance/impl/users.data")
			.getPath();
	BlockingQueue<PendingMessage> messages;

	public Job(BlockingQueue<PendingMessage> messages) {
		this.messages = messages;
	}

	public void execute() throws JobExecutionException {
		ItemWriter<PendingMessage> writer = new BlockingQueueItemWriter(messages);
		try {
			ItemReader<Member> reader = new FileItemReader(usersData);
			Reader rulesReader = new FileReader(rulesPath);
			MembershipProcess mailer = new MembershipProcess(reader, writer, rulesReader);

			mailer.setRulesReader(rulesReader);
			mailer.doProcessing();
		} catch (IOException ioe) {
			throw new JobExecutionException(ioe);
		}
	}
}
