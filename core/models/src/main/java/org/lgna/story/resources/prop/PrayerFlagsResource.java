/*
 * Alice 3 End User License Agreement
 *
 * Copyright (c) 2006-2015, Carnegie Mellon University. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Products derived from the software may not be called "Alice", nor may "Alice" appear in their name, without prior written permission of Carnegie Mellon University.
 *
 * 4. All advertising materials mentioning features or use of this software must display the following acknowledgement: "This product includes software developed by Carnegie Mellon University"
 *
 * 5. The gallery of art assets and animations provided with this software is contributed by Electronic Arts Inc. and may be used for personal, non-commercial, and academic use only. Redistributions of any program source code that utilizes The Sims 2 Assets must also retain the copyright notice, list of conditions and the disclaimer contained in The Alice 3.0 Art Gallery License.
 *
 * DISCLAIMER:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND.  ANY AND ALL EXPRESS, STATUTORY OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,  FITNESS FOR A PARTICULAR PURPOSE, TITLE, AND NON-INFRINGEMENT ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHORS, COPYRIGHT OWNERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, PUNITIVE OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING FROM OR OTHERWISE RELATING TO THE USE OF OR OTHER DEALINGS WITH THE SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package org.lgna.story.resources.prop;

import org.lgna.project.annotations.FieldTemplate;
import org.lgna.project.annotations.Visibility;
import org.lgna.story.SJointedModel;
import org.lgna.story.implementation.BasicJointedModelImp;
import org.lgna.story.implementation.JointedModelImp;
import org.lgna.story.resources.ImplementationAndVisualType;
import org.lgna.story.resources.JointId;
import org.lgna.story.resources.JointedModelResource;
import org.lgna.story.resources.PropResource;

public enum PrayerFlagsResource implements PropResource {
  DEFAULT;

  @FieldTemplate(visibility = Visibility.COMPLETELY_HIDDEN) public static final JointId ROOT = new JointId(null, PrayerFlagsResource.class);
  @FieldTemplate(visibility = Visibility.PRIME_TIME) public static final JointId TOP = new JointId(ROOT, PrayerFlagsResource.class);
  @FieldTemplate(visibility = Visibility.COMPLETELY_HIDDEN) public static final JointId STRING_1 = new JointId(TOP, PrayerFlagsResource.class);
  @FieldTemplate(visibility = Visibility.COMPLETELY_HIDDEN) public static final JointId FLAG_05 = new JointId(STRING_1, PrayerFlagsResource.class);
  @FieldTemplate(visibility = Visibility.COMPLETELY_HIDDEN) public static final JointId FLAG_06 = new JointId(STRING_1, PrayerFlagsResource.class);
  @FieldTemplate(visibility = Visibility.COMPLETELY_HIDDEN) public static final JointId FLAG_07 = new JointId(STRING_1, PrayerFlagsResource.class);
  @FieldTemplate(visibility = Visibility.COMPLETELY_HIDDEN) public static final JointId FLAG_08 = new JointId(STRING_1, PrayerFlagsResource.class);
  @FieldTemplate(visibility = Visibility.COMPLETELY_HIDDEN) public static final JointId FLAG_09 = new JointId(STRING_1, PrayerFlagsResource.class);
  @FieldTemplate(visibility = Visibility.COMPLETELY_HIDDEN) public static final JointId STRING_0 = new JointId(TOP, PrayerFlagsResource.class);
  @FieldTemplate(visibility = Visibility.COMPLETELY_HIDDEN) public static final JointId FLAG_00 = new JointId(STRING_0, PrayerFlagsResource.class);
  @FieldTemplate(visibility = Visibility.COMPLETELY_HIDDEN) public static final JointId FLAG_01 = new JointId(STRING_0, PrayerFlagsResource.class);
  @FieldTemplate(visibility = Visibility.COMPLETELY_HIDDEN) public static final JointId FLAG_02 = new JointId(STRING_0, PrayerFlagsResource.class);
  @FieldTemplate(visibility = Visibility.COMPLETELY_HIDDEN) public static final JointId FLAG_03 = new JointId(STRING_0, PrayerFlagsResource.class);
  @FieldTemplate(visibility = Visibility.COMPLETELY_HIDDEN) public static final JointId FLAG_04 = new JointId(STRING_0, PrayerFlagsResource.class);
  @FieldTemplate(visibility = Visibility.COMPLETELY_HIDDEN) public static final JointId STRING_2 = new JointId(TOP, PrayerFlagsResource.class);
  @FieldTemplate(visibility = Visibility.COMPLETELY_HIDDEN) public static final JointId FLAG_10 = new JointId(STRING_2, PrayerFlagsResource.class);
  @FieldTemplate(visibility = Visibility.COMPLETELY_HIDDEN) public static final JointId FLAG_11 = new JointId(STRING_2, PrayerFlagsResource.class);
  @FieldTemplate(visibility = Visibility.COMPLETELY_HIDDEN) public static final JointId FLAG_12 = new JointId(STRING_2, PrayerFlagsResource.class);
  @FieldTemplate(visibility = Visibility.COMPLETELY_HIDDEN) public static final JointId FLAG_13 = new JointId(STRING_2, PrayerFlagsResource.class);
  @FieldTemplate(visibility = Visibility.COMPLETELY_HIDDEN) public static final JointId FLAG_14 = new JointId(STRING_2, PrayerFlagsResource.class);

  @FieldTemplate(visibility = Visibility.COMPLETELY_HIDDEN) public static final JointId[] JOINT_ID_ROOTS = {ROOT};

  public static final JointId[] FLAG_ARRAY = {FLAG_00, FLAG_01, FLAG_02, FLAG_03, FLAG_04, FLAG_05, FLAG_06, FLAG_07, FLAG_08, FLAG_09, FLAG_10, FLAG_11, FLAG_12, FLAG_13, FLAG_14};

  public static final JointId[] STRING_ARRAY = {STRING_0, STRING_1, STRING_2};

  private final ImplementationAndVisualType resourceType;

  PrayerFlagsResource() {
    this(ImplementationAndVisualType.ALICE);
  }

  PrayerFlagsResource(ImplementationAndVisualType resourceType) {
    this.resourceType = resourceType;
  }

  @Override
  public JointId[] getRootJointIds() {
    return PrayerFlagsResource.JOINT_ID_ROOTS;
  }

  @Override
  public JointedModelImp.JointImplementationAndVisualDataFactory<JointedModelResource> getImplementationAndVisualFactory() {
    return this.resourceType.getFactory(this);
  }

  @Override
  public BasicJointedModelImp createImplementation(SJointedModel abstraction) {
    return new BasicJointedModelImp(abstraction, this.resourceType.getFactory(this));
  }
}
