package com.friendsurance.impl.process;

import java.util.concurrent.BlockingQueue;

import com.friendsurance.impl.model.PendingMessage;
import com.friendsurance.processing.ItemWriter;

public class BlockingQueueItemWriter implements ItemWriter<PendingMessage> {

	private BlockingQueue<PendingMessage> waitingMessages;

	public BlockingQueueItemWriter(BlockingQueue<PendingMessage> waitingMessages) {
		this.waitingMessages = waitingMessages;
	}

	@Override
	public void write(PendingMessage item) {
		try {
			waitingMessages.put(item);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
