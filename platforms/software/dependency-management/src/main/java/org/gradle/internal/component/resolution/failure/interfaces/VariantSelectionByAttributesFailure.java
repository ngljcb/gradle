/*
 * Copyright 2024 the original author or authors.
 *
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
 */

package org.gradle.internal.component.resolution.failure.interfaces;

import org.gradle.api.internal.attributes.ImmutableAttributes;
import org.gradle.internal.component.external.model.ImmutableCapabilities;

/**
 * Represents a specific type of {@link VariantSelectionFailure} where the failure occurred
 * due to a failure to select a variant of a component based on the requested attributes.
 */
public interface VariantSelectionByAttributesFailure extends VariantSelectionFailure {
    /**
     * Gets the attributes that were used to attempt to select a variant.
     * <p>
     * This includes attributes from the resolvable configuration and any additional attributes added
     * to the specific dependency used to select the {@link #getTargetComponent()} represented by this edge in the graph.
     *
     * @return the requested attributes that were used to attempt to select a variant
     */
    ImmutableAttributes getRequestedAttributes();

    /**
     * Gets the capabilities present on the {@link #getTargetComponent()} represented by this edge in the graph
     *
     * @return the capabilities associated with the component failing to select a variant
     */
    ImmutableCapabilities getRequestedCapabilities();
}
