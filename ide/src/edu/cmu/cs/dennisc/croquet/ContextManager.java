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
public class ContextManager {
	private static java.util.Stack< ModelContext< ? > > stack;
	static {
		stack = edu.cmu.cs.dennisc.java.util.Collections.newStack();
		stack.push( new ModelContext( null, null, null, null ) {
		} );
	}
	public static ModelContext< ? > getRootContext() {
		return stack.firstElement();
	}
	public static ModelContext< ? > getCurrentContext() {
		return stack.peek();
	}
	/*package-private*/ static ActionOperationContext createAndPushActionOperationContext(ActionOperation actionOperation, java.util.EventObject e, ViewController<?, ?> viewController) {
		ModelContext< ? > parentContext = getCurrentContext();
		ActionOperationContext rv = new ActionOperationContext(parentContext, actionOperation, e, viewController);
		parentContext.addChild(rv);
		stack.push( rv );
		return rv;
	}
	/*package-private*/ static CompositeOperationContext createAndPushCompositeOperationContext(CompositeOperation compositeOperation, java.util.EventObject e, ViewController<?, ?> viewController) {
		ModelContext< ? > parentContext = getCurrentContext();
		CompositeOperationContext rv = new CompositeOperationContext(parentContext, compositeOperation, e, viewController);
		parentContext.addChild(rv);
		stack.push( rv );
		return rv;
	}
	/*package-private*/ static DialogOperationContext createAndPushDialogOperationContext(DialogOperation dialogOperation, java.util.EventObject e, ViewController<?, ?> viewController) {
		ModelContext< ? > parentContext = getCurrentContext();
		DialogOperationContext rv = new DialogOperationContext(parentContext, dialogOperation, e, viewController);
		parentContext.addChild(rv);
		stack.push( rv );
		return rv;
	}
	/*package-private*/ static <J extends Component<?>> InformationDialogOperationContext<J> createAndPushInformationDialogOperationContext(InformationDialogOperation<J> informationDialogOperation, java.util.EventObject e, ViewController<?, ?> viewController) {
		ModelContext< ? > parentContext = getCurrentContext();
		InformationDialogOperationContext<J> rv = new InformationDialogOperationContext<J>(parentContext, informationDialogOperation, e, viewController);
		parentContext.addChild(rv);
		stack.push( rv );
		return rv;
	}
	/*package-private*/ static <J extends Component< ? >> InputDialogOperationContext<J> createAndPushInputDialogOperationContext(InputDialogOperation<J> inputDialogOperation, java.util.EventObject e, ViewController<?, ?> viewController) {
		ModelContext< ? > parentContext = getCurrentContext();
		InputDialogOperationContext<J> rv = new InputDialogOperationContext<J>(parentContext, inputDialogOperation, e, viewController);
		parentContext.addChild(rv);
		stack.push( rv );
		return rv;
	}
	/*package-private*/ static WizardDialogOperationContext createAndPushWizardDialogOperationContext(WizardDialogOperation informationDialogOperation, java.util.EventObject e, ViewController<?, ?> viewController) {
		ModelContext< ? > parentContext = getCurrentContext();
		WizardDialogOperationContext rv = new WizardDialogOperationContext(parentContext, informationDialogOperation, e, viewController);
		parentContext.addChild(rv);
		stack.push( rv );
		return rv;
	}
	
