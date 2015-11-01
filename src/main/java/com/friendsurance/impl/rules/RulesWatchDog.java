/**
 * 
 */
package com.friendsurance.impl.rules;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Set;

import com.friendsurance.impl.Configs;
import com.friendsurance.impl.rules.model.Rule;

/**
 * as a part of dynamic rule to be flexible this class provide a utility to
 * watch a folder which path is configurable and watch changes to this folder or
 * files contained within, and fire a change in the rules held by the
 * {@link RuleEngine}.<br>
 * using Java 7 NIO.2 this is simply achieved using {@link WatchService}
 * 
 * @author durrah
 *
 */
public class RulesWatchDog extends Thread {
	private WatchService watchService;
	private String rulesFolder;
	private Set<Rule> rules;
	private RulesLoader rulesReader;

	/**
	 * constructor and initialization
	 * 
	 * @param rules
	 * @param rulesReader
	 * @param ruleParser
	 * @throws IOException
	 */
	public RulesWatchDog(Set<Rule> rules, RulesLoader rulesReader, RuleParser ruleParser) throws IOException {
		this.rulesReader = new RulesLoader(ruleParser);
		this.rules = rules;

		watchService = FileSystems.getDefault().newWatchService();
		rulesFolder = Configs.get("rulesfolder");
		Path path = Paths.get(rulesFolder);
		path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
	}

	/**
	 * thread run method, run until an interrupt sent
	 */
	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				final WatchKey key = watchService.take();
				for (WatchEvent<?> watchEvent : key.pollEvents()) {
					final Kind<?> kind = watchEvent.kind();
					if (kind == StandardWatchEventKinds.OVERFLOW)
						continue;

					final WatchEvent<Path> watchEventPath = (WatchEvent<Path>) watchEvent;
					final Path filename = watchEventPath.context();
					File file = Paths.get(rulesFolder + filename.getFileName()).toFile();
					// reload rules
					FileReader fileReader = new FileReader(file);
					this.rules.addAll(rulesReader.loadRules(fileReader));
					boolean valid = key.reset();
					if (!valid)
						break;
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
