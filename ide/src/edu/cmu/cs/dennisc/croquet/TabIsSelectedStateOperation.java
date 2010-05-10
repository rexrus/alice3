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
package edu.cmu.cs.dennisc.croquet;

/**
 * @author Dennis Cosgrove
 */
public abstract class TabIsSelectedStateOperation extends BooleanStateOperation {
	public TabIsSelectedStateOperation( java.util.UUID groupUUID, java.util.UUID individualUUID, boolean initialState ) {
		super( groupUUID, individualUUID, initialState );
	}
	public TabIsSelectedStateOperation( java.util.UUID groupUUID, java.util.UUID individualUUID, boolean initialState, String title ) {
		super( groupUUID, individualUUID, initialState, title );
	}
	
	//todo: add scrollpane
	
	private edu.cmu.cs.dennisc.croquet.ScrollPane singletonScrollPane;

	protected ScrollPane createSingletonScrollPane() {
		ScrollPane rv = new edu.cmu.cs.dennisc.croquet.ScrollPane( this.getSingletonView() );
		rv.setOpaque( false );
		rv.setBackgroundColor( this.getSingletonView().getBackgroundColor() );
		rv.setBorder( javax.swing.BorderFactory.createEmptyBorder() );
		rv.getJComponent().getVerticalScrollBar().setUnitIncrement( 12 );
		return rv;
	}
	public final ScrollPane getSingletonScrollPane() {
		if( this.singletonScrollPane != null ) {
			//pass
		} else {
			this.singletonScrollPane = this.createSingletonScrollPane();
		}
		return this.singletonScrollPane;
	}
	
	private Component<?> singletonView;
	public final Component<?> getSingletonView() {
		if( this.singletonView != null ) {
			//pass
		} else {
			this.singletonView = this.createSingletonView();
		}
		return this.singletonView;
	}
	protected abstract Component<?> createSingletonView();
	protected abstract boolean isCloseAffordanceDesired();

	/*package-private*/ TabTitle createTabTitle() {
		Application.getSingleton().register( this );
		return new TabTitle( this.isCloseAffordanceDesired() ) {
			@Override
			protected void handleAddedTo( Component<?> parent ) {
				super.handleAddedTo( parent );
				TabIsSelectedStateOperation.this.addAbstractButton(this);
			}

			@Override
			protected void handleRemovedFrom(edu.cmu.cs.dennisc.croquet.Component<?> parent) {
				TabIsSelectedStateOperation.this.removeAbstractButton(this);
				super.handleRemovedFrom( parent );
			}
		};
	}
	
}
