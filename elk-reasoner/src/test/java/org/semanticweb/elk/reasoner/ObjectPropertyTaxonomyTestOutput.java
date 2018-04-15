package org.semanticweb.elk.reasoner;

import java.util.Objects;

/*-
 * #%L
 * ELK Reasoner Core
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2018 Department of Computer Science, University of Oxford
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

import org.semanticweb.elk.exceptions.ElkException;
import org.semanticweb.elk.owl.interfaces.ElkObjectProperty;
import org.semanticweb.elk.reasoner.taxonomy.hashing.TaxonomyHasher;
import org.semanticweb.elk.reasoner.taxonomy.model.Taxonomy;

public class ObjectPropertyTaxonomyTestOutput
		extends TaxonomyTestOutput<Taxonomy<ElkObjectProperty>> {

	public ObjectPropertyTaxonomyTestOutput(
			Taxonomy<ElkObjectProperty> taxonomy, boolean isComplete) {
		super(taxonomy, isComplete);
	}

	public ObjectPropertyTaxonomyTestOutput(Reasoner reasoner)
			throws ElkException {
		this(reasoner.getObjectPropertyTaxonomyQuietly(),
				reasoner.isObjectPropertyTaxonomyComplete());
	}

	@Override
	public final int hashCode() {
		return Objects.hash(ObjectPropertyTaxonomyTestOutput.class,
				// TODO: make direct comparison of mock taxonomy with taxonomy
				TaxonomyHasher.hash(getTaxonomy()), isComplete());
	}

	@Override
	public final boolean equals(final Object obj) {
		if (obj instanceof ObjectPropertyTaxonomyTestOutput) {
			ObjectPropertyTaxonomyTestOutput other = (ObjectPropertyTaxonomyTestOutput) obj;
			return this == obj || (getTaxonomy().equals(other.getTaxonomy())
					&& isComplete() == other.isComplete());
		}
		// else
		return false;
	}

}