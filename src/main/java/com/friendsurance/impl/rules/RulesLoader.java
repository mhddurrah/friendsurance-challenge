package com.friendsurance.impl.rules;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.friendsurance.impl.rules.model.Rule;

/**
 * reads rules from a file and returns a list of {@link Rule} objects
 * 
 * @author durrah
 *
 */
public class RulesLoader {
	/**
	 * @Inject rule parser
	 */
	private RuleParser ruleParser;

	public RulesLoader(RuleParser ruleParser) {
		this.ruleParser = ruleParser;
	}

	/**
	 * load rules from a reader (rules are stored as ascii file/s)
	 * 
	 * @param reader
	 * @return
	 * @throws Exception
	 *             in case of I/O error occurs
	 */
	public List<Rule> loadRules(Reader reader) throws Exception {
		List<Rule> rules = new ArrayList<>();
		try (BufferedReader bufferedReader = new BufferedReader(reader)) {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.trim().isEmpty() || line.startsWith("#"))
					continue;
				try {
					Rule rule = ruleParser.parseRule(line);
					rules.add(rule);
				} catch (Exception e) {
					// ignore invalid rules, only add valid rules
					System.out.println("error: " + line);
				}
			}
		}
		return rules;
	}
}
