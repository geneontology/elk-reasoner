/*
 * #%L
 * ELK OWL Object Interfaces
 * 
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2011 Department of Computer Science, University of Oxford
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
package org.semanticweb.elk.owl.interfaces;

import java.util.List;

/**
 * Corresponds to an <a href=
 * "http://www.w3.org/TR/owl2-syntax/#Datatype_Restrictions">Datatype
 * Restrictions<a> in the OWL 2 specification.
 * 
 * @author Markus Kroetzsch
 */
public interface ElkDatatypeRestriction extends ElkDataRange {

	/**
	 * Get the main datatype of this datatype restriction.
	 * 
	 * @return The datatype of this datatype restriction.
	 */
	public ElkDatatype getDatatype();
	
	/**
	 * Get the facet restrictions of this datatype restriction.
	 * 
	 * @return The facet restrictions of this datatype restriction.
	 */
	public List<? extends ElkFacetRestriction> getFacetRestrictions();

}
