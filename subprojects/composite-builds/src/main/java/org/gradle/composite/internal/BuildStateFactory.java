/*
 * Copyright 2021 the original author or authors.
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

package org.gradle.composite.internal;

import org.gradle.api.artifacts.component.BuildIdentifier;
import org.gradle.api.internal.BuildDefinition;
import org.gradle.initialization.BuildCancellationToken;
import org.gradle.initialization.GradleLauncherFactory;
import org.gradle.internal.build.BuildState;
import org.gradle.internal.service.scopes.GradleUserHomeScopeServiceRegistry;
import org.gradle.internal.service.scopes.Scopes;
import org.gradle.internal.service.scopes.ServiceScope;
import org.gradle.internal.session.CrossBuildSessionState;
import org.gradle.util.Path;

@ServiceScope(Scopes.BuildTree.class)
public class BuildStateFactory {
    private final GradleLauncherFactory gradleLauncherFactory;
    private final GradleUserHomeScopeServiceRegistry userHomeDirServiceRegistry;
    private final CrossBuildSessionState crossBuildSessionState;
    private final BuildCancellationToken buildCancellationToken;

    public BuildStateFactory(GradleLauncherFactory gradleLauncherFactory,
                             GradleUserHomeScopeServiceRegistry userHomeDirServiceRegistry,
                             CrossBuildSessionState crossBuildSessionState,
                             BuildCancellationToken buildCancellationToken) {
        this.gradleLauncherFactory = gradleLauncherFactory;
        this.userHomeDirServiceRegistry = userHomeDirServiceRegistry;
        this.crossBuildSessionState = crossBuildSessionState;
        this.buildCancellationToken = buildCancellationToken;
    }

    public RootOfNestedBuildTree createNestedTree(BuildDefinition buildDefinition,
                                                  BuildIdentifier buildIdentifier,
                                                  Path identityPath,
                                                  BuildState owner) {
        return new RootOfNestedBuildTree(buildDefinition, buildIdentifier, identityPath, owner, gradleLauncherFactory, userHomeDirServiceRegistry, crossBuildSessionState, buildCancellationToken);
    }
}
