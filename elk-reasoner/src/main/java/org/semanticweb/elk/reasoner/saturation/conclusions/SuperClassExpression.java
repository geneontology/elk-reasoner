/*
 * #%L
 * ELK Reasoner
 * 
 * $Id$
 * $HeadURL$
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
package org.semanticweb.elk.reasoner.saturation.conclusions;

import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedClassExpression;
import org.semanticweb.elk.reasoner.saturation.context.Context;
import org.semanticweb.elk.reasoner.saturation.rules.RuleEngine;

/**
 * @author Frantisek Simancik
 * @author "Yevgeny Kazakov"
 * 
 */
public abstract class SuperClassExpression implements Conclusion {

	protected final IndexedClassExpression expression;

	public SuperClassExpression(IndexedClassExpression expression) {
		this.expression = expression;
	}

	public IndexedClassExpression getExpression() {
		return expression;
	}

	public boolean storeInContext(Context context, RuleEngine ruleEngine) {
		RuleStatistics statistics = ruleEngine.getRuleStatistics();
		statistics.superClassExpressionInfNo++;
		if (context.addSuperClassExpression(expression)) {
			statistics.superClassExpressionNo++;
			return true;
		}
		return false;
	}

	public String toString() {
		return expression.toString();
	}
}
