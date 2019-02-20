/*******************************************************************************
 * Copyright (c) 2006, 2015, Carnegie Mellon University. All rights reserved.
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
 *******************************************************************************/

package org.alice.ide.members;

import org.alice.ide.croquet.models.ui.preferences.IsAlwaysShowingBlocksState;
import org.alice.ide.member.ControlFlowTabComposite;
import org.alice.ide.member.FunctionTabComposite;
import org.alice.ide.member.MemberOrControlFlowTabComposite;
import org.alice.ide.member.ProcedureTabComposite;
import org.alice.ide.members.components.MembersView;
import org.lgna.croquet.ImmutableDataTabState;
import org.lgna.croquet.SimpleComposite;

import java.util.UUID;

/**
 * @author Dennis Cosgrove
 */
public class MembersComposite extends SimpleComposite<MembersView> {
	private static class SingletonHolder {
		private static MembersComposite instance = new MembersComposite();
	}

	public static MembersComposite getInstance() {
		return SingletonHolder.instance;
	}

	private final ProcedureTabComposite procedureTabComposite = new ProcedureTabComposite();
	private final FunctionTabComposite functionTabComposite = new FunctionTabComposite();
	private final ControlFlowTabComposite controlStructureTabComposite;
	private final ImmutableDataTabState<MemberOrControlFlowTabComposite<?>> tabState;

	private MembersComposite() {
		super( UUID.fromString( "10225a3f-f05d-42f3-baaf-f6bd0f8a7c68" ) );
		MemberOrControlFlowTabComposite<?>[] tabComposites;
		if( IsAlwaysShowingBlocksState.getInstance().getValue() ) {
			this.controlStructureTabComposite = null;
			tabComposites = new MemberOrControlFlowTabComposite[] { this.procedureTabComposite, this.functionTabComposite };
		} else {
			this.controlStructureTabComposite = new ControlFlowTabComposite();
			tabComposites = new MemberOrControlFlowTabComposite[] { this.procedureTabComposite, this.functionTabComposite, this.controlStructureTabComposite };
		}
		this.tabState = (ImmutableDataTabState)this.createImmutableTabState( "tabState", 0, MemberOrControlFlowTabComposite.class, tabComposites );
	}

	public ImmutableDataTabState<MemberOrControlFlowTabComposite<?>> getTabState() {
		return this.tabState;
	}

	public ProcedureTabComposite getProcedureTabComposite() {
		return this.procedureTabComposite;
	}

	public FunctionTabComposite getFunctionTabComposite() {
		return this.functionTabComposite;
	}

	public ControlFlowTabComposite getControlStructureTabComposite() {
		return this.controlStructureTabComposite;
	}

	@Override
	protected MembersView createView() {
		return new MembersView( this );
	}
}
