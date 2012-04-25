/*
 * #%L
 * elk-reasoner
 * 
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2011 Oxford University Computing Laboratory
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
package org.semanticweb.elk.owl.visitors;

import org.semanticweb.elk.owl.interfaces.ElkDeclarationAxiom;

/**
 * Super interface of all visitors for ElkAxioms.
 * 
 * @author Markus Kroetzsch
 * @param <O>
 */
public interface ElkAxiomVisitor<O> extends
		ElkClassAxiomVisitor<O>,
		ElkDatatypeDefinitionVisitor<O>,
		ElkHasKeyAxiomVisitor<O>,
		ElkObjectPropertyAxiomVisitor<O>,
		ElkDataPropertyAxiomVisitor<O>,
		ElkAssertionAxiomVisitor<O>,
		ElkAnnotationAxiomVisitor<O> {

	O visit(ElkDeclarationAxiom elkDeclarationAxiom);

}
