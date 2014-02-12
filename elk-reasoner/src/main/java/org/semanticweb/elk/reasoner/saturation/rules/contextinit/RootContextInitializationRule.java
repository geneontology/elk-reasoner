package org.semanticweb.elk.reasoner.saturation.rules.contextinit;

/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2014 Department of Computer Science, University of Oxford
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

import org.semanticweb.elk.reasoner.indexing.hierarchy.ModifiableOntologyIndex;
import org.semanticweb.elk.reasoner.saturation.conclusions.ContextInitialization;
import org.semanticweb.elk.reasoner.saturation.conclusions.DecomposedSubsumer;
import org.semanticweb.elk.reasoner.saturation.conclusions.Subsumer;
import org.semanticweb.elk.reasoner.saturation.context.Context;
import org.semanticweb.elk.reasoner.saturation.context.ContextPremises;
import org.semanticweb.elk.reasoner.saturation.rules.ConclusionProducer;
import org.semanticweb.elk.util.collections.chains.Chain;
import org.semanticweb.elk.util.collections.chains.Matcher;
import org.semanticweb.elk.util.collections.chains.ReferenceFactory;
import org.semanticweb.elk.util.collections.chains.SimpleTypeBasedMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A {@link ChainableContextInitRule} that produces a {@link Subsumer} for the
 * root of the given {@link Context}
 * 
 * @see Context#getRoot()
 * 
 * @author "Yevgeny Kazakov"
 */
public class RootContextInitializationRule extends
		AbstractChainableContextInitRule {

	// logger for events
	private static final Logger LOGGER_ = LoggerFactory
			.getLogger(RootContextInitializationRule.class);

	private static final String NAME_ = "Root Introduction";

	private RootContextInitializationRule(ChainableContextInitRule tail) {
		super(tail);
	}

	private RootContextInitializationRule() {
		super(null);
	}

	/**
	 * Add a {@link RootContextInitializationRule} to the given
	 * {@link ModifiableOntologyIndex}
	 * 
	 * @param owlThing
	 * @param index
	 */
	public static void addRuleFor(ModifiableOntologyIndex index) {
		index.addContextInitRule(new RootContextInitializationRule());
	}

	/**
	 * Removes a {@link RootContextInitializationRule} to the given
	 * {@link ModifiableOntologyIndex}
	 * 
	 * @param owlThing
	 * @param index
	 */
	public static void removeRuleFor(ModifiableOntologyIndex index) {
		index.removeContextInitRule(new RootContextInitializationRule());
	}

	@Override
	public String getName() {
		return NAME_;
	}

	@Override
	public void apply(ContextInitialization premise, ContextPremises premises,
			ConclusionProducer producer) {
		producer.produce(premises.getRoot(),
				new DecomposedSubsumer(premises.getRoot()));
	}

	@Override
	public boolean addTo(Chain<ChainableContextInitRule> ruleChain) {
		RootContextInitializationRule rule = ruleChain.find(MATCHER_);

		if (rule == null) {
			ruleChain.getCreate(MATCHER_, FACTORY_);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeFrom(Chain<ChainableContextInitRule> ruleChain) {
		return ruleChain.remove(MATCHER_) != null;
	}

	@Override
	public void accept(LinkedContextInitRuleVisitor visitor,
			ContextInitialization premise, ContextPremises premises,
			ConclusionProducer producer) {
		visitor.visit(this, premise, premises, producer);
	}

	private static final Matcher<ChainableContextInitRule, RootContextInitializationRule> MATCHER_ = new SimpleTypeBasedMatcher<ChainableContextInitRule, RootContextInitializationRule>(
			RootContextInitializationRule.class);

	private static final ReferenceFactory<ChainableContextInitRule, RootContextInitializationRule> FACTORY_ = new ReferenceFactory<ChainableContextInitRule, RootContextInitializationRule>() {
		@Override
		public RootContextInitializationRule create(
				ChainableContextInitRule tail) {
			return new RootContextInitializationRule(tail);
		}
	};

}