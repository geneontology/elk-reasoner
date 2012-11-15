/**
 * 
 */
package org.semanticweb.elk.reasoner.saturation.conclusions;

/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2012 Department of Computer Science, University of Oxford
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.Collection;

import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedPropertyChain;
import org.semanticweb.elk.reasoner.saturation.SaturationState;
import org.semanticweb.elk.reasoner.saturation.context.Context;
import org.semanticweb.elk.reasoner.saturation.rules.BackwardLinkRules;
import org.semanticweb.elk.util.collections.Multimap;
import org.semanticweb.elk.util.collections.chains.Matcher;
import org.semanticweb.elk.util.collections.chains.ReferenceFactory;
import org.semanticweb.elk.util.collections.chains.SimpleTypeBasedMatcher;

/**
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 */
public class Bottom implements Conclusion {

	// private static final Logger LOGGER_ = Logger.getLogger(Bottom.class);

	@Override
	public void deapply(SaturationState.Engine engine, Context context) {
		context.setConsistent(true);
		propagateThroughBackwardLinks(engine, context);
		context.getBackwardLinkRulesChain().remove(
				BottomBackwardLinkRule.MATCHER_);
	}

	@Override
	public void apply(SaturationState.Engine engine, Context context) {
		context.setConsistent(false);
		propagateThroughBackwardLinks(engine, context);
		// register the backward link rule for propagation of bottom
		context.getBackwardLinkRulesChain().getCreate(
				BottomBackwardLinkRule.MATCHER_,
				BottomBackwardLinkRule.FACTORY_);
	}

	private void propagateThroughBackwardLinks(SaturationState.Engine engine,
			Context context) {

		final Multimap<IndexedPropertyChain, Context> backLinks = context
				.getBackwardLinksByObjectProperty();

		for (IndexedPropertyChain propRelation : backLinks.keySet()) {

			Collection<Context> targets = backLinks.get(propRelation);

			for (Context target : targets) {
				// the reason we propagate a positive SCE, not the Bot directly,
				// is because we want the SCE to appear in the list of
				// superclasses
				engine.produce(
						target,
						new PositiveSuperClassExpression(engine.getOwlNothing()));
			}
		}
	}

	@Override
	public <R> R accept(ConclusionVisitor<R> visitor, Context context) {
		return visitor.visit(this, context);
	}

	@Override
	public String toString() {
		return "owl:Nothing";
	}

	/**
	 * A backward link rule to propagate bottom through any new backward links
	 */
	private static class BottomBackwardLinkRule extends BackwardLinkRules {

		BottomBackwardLinkRule(BackwardLinkRules tail) {
			super(tail);
		}

		@Override
		public void apply(SaturationState.Engine engine, BackwardLink link) {
			/*
			 * RuleStatistics stats = ruleEngine.getRulesTimer();
			 * 
			 * stats.timeClassBottomBackwardLinkRule -=
			 * CachedTimeThread.currentTimeMillis;
			 * stats.countClassBottomBackwardLinkRule++;
			 */

			try {
				engine.produce(
						link.getSource(),
						new PositiveSuperClassExpression(engine.getOwlNothing()));
			} finally {
				// stats.timeClassBottomBackwardLinkRule +=
				// CachedTimeThread.currentTimeMillis;
			}
		}

		private static Matcher<BackwardLinkRules, BottomBackwardLinkRule> MATCHER_ = new SimpleTypeBasedMatcher<BackwardLinkRules, BottomBackwardLinkRule>(
				BottomBackwardLinkRule.class);

		private static ReferenceFactory<BackwardLinkRules, BottomBackwardLinkRule> FACTORY_ = new ReferenceFactory<BackwardLinkRules, BottomBackwardLinkRule>() {
			@Override
			public BottomBackwardLinkRule create(BackwardLinkRules tail) {
				return new BottomBackwardLinkRule(tail);
			}
		};
	}
}