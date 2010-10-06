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
public class ComboBox< E > extends ItemSelectable<javax.swing.JComboBox, E> {
	/*package-private*/ ComboBox( ListSelectionState<E> model ) {
		super( model );
	}
	
	@Override
	public boolean isSingleStageSelectable() {
		return false;
	}
	@Override
	protected javax.swing.JComboBox createAwtComponent() {
		javax.swing.JComboBox rv = new javax.swing.JComboBox() {
			@Override
			public java.awt.Dimension getPreferredSize() {
				return constrainPreferredSizeIfNecessary( super.getPreferredSize() );
			}
			@Override
			public java.awt.Dimension getMaximumSize() {
				java.awt.Dimension rv = super.getMaximumSize();
				if( ComboBox.this.isMaximumSizeClampedToPreferredSize() ) {
					rv.setSize( this.getPreferredSize() );
				} else {
					rv.height = this.getPreferredSize().height;
				}
				return rv;
			}
		};
		//edu.cmu.cs.dennisc.print.PrintUtilities.println( "comboBoxUI:", rv.getUI() );
		return rv;
	}

	private javax.swing.event.PopupMenuListener popupMenuListener = new javax.swing.event.PopupMenuListener() {
		public void popupMenuWillBecomeVisible( javax.swing.event.PopupMenuEvent e ) {
			ContextManager.addListSelectionPopupMenuWillBecomeVisible( ComboBox.this.getModel(), e, ComboBox.this );
		}
		public void popupMenuWillBecomeInvisible( javax.swing.event.PopupMenuEvent e ) {
			ContextManager.addListSelectionPopupMenuWillBecomeInvisible( ComboBox.this.getModel(), e, ComboBox.this );
		}
		public void popupMenuCanceled( javax.swing.event.PopupMenuEvent e ) {
			ContextManager.addListSelectionPopupMenuCanceled( ComboBox.this.getModel(), e, ComboBox.this );
		}
	};
	@Override
	protected void handleAddedTo( edu.cmu.cs.dennisc.croquet.Component< ? > parent ) {
		super.handleAddedTo( parent );
		this.getAwtComponent().addPopupMenuListener( this.popupMenuListener );
	}
	@Override
	protected void handleRemovedFrom( edu.cmu.cs.dennisc.croquet.Component< ? > parent ) {
		this.getAwtComponent().removePopupMenuListener( this.popupMenuListener );
		super.handleRemovedFrom( parent );
	}
	
	public javax.swing.ListCellRenderer getRenderer() {
		return this.getAwtComponent().getRenderer();
	}
	public void setRenderer( javax.swing.ListCellRenderer listCellRenderer ) {
		this.getAwtComponent().setRenderer( listCellRenderer );
	}
	public int getMaximumRowCount() {
		return this.getAwtComponent().getMaximumRowCount();
	}
	public void setMaximumRowCount( int maximumRowCount ) {
		this.getAwtComponent().setMaximumRowCount( maximumRowCount );
	}

	@Override
	/*package-private*/ TrackableShape getTrackableShapeFor( final E item ) {
		if( this.getAwtComponent().isPopupVisible() ) {
			javax.swing.JComboBox box = this.getAwtComponent();
			javax.accessibility.Accessible accessible = box.getUI().getAccessibleChild( box, 0 );
			if( accessible instanceof javax.swing.JPopupMenu ) {
				javax.swing.JPopupMenu jPopupMenu = (javax.swing.JPopupMenu)accessible;
				java.awt.Component component = jPopupMenu.getComponent( 0 );
				if( component instanceof javax.swing.JScrollPane ) {
					final javax.swing.JScrollPane scrollPane = (javax.swing.JScrollPane)component;
					javax.swing.JViewport viewport = scrollPane.getViewport();
					final java.awt.Component view = viewport.getView();
					return new TrackableShape() {
						public edu.cmu.cs.dennisc.croquet.ScrollPane getScrollPaneAncestor() {
							//todo
							return null;
						}
						public java.awt.Shape getShape( edu.cmu.cs.dennisc.croquet.ScreenElement asSeenBy, java.awt.Insets insets ) {
							java.awt.Rectangle rv = edu.cmu.cs.dennisc.java.awt.ComponentUtilities.convertRectangle( view.getParent(), view.getBounds(), asSeenBy.getAwtComponent() );
							ListSelectionState<E> listSelectionState = ComboBox.this.getModel();
							final int N = listSelectionState.getItemCount();
							int index = listSelectionState.indexOf( item );
							if( index != -1 ) {
								int offsetY = 0;
								int height = rv.height;
//								if( view instanceof javax.swing.JComponent ) {
//									javax.swing.JComponent jView = (javax.swing.JComponent)view;
//									javax.swing.border.Border border = scrollPane.getBorder();
//									java.awt.Insets viewInsets = border.getBorderInsets( scrollPane );
//									java.awt.Insets viewInsets = scrollPane.getInsets();
//								
//									if( viewInsets != null ) {
//										offsetY = viewInsets.top;
//										height = rv.height - viewInsets.top - viewInsets.bottom;
//									}
//								}
//								javax.swing.ListCellRenderer listCellRenderer = ComboBox.this.getAwtComponent().getRenderer();
//								if( listCellRenderer instanceof javax.swing.JComponent ) {
//									edu.cmu.cs.dennisc.print.PrintUtilities.println( ((javax.swing.JComponent)listCellRenderer).getInsets() );
//								}
								double heightPerCell = height/(double)N;
								rv.y += offsetY;
								rv.y += (int)(heightPerCell*index);
								rv.height = (int)heightPerCell;
								
								
								
								
								//todo
								rv.y += 3;
								rv.height -= 6;
								
								
								
							}
							edu.cmu.cs.dennisc.java.awt.RectangleUtilities.inset( rv, insets );
							return rv;
						}
						public java.awt.Shape getVisibleShape( edu.cmu.cs.dennisc.croquet.ScreenElement asSeenBy, java.awt.Insets insets ) {
							return getShape( asSeenBy, insets );
						}
						public boolean isInView() {
							return view.isShowing();
						}
						public final void addComponentListener(java.awt.event.ComponentListener listener) {
						}
						public final void removeComponentListener(java.awt.event.ComponentListener listener) {
						}
						public final void addHierarchyBoundsListener(java.awt.event.HierarchyBoundsListener listener) {
						}
						public final void removeHierarchyBoundsListener(java.awt.event.HierarchyBoundsListener listener) {
						}
					};
//					return Component.lookup( view );
				}
			}
		}
		return this;
	}
	/*package-private*/ void setSwingComboBoxModel( javax.swing.ComboBoxModel model ) {
		this.getAwtComponent().setModel( model );
	}
	
	/*package-private*/ void addItemListener(java.awt.event.ItemListener itemListener) {
		this.getAwtComponent().addItemListener( itemListener );
	}
	/*package-private*/ void removeItemListener(java.awt.event.ItemListener itemListener) {
		this.getAwtComponent().removeItemListener( itemListener );
	}
}
