/*
 * Copyright © Daniel Bertinshaw, 2017
 * 
 * See the LICENSE file distributed with this work for additional
 * information regarding copyright ownership.  The ASF licenses 
 * this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.shl.validation.lambda.core.testgroups;

import org.shl.validation.lambda.core.testgroups.NonStrict;
import org.shl.validation.lambda.core.testgroups.Strict;

public final class GroupConstants {
  
  private GroupConstants() {}
  
  private final static Class<?>[] strict = new Class<?>[] { Strict.class }; 
  private final static Class<?>[] nonstrict = new Class<?>[] { NonStrict.class };
  
  public static Class<?>[] strict() {
    return strict;
  }
  
  public static Class<?>[] nonstrict() {
    return nonstrict;
  }
}
