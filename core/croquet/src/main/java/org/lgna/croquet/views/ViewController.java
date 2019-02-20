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

package org.lgna.croquet.views;

import edu.cmu.cs.dennisc.java.awt.event.LenientMouseClickAdapter;
import edu.cmu.cs.dennisc.java.awt.event.MouseEventUtilities;
import edu.cmu.cs.dennisc.print.PrintUtilities;
import org.lgna.croquet.Model;
import org.lgna.croquet.PopupPrepModel;
import org.lgna.croquet.triggers.MouseEventTrigger;

import javax.swing.JComponent;
import java.awt.event.MouseEvent;

/**
 * @author Dennis Cosgrove
 */
public abstract class ViewController<J extends JComponent, M extends Model> extends SwingComponentView<J> {
	private final M model;

	public ViewController( M model ) {
		this.model = model;
		if( this.model != null ) {
			this.model.initializeIfNecessary();
		}
	}

	public M getModel() {
		return model;
	}

	private PopupPrepModel popupPrepModel;

	public final PopupPrepModel getPopupPrepModel() {
		return this.popupPrepModel;
	}

	public final void setPopupPrepModel( PopupPrepModel popupMenuPrepModel ) {
		if( this.getAwtComponent().getParent() == null ) {
			//pass
		} else {
			PrintUtilities.println( "warning: setPopupMenuOperation" );
		}
		if( this.popupPrepModel != null ) {
			this.getAwtComponent().removeMouseListener( this.lenientMouseClickAdapter );
			this.getAwtComponent().removeMouseMotionListener( this.lenientMouseClickAdapter );
			ComponentManager.removeComponent( this.popupPrepModel, this );
		}
		this.popupPrepModel = popupMenuPrepModel;
		if( this.popupPrepModel != null ) {
			ComponentManager.addComponent( this.popupPrepModel, this );
			this.getAwtComponent().addMouseListener( this.lenientMouseClickAdapter );
			this.getAwtComponent().addMouseMotionListener( this.lenientMouseClickAdapter );
		}
	}

	private LenientMouseClickAdapter lenientMouseClickAdapter = new LenientMouseClickAdapter() {
		@Override
		protected void mouseQuoteClickedUnquote( MouseEvent e, int quoteClickCountUnquote ) {
			if ( quoteClickCountUnquote == 1 &&
				ViewController.this.popupPrepModel != null
				&& MouseEventUtilities.isQuoteRightUnquoteMouseButton( e ) ) {
				ViewController.this.popupPrepModel
					.fire( MouseEventTrigger.createUserActivity( ViewController.this, e ) );
			}
		}
	};

	@Override
	protected void handleAddedTo( AwtComponentView<?> parent ) {
		super.handleAddedTo( parent );
		M model = this.getModel();
		if( model != null ) {
			ComponentManager.addComponent( model, this );
		}
	}

	@Override
	protected void handleRemovedFrom( AwtComponentView<?> parent ) {
		M model = this.getModel();
		if( model != null ) {
			ComponentManager.removeComponent( model, this );
		}
		super.handleRemovedFrom( parent );
	}
}
