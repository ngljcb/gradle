/*
 * Copyright 2022 the original author or authors.
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

package org.gradle.integtests.tooling.r84

import org.gradle.integtests.tooling.fixture.TargetGradleVersion
import org.gradle.integtests.tooling.fixture.TestFailureSpecification
import org.gradle.integtests.tooling.fixture.ToolingApiVersion
import org.gradle.tooling.BuildException
import org.gradle.tooling.TestAssertionFailure

@ToolingApiVersion(">=8.4")
@TargetGradleVersion(">=8.4")
class TestFailureProgressEventCrossVersionTest extends TestFailureSpecification {

    def "Wrapped assertion errors are emitted as test failure events"() {
        given:
        enableTestJvmDebugging = true
        file('src/test/java/org/gradle/JUnitJupiterTest.java') << '''
            package org.gradle;

            import org.junit.jupiter.api.Test;

            public class JUnitJupiterTest {

                @Test
                void testingFileComparisonFailure() {
                    throw new RuntimeException(
                        "This exception wraps an assertion error",
                        new AssertionError("This is a wrapped assertion error")
                    );
                }
            }
        '''

        when:
        runTestTaskWithFailureCollection()

        then:
        thrown(BuildException)
        failures.size() == 1
        failures[0] instanceof TestAssertionFailure
        TestAssertionFailure f = failures[0]
        f.message == "This is a wrapped assertion error"
    }

}