	/*package-private*/ static PopupMenuOperationContext createAndPushPopupMenuOperationContext(PopupMenuOperation popupMenuOperation, java.util.EventObject e, ViewController<?, ?> viewController) {
		ModelContext< ? > parentContext = getCurrentContext();
		PopupMenuOperationContext rv = new PopupMenuOperationContext(parentContext, popupMenuOperation, e, viewController);
		parentContext.addChild(rv);
		stack.push( rv );
		return rv;
	}
	/*package-private*/ static <T> ListSelectionStateContext<T> createAndPushItemSelectionStateContext(ListSelectionState<T> itemSelectionState, java.util.EventObject e, ViewController<?, ?> viewController, int prevIndex, T prevItem, int nextIndex, T nextItem) {
		ModelContext< ? > parentContext = getCurrentContext();
		ListSelectionStateContext<T> rv = new ListSelectionStateContext<T>(parentContext, itemSelectionState, e, viewController, prevIndex, prevItem, nextIndex, nextItem);
		parentContext.addChild(rv);
		stack.push( rv );
		return rv;
	}
	/*package-private*/ static BoundedRangeIntegerStateContext createAndPushBoundedRangeIntegerStateContext(BoundedRangeIntegerState boundedRangeIntegerState) {
		ModelContext< ? > parentContext = getCurrentContext();
		BoundedRangeIntegerStateContext rv = new BoundedRangeIntegerStateContext(parentContext, boundedRangeIntegerState, null, null);
		parentContext.addChild(rv);
		stack.push( rv );
		return rv;
	}
	/*package-private*/ static BooleanStateContext createAndPushBooleanStateContext(BooleanState booleanState, java.awt.event.ItemEvent e, ViewController<?, ?> viewController) {
		ModelContext< ? > parentContext = getCurrentContext();
		BooleanStateContext rv = new BooleanStateContext(parentContext, booleanState, e, viewController);
		parentContext.addChild(rv);
		stack.push( rv );
		return rv;
	}
	/*package-private*/ static StringStateContext createAndPushStringStateContext(StringState stringState) {
		ModelContext< ? > parentContext = getCurrentContext();
		StringStateContext rv = new StringStateContext(parentContext, stringState, null, null);
		parentContext.addChild(rv);
		stack.push( rv );
		return rv;
	}
	/*package-private*/ static MenuBarModelContext createAndPushMenuBarModelContext(MenuBarModel menuBarModel, javax.swing.event.ChangeEvent e, MenuBar menuBar) {
		ModelContext< ? > parentContext = getCurrentContext();
		MenuBarModelContext rv = new MenuBarModelContext(parentContext, menuBarModel, e, menuBar);
		parentContext.addChild(rv);
		stack.push( rv );
		return rv;
	}
	/*package-private*/ static MenuModelContext createAndPushMenuModelContext(MenuModel menuModel, MenuItemContainer menuItemContainer) {
		ModelContext< ? > parentContext = getCurrentContext();
		MenuModelContext rv = new MenuModelContext( parentContext, menuModel, null, menuItemContainer.getViewController() );
		parentContext.addChild(rv);
		stack.push( rv );
		return rv;
	}
	/*package-private*/ static DragAndDropContext createAndPushDragAndDropContext(DragAndDropOperation dragAndDropOperation, java.awt.event.MouseEvent originalMouseEvent, java.awt.event.MouseEvent latestMouseEvent, DragComponent dragSource) {
		ModelContext< ? > parentContext = getCurrentContext();
		DragAndDropContext rv = new DragAndDropContext(parentContext, dragAndDropOperation, originalMouseEvent, latestMouseEvent, dragSource);
		parentContext.addChild(rv);
		stack.push( rv );
		return rv;
	}
	/*package-private*/ static ModelContext< ? > popContext() {
		return stack.pop();
	}
	
	private static javax.swing.JMenuBar getJMenuBarOrigin( javax.swing.MenuElement[] menuElements ) { 
		if( menuElements.length > 0 ) {
			javax.swing.MenuElement menuElement0 = menuElements[ 0 ];
			if( menuElement0 instanceof javax.swing.JMenuBar ) {
				return (javax.swing.JMenuBar)menuElement0;
			}
		}
		return null;
	}
	private static MenuBar getMenuBarOrigin( javax.swing.MenuElement[] menuElements ) {
		javax.swing.JMenuBar jMenuBar = getJMenuBarOrigin( menuElements );
		if( jMenuBar != null ) {
			return (MenuBar)Component.lookup( jMenuBar );
		} else {
			return null;
		}
	}
	private static MenuBarModel getMenuBarModelOrigin( javax.swing.MenuElement[] menuElements ) {
		MenuBar menuBar = getMenuBarOrigin( menuElements );
		if( menuBar != null ) {
			return menuBar.getModel();
		} else {
			return null;
		}
	}

