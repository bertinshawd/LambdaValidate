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
package org.shl.validation.functional;

/**
 * Interface representing a constraint function of an object.
 */
public interface ConstraintFunction {
   /**
    * Override this to provide the validation logic on the containing object.
    * 
    * @return true if the containing object is valid
    * @throws Exception Any thrown exceptions are wrapped in {@see ValidationException}
    *           by {@see ConstraintFunctionValidator}
    */
   public boolean validate() throws Exception;
}
