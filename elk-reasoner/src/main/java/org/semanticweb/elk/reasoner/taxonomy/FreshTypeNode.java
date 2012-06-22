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
package org.semanticweb.elk.reasoner.taxonomy;

import java.util.Collections;
import java.util.Set;

import org.semanticweb.elk.owl.interfaces.ElkObject;

/**
 * A fresh TypeNode containing an object that does not occur in a taxonomy. Such
 * nodes are returned to queries when FreshEntityPolicy is set to ALLOW.
 * 
 * @author Frantisek Simancik
 * 
 */
public class FreshTypeNode<T extends ElkObject, I extends ElkObject> extends
		FreshNode<T> implements TypeNode<T, I> {

	protected InstanceTaxonomy<T, I> taxonomy;

	public FreshTypeNode(T member, InstanceTaxonomy<T, I> taxonomy) {
		super(member);
		this.taxonomy = taxonomy;
	}

	@Override
	public InstanceTaxonomy<T, I> getTaxonomy() {
		return taxonomy;
	}

	@Override
	public Set<TypeNode<T, I>> getDirectSuperNodes() {
		return Collections.singleton(taxonomy.getTopNode());
	}

	@Override
	public Set<TypeNode<T, I>> getAllSuperNodes() {
		return Collections.singleton(taxonomy.getTopNode());
	}

	@Override
	public Set<TypeNode<T, I>> getDirectSubNodes() {
		return Collections.singleton(taxonomy.getBottomNode());
	}

	@Override
	public Set<TypeNode<T, I>> getAllSubNodes() {
		return Collections.singleton(taxonomy.getBottomNode());
	}

	@Override
	public Set<? extends InstanceNode<T, I>> getDirectInstanceNodes() {
		return Collections.emptySet();
	}

	@Override
	public Set<? extends InstanceNode<T, I>> getAllInstanceNodes() {
		return Collections.emptySet();
	}

}