	private static javax.swing.MenuElement[] previousMenuElements = {};
	private static void handleStateChanged( javax.swing.event.ChangeEvent e ) {
		javax.swing.MenuElement[] menuElements = javax.swing.MenuSelectionManager.defaultManager().getSelectedPath();
		if( previousMenuElements.length > 0 ) {
			if( menuElements.length > 0 ) {
				java.util.List< Model > models = edu.cmu.cs.dennisc.java.util.Collections.newLinkedList();
				MenuBar menuBar = getMenuBarOrigin( menuElements );
				int i0;
				if( menuBar != null ) {
					
					models.add( menuBar.getModel() );
					javax.swing.JPopupMenu jPreviousPopupMenu;
					if( previousMenuElements.length >= 3 ) {
						jPreviousPopupMenu = (javax.swing.JPopupMenu)previousMenuElements[ 2 ];
					} else {
						jPreviousPopupMenu = null;
					}
					
					assert menuElements.length >= 3;
					assert menuElements[ 1 ] instanceof javax.swing.JMenu;
					assert menuElements[ 2 ] instanceof javax.swing.JPopupMenu;
					javax.swing.JPopupMenu jPopupMenu = (javax.swing.JPopupMenu)menuElements[ 2 ];
					
					javax.swing.JMenu jMenu = (javax.swing.JMenu)menuElements[ 1 ];
					Menu<MenuModel> menu = (Menu<MenuModel>)Component.lookup( jMenu );
					assert menu != null;

					MenuModel menuModel = menu.getModel();
					assert menuModel != null;
					models.add( menuModel );

					if( jPreviousPopupMenu != jPopupMenu ) {
						if( jPreviousPopupMenu != null ) {
							ModelContext< ? > popupMenuOperationContext = ContextManager.popContext();
							assert popupMenuOperationContext instanceof PopupMenuOperationContext;
						}
						
						
						
						/*PopupMenuOperationContext popupMenuOperationContext =*/ ContextManager.createAndPushPopupMenuOperationContext( menuModel.getPopupMenuOperation(), e, null );
					}
					i0 = 3;
				} else {
					i0 = 0;
				}
				for( int i=i0; i<menuElements.length; i++ ) {
					javax.swing.MenuElement menuElementI = menuElements[ i ];
					if( menuElementI instanceof javax.swing.JPopupMenu ) {
						javax.swing.JPopupMenu jPopupMenu = (javax.swing.JPopupMenu)menuElementI;
						//pass
					} else if( menuElementI instanceof javax.swing.JMenuItem ) {
						javax.swing.JMenuItem jMenuItem = (javax.swing.JMenuItem)menuElementI;
						Component< ? > component = Component.lookup( jMenuItem );
						if( component instanceof ViewController< ?, ? > ) {
							ViewController< ?, ? > viewController = (ViewController< ?, ? >)component;
							models.add( viewController.getModel() );
						}
					}
				}
				ModelContext< ? > modelContext = ContextManager.getCurrentContext();
				assert modelContext instanceof PopupMenuOperationContext;
				PopupMenuOperationContext popupMenuOperationContext = (PopupMenuOperationContext)modelContext;
				popupMenuOperationContext.handleMenuSelectionChanged( models, e );
			} else {
				MenuBarModel menuBarModel = getMenuBarModelOrigin( previousMenuElements );
				if( menuBarModel != null ) {
					ModelContext< ? > popupMenuOperationContext = ContextManager.popContext();
					assert popupMenuOperationContext instanceof PopupMenuOperationContext;

					ModelContext< ? > menuBarContext = ContextManager.popContext();
					assert menuBarContext instanceof MenuBarModelContext;
				}
			}
		} else {
			if( menuElements.length > 0 ) {
				MenuBar menuBar = getMenuBarOrigin( menuElements );
				if( menuBar != null ) {
					/*MenuBarModelContext childContext =*/ ContextManager.createAndPushMenuBarModelContext( menuBar.getModel(), e, menuBar );
					assert menuElements.length == 2;
				} else {
					ModelContext< ? > modelContext = ContextManager.getCurrentContext();
					assert modelContext instanceof PopupMenuOperationContext;
				}
			} else {
				assert false;
			}
		}
		previousMenuElements = menuElements;
	}
	private static javax.swing.event.ChangeListener changeListener = new javax.swing.event.ChangeListener() {
		public void stateChanged( javax.swing.event.ChangeEvent e ) {
			handleStateChanged( e );
		}
	};
	public static void startListeningToMenuSelection() {
		javax.swing.MenuSelectionManager.defaultManager().addChangeListener( changeListener );
	}
	public static void stopListeningToMenuSelection() {
		javax.swing.MenuSelectionManager.defaultManager().removeChangeListener( changeListener );
	}
	
//	public static <A extends Application> A createAndShowApplication( Class<A> cls, String[] args ) throws IllegalAccessException, InstantiationException {
//		A application = cls.newInstance();
//		//application.initialize( args );
//		//application.getFrame().setVisible( true );
//		return application;
//	}
	
	
	
