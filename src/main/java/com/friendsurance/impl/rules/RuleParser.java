package com.friendsurance.impl.rules;

import com.friendsurance.impl.rules.model.Rule;
import com.friendsurance.impl.rules.model.RuleParseException;

/**
 * the interface to create rules from a String
 * 
 * @author durrah
 *
 */
public interface RuleParser {
	/**
	 * create a rule instance from a given String
	 * 
	 * @param line
	 * @return
	 * @throws Exception
	 *             either {@link RuleParseException} or
	 *             {@link NumberFormatException}
	 */
	public Rule parseRule(String line) throws Exception;
}
