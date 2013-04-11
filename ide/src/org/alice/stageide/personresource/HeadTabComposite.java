/*
 * Copyright (c) 2006-2010, Carnegie Mellon University. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution.
 *
 * 3. Products derived from the software may not be called "Alice", nor may 
 *    "Alice" appear in their name, without prior written permission of 
 *    Carnegie Mellon University.
 *
 * 4. All advertising materials mentioning features or use of this software must
 *    display the following acknowledgement: "This product includes software 
 *    developed by Carnegie Mellon University"
 *
 * 5. The gallery of art assets and animations provided with this software is 
 *    contributed by Electronic Arts Inc. and may be used for personal, 
 *    non-commercial, and academic use only. Redistributions of any program 
 *    source code that utilizes The Sims 2 Assets must also retain the copyright
 *    notice, list of conditions and the disclaimer contained in 
 *    The Alice 3.0 Art Gallery License.
 * 
 * DISCLAIMER:
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND.  
 * ANY AND ALL EXPRESS, STATUTORY OR IMPLIED WARRANTIES, INCLUDING, BUT NOT 
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,  FITNESS FOR A 
 * PARTICULAR PURPOSE, TITLE, AND NON-INFRINGEMENT ARE DISCLAIMED. IN NO EVENT 
 * SHALL THE AUTHORS, COPYRIGHT OWNERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, PUNITIVE OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND 
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING FROM OR OTHERWISE RELATING TO 
 * THE USE OF OR OTHER DEALINGS WITH THE SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.alice.stageide.personresource;

/**
 * @author Dennis Cosgrove
 */
public class HeadTabComposite extends org.lgna.croquet.SimpleTabComposite<org.alice.stageide.personresource.views.HeadTabView> {
	private final org.lgna.croquet.ListSelectionState<org.lgna.story.resources.sims2.BaseEyeColor> baseEyeColorState = this.createListSelectionStateForEnum( this.createKey( "baseEyeColorState" ), org.lgna.story.resources.sims2.BaseEyeColor.class, org.lgna.story.resources.sims2.BaseEyeColor.getRandom() );

	private final org.alice.stageide.personresource.data.HairListData hairData = new org.alice.stageide.personresource.data.HairListData();
	private final org.lgna.croquet.ListSelectionState<org.lgna.story.resources.sims2.Hair> hairState = this.createListSelectionState( this.createKey( "hairState" ), this.hairData, -1 );
	private final org.alice.stageide.personresource.data.HairColorNameListData hairColorNameData = new org.alice.stageide.personresource.data.HairColorNameListData();
	private final org.lgna.croquet.ListSelectionState<String> hairColorNameState = this.createListSelectionState( this.createKey( "hairColorNameState" ), this.hairColorNameData, -1 );

	private final org.lgna.croquet.ListSelectionState<org.lgna.story.resources.sims2.BaseFace> baseFaceState = this.createListSelectionStateForEnum( this.createKey( "baseFaceState" ), org.lgna.story.resources.sims2.BaseFace.class, org.lgna.story.resources.sims2.BaseFace.getRandom() );

	public HeadTabComposite() {
		super( java.util.UUID.fromString( "1e1d604d-974f-4666-91e0-ccf5adec0e4d" ), IsCloseable.FALSE );
	}

	@Override
	protected org.lgna.croquet.components.ScrollPane createScrollPaneIfDesired() {
		return null;
	}

	@Override
	protected org.alice.stageide.personresource.views.HeadTabView createView() {
		return new org.alice.stageide.personresource.views.HeadTabView( this );
	}

	public org.alice.stageide.personresource.data.HairColorNameListData getHairColorNameData() {
		return this.hairColorNameData;
	}

	public org.lgna.croquet.ListSelectionState<String> getHairColorNameState() {
		return this.hairColorNameState;
	}

	public org.alice.stageide.personresource.data.HairListData getHairData() {
		return this.hairData;
	}

	public org.lgna.croquet.ListSelectionState<org.lgna.story.resources.sims2.Hair> getHairState() {
		return this.hairState;
	}

	public org.lgna.croquet.ListSelectionState<org.lgna.story.resources.sims2.BaseEyeColor> getBaseEyeColorState() {
		return this.baseEyeColorState;
	}

	public org.lgna.croquet.ListSelectionState<org.lgna.story.resources.sims2.BaseFace> getBaseFaceState() {
		return this.baseFaceState;
	}
};
