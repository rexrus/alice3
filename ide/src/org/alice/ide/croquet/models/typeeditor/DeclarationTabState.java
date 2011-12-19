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

package org.alice.ide.croquet.models.typeeditor;

/**
 * @author Dennis Cosgrove
 */
public class DeclarationTabState extends org.lgna.croquet.TabSelectionState< DeclarationComposite > {
	private static class SingletonHolder {
		private static DeclarationTabState instance = new DeclarationTabState();
	}
	public static DeclarationTabState getInstance() {
		return SingletonHolder.instance;
	}
	
	private final org.lgna.croquet.State.ValueObserver< org.lgna.project.ast.NamedUserType > typeObserver = new org.lgna.croquet.State.ValueObserver< org.lgna.project.ast.NamedUserType >() {
		public void changing( org.lgna.croquet.State< org.lgna.project.ast.NamedUserType > state, org.lgna.project.ast.NamedUserType prevValue, org.lgna.project.ast.NamedUserType nextValue, boolean isAdjusting ) {
		}
		public void changed( org.lgna.croquet.State< org.lgna.project.ast.NamedUserType > state, org.lgna.project.ast.NamedUserType prevValue, org.lgna.project.ast.NamedUserType nextValue, boolean isAdjusting ) {
			DeclarationTabState.this.handleTypeChanged( prevValue, nextValue );
		}
	};
	private final edu.cmu.cs.dennisc.property.event.ListPropertyListener< org.lgna.project.ast.UserMethod > methodsListener = new edu.cmu.cs.dennisc.property.event.ListPropertyListener< org.lgna.project.ast.UserMethod >() {
		public void adding( edu.cmu.cs.dennisc.property.event.AddListPropertyEvent< org.lgna.project.ast.UserMethod > e ) {
		}
		public void added( edu.cmu.cs.dennisc.property.event.AddListPropertyEvent< org.lgna.project.ast.UserMethod > e ) {
			DeclarationTabState.this.refresh();
		}
		public void clearing( edu.cmu.cs.dennisc.property.event.ClearListPropertyEvent< org.lgna.project.ast.UserMethod > e ) {
		}
		public void cleared( edu.cmu.cs.dennisc.property.event.ClearListPropertyEvent< org.lgna.project.ast.UserMethod > e ) {
			DeclarationTabState.this.refresh();
		}
		public void removing( edu.cmu.cs.dennisc.property.event.RemoveListPropertyEvent< org.lgna.project.ast.UserMethod > e ) {
		}
		public void removed( edu.cmu.cs.dennisc.property.event.RemoveListPropertyEvent< org.lgna.project.ast.UserMethod > e ) {
			DeclarationTabState.this.refresh();
		}
		public void setting( edu.cmu.cs.dennisc.property.event.SetListPropertyEvent< org.lgna.project.ast.UserMethod > e ) {
		}
		public void set( edu.cmu.cs.dennisc.property.event.SetListPropertyEvent< org.lgna.project.ast.UserMethod > e ) {
			DeclarationTabState.this.refresh();
		}
	};
	
	private java.util.Map< org.lgna.project.ast.NamedUserType, DeclarationComposite > map = edu.cmu.cs.dennisc.java.util.Collections.newHashMap();
	private org.lgna.project.ast.NamedUserType type;
	private DeclarationTabState() {
		super( org.alice.ide.IDE.UI_STATE_GROUP, java.util.UUID.fromString( "7b3f95a0-c188-43bf-9089-21ec77c99a69" ), org.alice.ide.croquet.codecs.typeeditor.DeclarationCompositeCodec.SINGLETON );
		TypeState.getInstance().addAndInvokeValueObserver( this.typeObserver );
	}
	private void refresh() {
		this.pushAtomic();
		if( org.alice.ide.croquet.models.ui.preferences.IsEmphasizingClassesState.getInstance().getValue() ) {
			//pass
		} else {
			this.clear();
			if( this.type != null ) {
				this.addItem( DeclarationComposite.getInstance( this.type ) );
				for( org.lgna.project.ast.UserMethod method : this.type.methods ) {
					if( method.isPublicAccess() ) {
						if( method.getManagementLevel() == org.lgna.project.ast.ManagementLevel.NONE ) {
							this.addItem( DeclarationComposite.getInstance( method ) );
						}
					}
				}
				DeclarationComposite selection = this.map.get( this.type );
				int index;
				if( selection != null ) {
					index = this.indexOf( selection );
					index = Math.max( index, 0 );
				} else {
					index = this.getItemCount()-1;
					//index = -1;
				}
				this.setSelectedIndex( index );
			} else {
				this.setSelectedIndex( -1 );
			}
		}
		this.popAtomic();
	}
	private void handleTypeChanged( org.lgna.project.ast.NamedUserType prevType, org.lgna.project.ast.NamedUserType nextType ) {
		if( this.type != nextType ) {
			if( prevType != null ) {
				DeclarationComposite prevDeclarationComposite = this.getSelectedItem();
				if( prevDeclarationComposite != null ) {
					this.map.put( prevType, prevDeclarationComposite );
				} else {
					this.map.remove( prevType );
				}
			}

			if( this.type != null ) {
				this.type.methods.removeListPropertyListener( this.methodsListener );
			}
			this.type = nextType;
			this.refresh();
			if( this.type != null ) {
				this.type.methods.addListPropertyListener( this.methodsListener );
			}
		}
	}
}
