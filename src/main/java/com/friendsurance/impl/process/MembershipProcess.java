/**
 * 
 */
package com.friendsurance.impl.process;

import java.io.Reader;
import java.util.SortedSet;

import com.friendsurance.impl.model.Member;
import com.friendsurance.impl.model.Member.NullMember;
import com.friendsurance.impl.model.PendingMessage;
import com.friendsurance.impl.model.PendingMessage.PoisonPillPendingMessage;
import com.friendsurance.impl.rules.RegexRuleParser;
import com.friendsurance.impl.rules.RuleEngine;
import com.friendsurance.impl.rules.model.Rule;
import com.friendsurance.processing.ItemProcessing;
import com.friendsurance.processing.ItemReader;
import com.friendsurance.processing.ItemWriter;

/**
 * item process implementer
 * 
 * @author durrah
 *
 */
public class MembershipProcess extends ItemProcessing<Member, PendingMessage> {
	private RuleEngine ruleEngine;
	private Reader rulesReader;

	protected MembershipProcess(ItemReader<Member> reader, ItemWriter<PendingMessage> writer, Reader rulesReader) {
		super(reader, writer);
		this.rulesReader = rulesReader;
	}

	public void setRulesReader(Reader rulesReader) {
		this.rulesReader = rulesReader;
		ruleEngine = new RuleEngine.Builder().setRulesContentReader(rulesReader).setRuleParser(new RegexRuleParser())
				.build();
		ruleEngine.start();
	}

	@Override
	protected PendingMessage process(Member item) {
		/**
		 * 
		 */
		if (item instanceof NullMember)
			return new PoisonPillPendingMessage();
		SortedSet<Rule> appl = (SortedSet<Rule>) ruleEngine.applicableRules(item);
		if (appl.isEmpty()) {
			return null;
		}
		return new PendingMessage(item, appl.first().getApplyResult());
	}

	protected void stop() {
		ruleEngine.stop();
	}

}
