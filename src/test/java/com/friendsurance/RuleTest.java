package com.friendsurance;

import org.junit.Before;
import org.junit.Test;

import com.friendsurance.impl.rules.RegexRuleParser;
import com.friendsurance.impl.rules.RuleParser;
import com.friendsurance.impl.rules.model.Condition;
import com.friendsurance.impl.rules.model.InvalidRuleSyntaxException;
import com.friendsurance.impl.rules.model.Operation;
import com.friendsurance.impl.rules.model.Rule;
import static org.junit.Assert.*;

public class RuleTest {

	private RuleParser ruleParser;

	@Before
	public void init() {
		ruleParser = new RegexRuleParser();
	}

	@Test(expected = InvalidRuleSyntaxException.class)
	public void testInvalidRule() throws Exception {
		String ruleString = "0  >8 => 1"; // invalid rule missing &
		Rule rule = ruleParser.parseRule(ruleString);
	}

	@Test
	public void testRuleValid() throws Exception {
		String ruleString = "0 & >8 & <9   => 1";
		Rule rule = ruleParser.parseRule(ruleString);
		assertEquals(rule.isContract(), false);
		assertEquals(rule.getFriendsCondition(), new Condition(8, Operation.GT));
		assertEquals(rule.getInvitationsCondition(), new Condition(9, Operation.LT));
	}

	@Test
	public void testRuleValidWithNullInvitation() throws Exception {
		String ruleString = "0 & >8 => 1";
		Rule rule = ruleParser.parseRule(ruleString);
		assertEquals(rule.isContract(), false);
		assertEquals(rule.getFriendsCondition(), new Condition(8, Operation.GT));
		assertNull(rule.getInvitationsCondition());
	}

}
