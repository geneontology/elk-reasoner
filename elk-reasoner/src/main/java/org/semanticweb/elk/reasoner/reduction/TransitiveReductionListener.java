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
package org.semanticweb.elk.reasoner.reduction;

import org.semanticweb.elk.util.concurrent.computation.InputProcessorListenerNotifyCanProcess;
import org.semanticweb.elk.util.concurrent.computation.InputProcessorListenerNotifyFinishedJob;

/**
 * A listener to be used with {@link TransitiveReductionEngine}. The listener
 * defines functions that are triggered during transitive reduction.
 * 
 * @author "Yevgeny Kazakov"
 * 
 * @param <J>
 *            the type of the jobs of {@link TransitiveReductionEngine}
 */
public interface TransitiveReductionListener<J extends TransitiveReductionJob<?>, P extends TransitiveReductionEngine<?, J>>
		extends InputProcessorListenerNotifyCanProcess<P>,
		InputProcessorListenerNotifyFinishedJob<J, P> {
}