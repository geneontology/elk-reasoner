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
package org.semanticweb.elk.reasoner.incremental;

import java.util.Collections;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.semanticweb.elk.RandomSeedProvider;
import org.semanticweb.elk.reasoner.BaseReasoningCorrectnessTest;
import org.semanticweb.elk.testing.PolySuite;
import org.semanticweb.elk.testing.TestInput;
import org.semanticweb.elk.testing.TestManifest;
import org.semanticweb.elk.testing.TestOutput;
import org.semanticweb.elk.util.logging.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 * @author Peter Skocovsky
 */
@RunWith(PolySuite.class)
public abstract class BaseIncrementalReasoningCorrectnessTest<I extends TestInput, A, EO extends TestOutput, AO extends TestOutput, TD extends IncrementalReasoningTestDelegate<A, EO, AO>>
		extends BaseReasoningCorrectnessTest<I, AO, TestManifest<I>, TD> {

	// logger for this class
	protected static final Logger LOGGER_ = LoggerFactory
			.getLogger(BaseIncrementalReasoningCorrectnessTest.class);

	private final static int REPEAT_NUMBER_ = 5;
	private final static double DELETE_RATIO_ = 0.2;

	private OnOffVector<A> changingAxioms_ = null;

	public BaseIncrementalReasoningCorrectnessTest(
			final TestManifest<I> testManifest, final TD testDelegate) {
		super(testManifest, testDelegate);
	}

	public OnOffVector<A> getChangingAxioms() {
		return changingAxioms_;
	}

	/**
	 * The main test method
	 * 
	 * @throws Exception
	 */
	@Test
	public void incrementalReasoning() throws Exception {
		changingAxioms_ = new OnOffVector<A>(15);
		changingAxioms_.addAll(getDelegate().initIncremental());
		changingAxioms_.setAllOn();

		final long seed = RandomSeedProvider.VALUE;
		final Random rnd = new Random(seed);

		// initial correctness check
		correctnessCheck(getDelegate().getActualOutput(),
				getDelegate().getExpectedOutput(), seed);

		for (int i = 0; i < REPEAT_NUMBER_; i++) {
			changingAxioms_.setAllOff();
			// delete some axioms

			randomFlip(changingAxioms_, rnd, DELETE_RATIO_);

			if (LOGGER_.isDebugEnabled()) {
				for (A del : changingAxioms_.getOnElements()) {
					getDelegate().dumpChangeToLog(del, LOGGER_, LogLevel.DEBUG);
				}
			}

			// incremental changes
			getDelegate().applyChanges(changingAxioms_.getOnElements(),
					IncrementalChangeType.DELETE);

			LOGGER_.info("===DELETIONS===");

			correctnessCheck(getDelegate().getActualOutput(),
					getDelegate().getExpectedOutput(), seed);

			// add the axioms back
			getDelegate().applyChanges(changingAxioms_.getOnElements(),
					IncrementalChangeType.ADD);

			LOGGER_.info("===ADDITIONS===");

			correctnessCheck(getDelegate().getActualOutput(),
					getDelegate().getExpectedOutput(), seed);
		}
	}

	protected void randomFlip(OnOffVector<A> axioms, Random rnd,
			double fraction) {
		Collections.shuffle(axioms, rnd);

		int flipped = 0;

		for (int i = 0; i < axioms.size()
				&& flipped <= fraction * axioms.size(); i++) {
			axioms.flipOnOff(i);
			flipped++;
		}
	}

	protected abstract void correctnessCheck(AO actualOutput, EO expectedOutput,
			long seed) throws Exception;

}
