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
package org.alice.ide.common;

/**
 * @author Dennis Cosgrove
 */
public class TypeComponent extends DeclarationNameLabel {
	private edu.cmu.cs.dennisc.croquet.PopupMenuOperation popupOperation;
	private java.awt.event.MouseListener mouseAdapter = new java.awt.event.MouseListener() {
		public void mouseEntered( java.awt.event.MouseEvent e ) {
			setRollover( true );
		}
		public void mouseExited( java.awt.event.MouseEvent e ) {
			setRollover( false );
		}
		public void mousePressed( java.awt.event.MouseEvent e ) {
			if( TypeComponent.this.popupOperation != null ) {
				TypeComponent.this.popupOperation.fire();
			}
		}
		public void mouseReleased( java.awt.event.MouseEvent e ) {
		}
		public void mouseClicked( java.awt.event.MouseEvent e ) {
		}
	};
	private boolean isRollover = false;
	public TypeComponent( edu.cmu.cs.dennisc.alice.ast.AbstractType type ) {
		super( type );
		this.setCursor( java.awt.Cursor.getDefaultCursor() );
		this.setBorder( TypeBorder.getSingletonFor( type ) );
		if( type instanceof edu.cmu.cs.dennisc.alice.ast.TypeDeclaredInAlice ) {
			edu.cmu.cs.dennisc.alice.ast.TypeDeclaredInAlice typeInAlice = (edu.cmu.cs.dennisc.alice.ast.TypeDeclaredInAlice)type;
			
			java.util.List< edu.cmu.cs.dennisc.croquet.Operation > operations = edu.cmu.cs.dennisc.java.util.Collections.newLinkedList();
			operations.add( new org.alice.ide.operations.ast.RenameTypeOperation( typeInAlice ) );
			
			org.alice.ide.IDE ide = org.alice.ide.IDE.getSingleton();
			if( ide.isInstanceCreationAllowableFor( typeInAlice ) ) {
				operations.add( new org.alice.ide.operations.ast.DeclareFieldOfPredeterminedTypeOperation( ide.getSceneType(), typeInAlice ) );
			}
			//operations.add( new org.alice.ide.operations.file.SaveAsTypeOperation( typeInAlice ) );
			this.popupOperation = new edu.cmu.cs.dennisc.croquet.PopupMenuOperation( edu.cmu.cs.dennisc.zoot.ZManager.UNKNOWN_GROUP, java.util.UUID.fromString( "9f84fe0c-ca20-45f1-8a25-c79bd8454dbd" ), operations );
					
//			this.popupOperation = new zoot.DefaultPopupActionOperation( 
//					new org.alice.ide.operations.ast.RenameTypeOperation( typeInAlice ),
//					new org.alice.ide.operations.ast.DeclareFieldOfPredeterminedTypeOperation( org.alice.ide.IDE.getSingleton().getSceneType(), typeInAlice ), 
//					new org.alice.ide.operations.file.SaveAsTypeOperation( typeInAlice ) 
//			);
		}
	}
	@Override
	protected javax.swing.JLabel createJComponent() {
		return new javax.swing.JLabel() {
			@Override
			public void paint( java.awt.Graphics g ) {
				this.paintBorder( g );
				this.paintComponent( g );
			}
		};
	}
	public TypeComponent( edu.cmu.cs.dennisc.alice.ast.AbstractType type, boolean isToolTipDesired ) {
		this( type );
		if( isToolTipDesired ) {
			String typeName;
			if( type != null ) {
				//typeName = type.getName() + " " + type.hashCode();
				typeName = type.getName();
			} else {
				typeName = "<unset>";
			}
			this.setToolTipText( "class: " + typeName );
		}
	}

	@Override
	protected void handleAddedTo(edu.cmu.cs.dennisc.croquet.Component<?> parent) {
		super.handleAddedTo( parent );
		if( this.popupOperation != null ) {
			this.addMouseListener( this.mouseAdapter );
		}
	}
	@Override
	protected void handleRemovedFrom(edu.cmu.cs.dennisc.croquet.Component<?> parent) {
		if( this.popupOperation != null ) {
			this.removeMouseListener( this.mouseAdapter );
		}
		super.handleRemovedFrom( parent );
	}

	public void setRollover( boolean isRollover ) {
		this.isRollover = isRollover;
		this.repaint();
	}
//todo: croquet switch
//	@Override
//	public java.awt.Color getForeground() {
//		if( this.isRollover ) {
//			return java.awt.Color.BLUE.darker();
//		} else {
//			return super.getForeground();
//		}
//	}
}