	private static java.util.Map< java.util.UUID, java.util.Set< Model > > mapIdToModels = edu.cmu.cs.dennisc.java.util.Collections.newHashMap();
	private static java.util.Set< Model > lookupModels( java.util.UUID id ) {
		synchronized ( mapIdToModels ) {
			return mapIdToModels.get( id );
		}
	}
	@Deprecated
	public static Model findFirstAppropriateModel( java.util.UUID id ) {
		java.util.Set< Model > models = lookupModels( id );
		for( Model model : models ) {
			for( JComponent<?> component : model.getComponents() ) {
				if( component.getAwtComponent().isShowing() ) {
					return model;
				}
			}
			for( JComponent<?> component : model.getComponents() ) {
				if( component.getAwtComponent().isVisible() ) {
					return model;
				}
			}
		}
		return null;
	}

	/*package-private*/ static void registerModel( Model model ) {
		java.util.UUID id = model.getIndividualUUID();
		synchronized ( mapIdToModels ) {
			java.util.Set< Model > set = mapIdToModels.get( id );
			if( set != null ) {
				//pass
			} else {
				set = edu.cmu.cs.dennisc.java.util.Collections.newHashSet();
				mapIdToModels.put( id, set );
			}
			set.add( model );
		}
	}
	/*package-private*/ static void unregisterModel( Model model ) {
		//edu.cmu.cs.dennisc.print.PrintUtilities.println( "unregister:", model );
		java.util.UUID id = model.getIndividualUUID();
		synchronized ( mapIdToModels ) {
			java.util.Set< Model > set = mapIdToModels.get( id );
			if( set != null ) {
				//edu.cmu.cs.dennisc.print.PrintUtilities.println( "pre set size:", set.size() );
				set.remove( model );
				//edu.cmu.cs.dennisc.print.PrintUtilities.println( "post set size:", set.size() );
				if( set.size() == 0 ) {
					mapIdToModels.remove( id );
				}
			} else {
				edu.cmu.cs.dennisc.print.PrintUtilities.println( "todo: investigate unregister" );
			}
		}
	}
	

	/*package-private*/ static void localizeAllModels() {
		synchronized ( mapIdToModels ) {
			java.util.Collection< java.util.Set< Model > > sets = mapIdToModels.values();
			for( java.util.Set< Model > set : sets ) {
				for( Model model : set ) {
					model.localize();
//					for( JComponent<?> component : model.getComponents() ) {
//					}
				}
			}
		}
	}
}
