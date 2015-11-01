/**
 * 
 */
package com.friendsurance;

import static org.junit.Assert.assertEquals;

import java.io.Reader;
import java.io.StringReader;
import java.util.Set;
import java.util.SortedSet;

import org.junit.Before;
import org.junit.Test;

import com.friendsurance.backend.User;
import com.friendsurance.impl.rules.RegexRuleParser;
import com.friendsurance.impl.rules.RuleEngine;
import com.friendsurance.impl.rules.model.Rule;

/**
 * @author durrah
 *
 */
public class RuleEngineTest {

	private RuleEngine ruleEngine;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		String string = "0 & >1 & <9   => 3 \n 0 & =2 & <9   => 2";
		Reader reader = new StringReader(string);
		ruleEngine = new RuleEngine.Builder().setRuleParser(new RegexRuleParser()).setRulesContentReader(reader)
				.build();
		ruleEngine.start();
	}

	@Test
	public void testApplicableMultiple() {
		User user = new User("test@test.com", false, 2, 0);
		Set<Rule> applicable = ruleEngine.applicableRules(user);
		assertEquals(applicable.size(), 2);
	}

	@Test
	public void testApplicableSingle() {
		User user = new User("test@test.com", false, 3, 0);
		Set<Rule> applicable = ruleEngine.applicableRules(user);
		assertEquals(applicable.size(), 1);
	}

	@Test
	public void testApplicableNone() {
		User user = new User("test@test.com", false, 1, 0);
		Set<Rule> applicable = ruleEngine.applicableRules(user);
		assertEquals(applicable.size(), 0);
	}

	@Test
	public void testAppliedRule() {
		User user = new User("test@test.com", false, 2, 0);
		Set<Rule> applicable = ruleEngine.applicableRules(user);
		assertEquals(applicable.size(), 2);
		/**
		 * the applied rule is (0 & >1 & <9 => 3) as it has max priority
		 */
		Rule rule = ((SortedSet<Rule>) applicable).first();

		assertEquals(rule.getApplyResult(), 3);
	}

}
