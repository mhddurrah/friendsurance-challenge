/**
 * 
 */
package com.friendsurance;

import java.io.StringReader;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.friendsurance.impl.rules.RegexRuleParser;
import com.friendsurance.impl.rules.RulesLoader;
import com.friendsurance.impl.rules.model.Rule;
import static org.junit.Assert.*;

/**
 * @author durrah
 *
 */
public class RulesLoaderTest {

	private RulesLoader rulesReader;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		rulesReader = new RulesLoader(new RegexRuleParser());
	}

	/**
	 * Test method for
	 * {@link com.friendsurance.impl.rules.RulesLoader#loadRules(java.io.Reader)}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test
	public void testReadRulesAllValid() throws Exception {
		String string = "0 & >8 & <9   => 1 \n 0 & =0 & <9   => 2";
		List<Rule> rules = rulesReader.loadRules(new StringReader(string));
		assertEquals(rules.size(), 2);
	}

	@Test
	public void testReadRulesInvalid() throws Exception {
		String string = "0 & >8 & <   => 1 \n 0 & =0 & <9   => 2";
		List<Rule> rules = rulesReader.loadRules(new StringReader(string));
		assertEquals(rules.size(), 1);
	}

}
