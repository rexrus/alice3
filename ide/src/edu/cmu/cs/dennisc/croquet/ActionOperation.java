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
public abstract class ActionOperation extends Operation {
	private javax.swing.ButtonModel buttonModel = new javax.swing.DefaultButtonModel();
	private javax.swing.Action action = new javax.swing.AbstractAction() {
		public void actionPerformed( java.awt.event.ActionEvent e ) {
		}
	};

	public ActionOperation( java.util.UUID groupUUID ) {
		super( groupUUID );
		this.buttonModel.addActionListener( new java.awt.event.ActionListener() {
			public void actionPerformed( java.awt.event.ActionEvent e ) {
				ActionOperation.this.handleActionPerformed( e );
			}
		} );
	}
	private void handleActionPerformed( java.awt.event.ActionEvent e ) {
		Application.performIfAppropriate( this, e, Application.CANCEL_IS_WORTHWHILE );
	}

	public abstract void perform( ActionContext actionContext );
	
	
	public String getName() {
		return String.class.cast( this.action.getValue( javax.swing.Action.NAME ) );
	}
	public void setName( String name ) {
		this.action.putValue( javax.swing.Action.NAME, name );
	}
	public String getShortDescription() {
		return String.class.cast( this.action.getValue( javax.swing.Action.SHORT_DESCRIPTION ) );
	}
	public void setShortDescription( String shortDescription ) {
		this.action.putValue( javax.swing.Action.SHORT_DESCRIPTION, shortDescription );
	}
	public String getLongDescription() {
		return String.class.cast( this.action.getValue( javax.swing.Action.LONG_DESCRIPTION ) );
	}
	public void setLongDescription( String longDescription ) {
		this.action.putValue( javax.swing.Action.LONG_DESCRIPTION, longDescription );
	}
	public javax.swing.Icon getSmallIcon() {
		return javax.swing.Icon.class.cast( this.action.getValue( javax.swing.Action.SMALL_ICON ) );
	}
	public void setSmallIcon( javax.swing.Icon icon ) {
		this.action.putValue( javax.swing.Action.SMALL_ICON, icon );
	}
	public int getMnemonicKey() {
		return Integer.class.cast( this.action.getValue( javax.swing.Action.MNEMONIC_KEY ) );
	}
	public void setMnemonicKey( int mnemonicKey ) {
		this.action.putValue( javax.swing.Action.MNEMONIC_KEY, mnemonicKey );
	}
	public javax.swing.KeyStroke getAcceleratorKey() {
		return javax.swing.KeyStroke.class.cast( this.action.getValue( javax.swing.Action.ACCELERATOR_KEY ) );
	}
	public void setAcceleratorKey( javax.swing.KeyStroke acceleratorKey ) {
		this.action.putValue( javax.swing.Action.ACCELERATOR_KEY, acceleratorKey );
	}

	private void addAbstractButton( javax.swing.AbstractButton abstractButton ) {
		abstractButton.setAction( this.action );
		abstractButton.setModel( this.buttonModel );
		this.addComponent( abstractButton );
	}
	private void removeAbstractButton( javax.swing.AbstractButton abstractButton ) {
		this.removeComponent( abstractButton );
		//abstractButton.setModel( null );
		//abstractButton.setAction( null );
	}

	public javax.swing.JButton createButton() {
		return new javax.swing.JButton() {
			@Override
			public void addNotify() {
				ActionOperation.this.addAbstractButton( this );
				super.addNotify();
			}
			@Override
			public void removeNotify() {
				super.removeNotify();
				ActionOperation.this.removeAbstractButton( this );
			}
		};
	}
	@Override
	public javax.swing.JMenuItem createMenuItem() {
		return new javax.swing.JMenuItem() {
			@Override
			public void addNotify() {
				ActionOperation.this.addAbstractButton( this );
				super.addNotify();
			}
			@Override
			public void removeNotify() {
				super.removeNotify();
				ActionOperation.this.removeAbstractButton( this );
			}
		};
	}
//	public javax.swing.AbstractButton createHyperlink( ActionOperation actionOperation ) {
//		assert actionOperation != null;
//		return new ZHyperlink(actionOperation);
//	}
}
