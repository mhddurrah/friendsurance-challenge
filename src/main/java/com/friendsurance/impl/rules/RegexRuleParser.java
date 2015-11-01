package com.friendsurance.impl.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.friendsurance.impl.rules.model.Condition;
import com.friendsurance.impl.rules.model.InvalidConditionException;
import com.friendsurance.impl.rules.model.InvalidRuleSyntaxException;
import com.friendsurance.impl.rules.model.Operation;
import com.friendsurance.impl.rules.model.Rule;
import com.friendsurance.impl.rules.model.RuleParseException;

/**
 * our default rule parser which create rules from a given string by applying
 * regular expressions.<br/>
 * the rule syntax for this parse is very simple: <br/>
 * <C> & <OP> <VALUE> [& <OP> <VALUE>] => <PRIORITY> <br/>
 * where <C> is either 0 or 1 which related to contract;<br/>
 * <p>
 * {@link Condition} <br>
 * the first <OP> <VALUE> is for friends number; for example: > 4 <br/>
 * the second optional <OP> <VALUE> is for invitations number; for example: < 2
 * <br/>
 * </p>
 * <PRIORITY> is for priority or rule application result <br/>
 * sample rules:<br/>
 * 0 & > 2 & = 0 => 2<br/>
 * 0 & > 2 => 5
 * 
 * @author durrah
 *
 */
public class RegexRuleParser implements RuleParser {
	/**
	 * regular expression for rule
	 */
	private final static Pattern PATTERN = Pattern.compile(
			"^\\s*(\\s*\\d+\\s*)\\s*&\\s*(\\s*[=><]\\s*\\d+\\s*)\\s*(&\\s*(\\s*[=><]\\s*\\d+\\s*))?\\s*=>\\s*(\\d+)\\s*");

	/**
	 * regular expression for condition
	 */
	private final static Pattern CONDITION_PATTERN = Pattern.compile("\\s*([=><])\\s*(\\d+)\\s*");

	/**
	 * get rule from String
	 * 
	 * @return rule object<br>
	 * @throws {@link
	 *             RuleParseException} if the syntax is invalid for rule that
	 *             caused also by invalid {@link Condition}
	 */
	public Rule parseRule(String line) throws RuleParseException {
		Matcher matcher = PATTERN.matcher(line);
		Rule rule = new Rule();

		if (matcher.matches()) {
			int contract = Integer.parseInt(matcher.group(1).trim());
			rule.setContract(contract == 1);
			rule.setApplyResult(Integer.parseInt(matcher.group(5).trim()));
			rule.setFriendsCondition(getCondition(matcher.group(2)));
			rule.setInvitationsCondition(getCondition(matcher.group(4)));
			return rule;
		}
		throw new InvalidRuleSyntaxException();

	}

	/**
	 * return an instance of {@link Condition}
	 * 
	 * @param cond
	 * @return
	 * @throws InvalidConditionException
	 */
	private Condition getCondition(String cond) throws InvalidConditionException {
		if (cond == null || "null".equalsIgnoreCase(cond))
			return null;
		cond = cond.trim();
		Matcher matcher = CONDITION_PATTERN.matcher(cond);
		if (matcher.matches()) {
			String op = matcher.group(1);
			String val = matcher.group(2);
			Operation operation = Operation.fromString(op);
			long value = Long.parseLong(val);
			return new Condition(value, operation);
		}
		throw new InvalidConditionException();
	}

}
