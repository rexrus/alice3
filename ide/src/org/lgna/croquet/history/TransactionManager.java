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
package org.lgna.croquet.history;

import org.lgna.croquet.*;

/**
 * @author Dennis Cosgrove
 */
public class TransactionManager {
	private static final java.util.Stack< TransactionHistory > stack = edu.cmu.cs.dennisc.java.util.Collections.newStack();

	static {
		stack.push( new TransactionHistory() );
	}
	private TransactionManager() {
		throw new AssertionError();
	}

	public static TransactionHistory getRootTransactionHistory() {
		return stack.firstElement();
	}
	private static TransactionHistory getActiveTransactionHistory() {
		return stack.peek();
	}
	
	public static void pushTransactionHistory( TransactionHistory transactionHistory ) {
		stack.push( transactionHistory );
	}
	public static TransactionHistory popTransactionHistory() {
		return stack.pop();
	}
	
	public static void handleDocumentEvent( StringState stringState, javax.swing.event.DocumentEvent documentEvent, String previousValue, String nextValue ) {
		Transaction transaction = getLastTransaction();
		StringStateChangeStep stringStateChangeStep = null;
		if( transaction != null ) {
			CompletionStep< ? > step = transaction.getCompletionStep();
			if( step instanceof StringStateChangeStep ) {
				if( step.getModel() == stringState ) {
					stringStateChangeStep = (StringStateChangeStep)step;
				}
			}
		} else {
			transaction = getActiveTransaction();
		}
		org.lgna.croquet.triggers.DocumentEventTrigger trigger = new org.lgna.croquet.triggers.DocumentEventTrigger( documentEvent );
		if( stringStateChangeStep != null ) {
			stringStateChangeStep.pendDocumentEvent( trigger, nextValue );
		} else {
			stringStateChangeStep = StringStateChangeStep.createAndAddToTransaction( transaction, stringState, new org.lgna.croquet.triggers.DocumentEventTrigger( documentEvent ), previousValue, nextValue );
		}
	}

	private static boolean isCroquetMenuSelection( javax.swing.MenuElement[] menuElements ) {
		for( javax.swing.MenuElement menuElement : menuElements ) {
			org.lgna.croquet.components.Component< ? > component = org.lgna.croquet.components.Component.lookup( menuElement.getComponent() );
			if( component instanceof org.lgna.croquet.components.MenuBar || component instanceof org.lgna.croquet.components.MenuItem || component instanceof org.lgna.croquet.components.Menu || component instanceof org.lgna.croquet.components.PopupMenu || component instanceof org.lgna.croquet.components.MenuTextSeparator ) {
				return true;
			}
		}
		return menuElements.length == 0;
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
	private static org.lgna.croquet.components.MenuBar getMenuBarOrigin( javax.swing.MenuElement[] menuElements ) {
		javax.swing.JMenuBar jMenuBar = getJMenuBarOrigin( menuElements );
		if( jMenuBar != null ) {
			return (org.lgna.croquet.components.MenuBar)org.lgna.croquet.components.Component.lookup( jMenuBar );
		} else {
			return null;
		}
	}
	private static MenuBarComposite getMenuBarModelOrigin( javax.swing.MenuElement[] menuElements ) {
		org.lgna.croquet.components.MenuBar menuBar = getMenuBarOrigin( menuElements );
		if( menuBar != null ) {
			return menuBar.getComposite();
		} else {
			return null;
		}
	}

	private static javax.swing.MenuElement[] previousMenuElements = {};
	private static void handleMenuSelectionStateChanged( javax.swing.event.ChangeEvent e ) {
		javax.swing.MenuElement[] menuElements = javax.swing.MenuSelectionManager.defaultManager().getSelectedPath();
		if( isCroquetMenuSelection( menuElements ) ) {
			java.util.List< PrepModel > models = edu.cmu.cs.dennisc.java.util.Collections.newLinkedList();
			org.lgna.croquet.components.MenuBar menuBar = getMenuBarOrigin( menuElements );
			int i0;
			if( menuBar != null ) {
				javax.swing.JPopupMenu jPreviousPopupMenu;
				if( previousMenuElements.length >= 3 ) {
					jPreviousPopupMenu = (javax.swing.JPopupMenu)previousMenuElements[ 2 ];
				} else {
					jPreviousPopupMenu = null;
				}
				if( menuElements.length >= 3 ) {
					assert menuElements.length >= 3;
					assert menuElements[ 1 ] instanceof javax.swing.JMenu;
					assert menuElements[ 2 ] instanceof javax.swing.JPopupMenu;
					javax.swing.JPopupMenu jPopupMenu = (javax.swing.JPopupMenu)menuElements[ 2 ];
					
					javax.swing.JMenu jMenu = (javax.swing.JMenu)menuElements[ 1 ];
					org.lgna.croquet.components.Menu menu = (org.lgna.croquet.components.Menu)org.lgna.croquet.components.Component.lookup( jMenu );
					assert menu != null;

					PrepModel menuModel = menu.getModel();
					assert menuModel != null;
					models.add( menuModel );
					i0 = 3;
				} else {
					i0 = -1;
				}
			} else {
				i0 = 0;
			}
			if( i0 != -1 && menuElements.length > 0 ) {
//				edu.cmu.cs.dennisc.print.PrintUtilities.println( menuElements.length );
//				for( javax.swing.MenuElement menuElement : menuElements ) {
//					edu.cmu.cs.dennisc.print.PrintUtilities.print( menuElement.getClass().getName() );
//					edu.cmu.cs.dennisc.print.PrintUtilities.print( ", " );
//				}
//				edu.cmu.cs.dennisc.print.PrintUtilities.println();
				getActiveTransaction().pendMenuSelection( e, menuElements, i0 );
			}

//			if( previousMenuElements.length > 0 ) {
//				if( menuElements.length > 0 ) {
//					java.util.List< Model > models = edu.cmu.cs.dennisc.java.util.Collections.newLinkedList();
//					MenuBar menuBar = getMenuBarOrigin( menuElements );
//					int i0;
//					if( menuBar != null ) {
//						models.add( menuBar.getModel() );
//						javax.swing.JPopupMenu jPreviousPopupMenu;
//						if( previousMenuElements.length >= 3 ) {
//							jPreviousPopupMenu = (javax.swing.JPopupMenu)previousMenuElements[ 2 ];
//						} else {
//							jPreviousPopupMenu = null;
//						}
//						
//						assert menuElements.length >= 3;
//						assert menuElements[ 1 ] instanceof javax.swing.JMenu;
//						assert menuElements[ 2 ] instanceof javax.swing.JPopupMenu;
//						javax.swing.JPopupMenu jPopupMenu = (javax.swing.JPopupMenu)menuElements[ 2 ];
//						
//						javax.swing.JMenu jMenu = (javax.swing.JMenu)menuElements[ 1 ];
//						Menu menu = (Menu)Component.lookup( jMenu );
//						assert menu != null;
//
//						Model menuModel = menu.getModel();
//						assert menuModel != null;
//						models.add( menuModel );
//
//						if( jPreviousPopupMenu != jPopupMenu ) {
//							if( jPreviousPopupMenu != null ) {
//								ModelContext< ? > popupContext = ContextManager.popContext();
//								assert popupContext instanceof PopupOperationContext;
//							}
//							
//							if( menuModel instanceof MenuModel ) {
//								/*AbstractPopupMenuOperationContext popupContext =*/ ContextManager.createAndPushStandardPopupOperationContext( ((MenuModel)menuModel).getPopupMenuOperation(), e, null );
//							} else {
//								System.err.println( "handleMenuSelectionStateChanged: " + menuModel );
//							}
//							
//						}
//						i0 = 3;
//					} else {
//						i0 = 0;
//					}
//					for( int i=i0; i<menuElements.length; i++ ) {
//						javax.swing.MenuElement menuElementI = menuElements[ i ];
//						if( menuElementI instanceof javax.swing.JPopupMenu ) {
//							javax.swing.JPopupMenu jPopupMenu = (javax.swing.JPopupMenu)menuElementI;
//							//pass
//						} else if( menuElementI instanceof javax.swing.JMenuItem ) {
//							javax.swing.JMenuItem jMenuItem = (javax.swing.JMenuItem)menuElementI;
//							Component< ? > component = Component.lookup( jMenuItem );
//							//edu.cmu.cs.dennisc.print.PrintUtilities.println( "handleMenuSelectionStateChanged", i, component.getClass() );
//							if( component instanceof ViewController< ?, ? > ) {
//								ViewController< ?, ? > viewController = (ViewController< ?, ? >)component;
//								//edu.cmu.cs.dennisc.print.PrintUtilities.println( "viewController", i, viewController.getModel() );
//								models.add( viewController.getModel() );
//							}
//						}
//					}
//					ModelContext< ? > modelContext = ContextManager.getCurrentContext();
//					if( modelContext instanceof PopupOperationContext ) {
//						PopupOperationContext popupContext = (PopupOperationContext)modelContext;
//						popupContext.handleMenuSelectionChanged( e, models );
//					} else {
//						System.err.println( "WARNING: handleMenuSelectionStateChanged not PopupMenuOperationContext " + modelContext );
//					}
//					TransactionManager.handleMenuSelectionChanged( models );
//				} else {
//					MenuBarModel menuBarModel = getMenuBarModelOrigin( previousMenuElements );
//					if( menuBarModel != null ) {
//						ModelContext< ? > popupContext = ContextManager.popContext();
//						assert popupContext instanceof StandardPopupOperationContext;
//
//						ModelContext< ? > menuBarContext = ContextManager.popContext();
//						assert menuBarContext instanceof MenuBarModelContext;
//					}
//				}
//			} else {
//				if( menuElements.length > 0 ) {
//					MenuBar menuBar = getMenuBarOrigin( menuElements );
//					if( menuBar != null ) {
//						/*MenuBarModelContext childContext =*/ ContextManager.createAndPushMenuBarModelContext( menuBar.getModel(), e, menuBar );
//						assert menuElements.length == 2;
//					} else {
//						ModelContext< ? > modelContext = ContextManager.getCurrentContext();
//						if( modelContext instanceof StandardPopupOperationContext ) {
//							//pass
//						} else {
//							System.err.println( "combo box? " + menuElements.length + " " + java.util.Arrays.toString( menuElements ) );
//							System.err.println( "modelContext: " + modelContext );
//						}
//					}
//				} else {
//					//assert false;
//					ModelContext< ? > modelContext = ContextManager.getCurrentContext();
//					System.err.println( "both prev and current menu selection length 0" );
//					System.err.println( "modelContext: " + modelContext );
//				}
//			}
//			previousMenuElements = menuElements;
		} else {
			System.err.println( "warning: not croquet menu selection." );
		}
	}
	private static javax.swing.event.ChangeListener menuSelectionChangeListener = new javax.swing.event.ChangeListener() {
		public void stateChanged( javax.swing.event.ChangeEvent e ) {
			handleMenuSelectionStateChanged( e );
		}
	};
//	public static void pendDrop( org.lgna.croquet.Model model, org.lgna.croquet.DropReceptor dropReceptor, org.lgna.croquet.DropSite dropSite ) {
////		fireDropPending( model, dropReceptor, dropSite );
//		getLastTransaction().pendDrop( model, dropReceptor, dropSite );
////		fireDropPended( model, dropReceptor, dropSite );
//	}
	
//	public static void handleMenuSelectionChanged( java.util.List< org.lgna.croquet.Model > models ) {
//		fireMenuItemsSelectionChanged( models );
//	}

//	public static <E> void addListSelectionPopupMenuWillBecomeVisible( ListSelectionState<E> model, javax.swing.event.PopupMenuEvent e, ItemSelectable< ?, ? > itemSelectable ) {
//		ListSelectionStateContext<E> listSelectionStateContext = createAndPushItemSelectionStateContext( model, e, itemSelectable );
//		listSelectionStateContext.handlePopupMenuWillBecomeVisibleEvent( e );
//		TransactionManager.addListSelectionPrepStep( model.getPrepModel() );
//	}
//	public static <E> void addListSelectionPopupMenuWillBecomeInvisible( ListSelectionState<E> model, javax.swing.event.PopupMenuEvent e, ItemSelectable< ?, ? > itemSelectable ) {
//	}
//	public static <E> void addListSelectionPopupMenuCanceled( ListSelectionState<E> model, javax.swing.event.PopupMenuEvent e, ItemSelectable< ?, ? > itemSelectable ) {
//		TransactionManager.addCancelCompletionStep( model );
//	}

	public static void startListeningToMenuSelection() {
		javax.swing.MenuSelectionManager.defaultManager().addChangeListener( menuSelectionChangeListener );
	}
	public static void stopListeningToMenuSelection() {
		javax.swing.MenuSelectionManager.defaultManager().removeChangeListener( menuSelectionChangeListener );
	}

	/*package-private*/ static Transaction getActiveTransaction() {
		return getActiveTransactionHistory().getActiveTransaction();
	}
	private static Transaction getLastTransaction() {
		return getActiveTransactionHistory().getLastTransaction();
	}

//	/*package-private*/ static void fireAddingStep( Step<?> step ) {
//		for( Observer observer : observers ) {
//			observer.addingStep( step );
//		}
//	}
//	/*package-private*/ static void fireAddedStep( Step<?> step ) {
//		for( Observer observer : observers ) {
//			observer.addedStep( step );
//		}
//	}
//	
//	//todo: reduce accessibility
	@Deprecated
	public static void fireTransactionCanceled( Transaction transaction ) {
//		for( Observer observer : observers ) {
//			observer.transactionCanceled( transaction );
//		}
	}
//	//todo: reduce accessibility
	@Deprecated
	public static void firePopupMenuResized( PopupPrepStep< ? > step ) {
		step.fireChanged( new org.lgna.croquet.history.event.PopupMenuResizedEvent( step ) );
//		for( Observer observer : observers ) {
//			observer.popupMenuResized( popupMenu );
//		}
	}
	@Deprecated
	public static void fireDialogOpened( org.lgna.croquet.components.Dialog dialog ) {
//		for( Observer observer : observers ) {
//			observer.dialogOpened( dialog );
//		}
	}
//	private static void fireEditCommitting( org.lgna.croquet.edits.Edit< ? > edit ) {
//		for( Observer observer : observers ) {
//			observer.editCommitting( edit );
//		}
//	}
//	private static void fireEditCommitted( org.lgna.croquet.edits.Edit< ? > edit ) {
//		for( Observer observer : observers ) {
//			observer.editCommitted( edit );
//		}
//	}
//	private static void fireFinishing( Transaction transaction ) {
//		for( Observer observer : observers ) {
//			observer.finishing( transaction );
//		}
//	}
//	private static void fireFinished( Transaction transaction ) {
//		for( Observer observer : observers ) {
//			observer.finished( transaction );
//		}
//	}
//	private static void fireDropPending( org.lgna.croquet.Model model, org.lgna.croquet.DropReceptor dropReceptor, org.lgna.croquet.DropSite dropSite ) {
//		for( Observer observer : observers ) {
//			observer.dropPending( model, dropReceptor, dropSite );
//		}
//	}
//	private static void fireDropPended( org.lgna.croquet.Model model, org.lgna.croquet.DropReceptor dropReceptor, org.lgna.croquet.DropSite dropSite ) {
//		for( Observer observer : observers ) {
//			observer.dropPended( model, dropReceptor, dropSite );
//		}
//	}
//	private static void fireMenuItemsSelectionChanged( java.util.List< org.lgna.croquet.Model > models ) {
//		for( Observer observer : observers ) {
//			observer.menuItemsSelectionChanged( models );
//		}
//	}
		
	public static DragStep addDragStep( org.lgna.croquet.DragModel model, org.lgna.croquet.triggers.Trigger trigger ) {
		return DragStep.createAndAddToTransaction( getActiveTransaction(), model, trigger ); 
	}
	public static ActionOperationStep addActionOperationStep( org.lgna.croquet.ActionOperation model, org.lgna.croquet.triggers.Trigger trigger ) {
		Transaction transaction = getActiveTransaction();
		return ActionOperationStep.createAndAddToTransaction( transaction, model, trigger ); 
	}
	public static SerialOperationStep addSerialOperationStep( org.lgna.croquet.SerialOperation model, org.lgna.croquet.triggers.Trigger trigger ) {
		Transaction transaction = getActiveTransaction();
		return SerialOperationStep.createAndAddToTransaction( transaction, model, trigger ); 
	}
	public static PlainDialogOperationStep addPlainDialogOperationStep( org.lgna.croquet.PlainDialogOperation model, org.lgna.croquet.triggers.Trigger trigger ) {
		Transaction transaction = getActiveTransaction();
		return PlainDialogOperationStep.createAndAddToTransaction( transaction, model, trigger );
	}
	public static PlainDialogCloseOperationStep addPlainDialogCloseOperationStep( org.lgna.croquet.PlainDialogCloseOperation model, org.lgna.croquet.triggers.Trigger trigger ) {
		Transaction transaction = getActiveTransaction();
		return PlainDialogCloseOperationStep.createAndAddToTransaction( transaction, model, trigger );
	}
	public static InputDialogOperationStep addInputDialogOperationStep( org.lgna.croquet.InputDialogOperation model, org.lgna.croquet.triggers.Trigger trigger ) {
		Transaction transaction = getActiveTransaction();
		return InputDialogOperationStep.createAndAddToTransaction( transaction, model, trigger );
	}
	public static InformationDialogOperationStep addInformationDialogOperationStep( org.lgna.croquet.InformationDialogOperation model, org.lgna.croquet.triggers.Trigger trigger ) {
		Transaction transaction = getActiveTransaction();
		return InformationDialogOperationStep.createAndAddToTransaction( transaction, model, trigger );
	}
	public static WizardDialogOperationStep addWizardDialogOperationStep( org.lgna.croquet.WizardDialogOperation model, org.lgna.croquet.triggers.Trigger trigger ) {
		Transaction transaction = getActiveTransaction();
		return WizardDialogOperationStep.createAndAddToTransaction( transaction, model, trigger );
	}
	public static StandardPopupPrepStep addStandardPopupOperationStep( org.lgna.croquet.StandardPopupPrepModel standardPopupOperation, org.lgna.croquet.triggers.Trigger trigger ) {
		return StandardPopupPrepStep.createAndAddToTransaction( getActiveTransaction(), standardPopupOperation, trigger );
	}
	public static <T> CascadePopupPrepStep<T> addCascadePopupPrepStep( org.lgna.croquet.CascadePopupPrepModel<T> model, org.lgna.croquet.triggers.Trigger trigger ) {
		return CascadePopupPrepStep.createAndAddToTransaction( getActiveTransaction(), model, trigger );
	}
	public static <T> CascadePopupOperationStep<T> addCascadePopupCompletionStep( org.lgna.croquet.CascadeCompletionModel<T> model, org.lgna.croquet.triggers.Trigger trigger ) {
		return CascadePopupOperationStep.createAndAddToTransaction( getActiveTransaction(), model, trigger );
	}

	public static BooleanStateChangeStep addBooleanStateChangeStep( org.lgna.croquet.BooleanState model, org.lgna.croquet.triggers.Trigger trigger ) {
		return BooleanStateChangeStep.createAndAddToTransaction( getActiveTransaction(), model, trigger );
	}
	public static <E> ListSelectionStateChangeStep<E> addListSelectionStateChangeStep( org.lgna.croquet.ListSelectionState< E > model, org.lgna.croquet.triggers.Trigger trigger ) {
		return ListSelectionStateChangeStep.createAndAddToTransaction( getActiveTransaction(), model, trigger ); 
	}
	public static <E> ListSelectionStatePrepStep<E> addListSelectionPrepStep( org.lgna.croquet.ListSelectionStatePrepModel< E > model, org.lgna.croquet.triggers.Trigger trigger ) {
		return ListSelectionStatePrepStep.createAndAddToTransaction( getActiveTransaction(), model, trigger ); 
	}
	public static <E> CancelCompletionStep addCancelCompletionStep( org.lgna.croquet.CompletionModel model, org.lgna.croquet.triggers.Trigger trigger ) {
		return CancelCompletionStep.createAndAddToTransaction( getActiveTransaction(), model, trigger ); 
	}


//	private static void popCompletionStepTransactionHistoryIfNecessary( org.lgna.croquet.Model model ) {
//		TransactionHistory transactionHistory = getActiveTransactionHistory();
//		CompletionStep< ? > completionStep = transactionHistory.getParent();
//		if( completionStep != null ) {
//			if( completionStep.getModel() == model ) {
//				completionStep.popTransactionHistoryIfNecessary();
//			}
//		}
//	}

//	private static void finishPendingTransactionIfNecessary() {
//		TransactionHistory activeTransactionHistory = getActiveTransactionHistory();
//		CompletionStep< ? > completionStep = activeTransactionHistory.getParent();
//		if( completionStep != null && completionStep.isActive() ) {
//			if( stepsAwaitingFinish.contains( completionStep ) ) {
//				finish( completionStep.getModel() );
//				stepsAwaitingFinish.remove( completionStep );
//			}
//		}
//	}

//	public static void commit( edu.cmu.cs.dennisc.croquet.Edit< ? > edit ) {
//		popCompletionStepTransactionHistoryIfNecessary( edit.getModel() );
//		fireEditCommitting( edit );
//		getLastTransaction().commit( edit );
//		fireEditCommitted( edit );
////		finishPendingTransactionIfNecessary();
//	}
//	public static void finish( edu.cmu.cs.dennisc.croquet.CompletionModel model ) {
//		popCompletionStepTransactionHistoryIfNecessary( model );
//		Transaction transaction = getLastTransaction();
//		fireFinishing( transaction );
//		transaction.finish();
//		fireFinished( transaction );
////		finishPendingTransactionIfNecessary();
//	}
//	public static void cancel( edu.cmu.cs.dennisc.croquet.CompletionModel model, org.lgna.croquet.Trigger trigger ) {
//		popCompletionStepTransactionHistoryIfNecessary( model );
//		getLastTransaction().cancel( trigger );
////		finishPendingTransactionIfNecessary();
//	}

	public static <E> Transaction createSimulatedTransaction( TransactionHistory transactionHistory, ListSelectionState< E > state, E prevValue, E nextValue, boolean isPrepStepDesired ) {
		org.lgna.croquet.history.Transaction rv = new org.lgna.croquet.history.Transaction( transactionHistory );
		if( isPrepStepDesired ) {
			org.lgna.croquet.history.ListSelectionStatePrepStep.createAndAddToTransaction( rv, state.getPrepModel(), new org.lgna.croquet.triggers.SimulatedTrigger() );
		}
		org.lgna.croquet.history.ListSelectionStateChangeStep completionStep = org.lgna.croquet.history.ListSelectionStateChangeStep.createAndAddToTransaction( rv, state, new org.lgna.croquet.triggers.SimulatedTrigger() );
		org.lgna.croquet.edits.ListSelectionStateEdit edit = new org.lgna.croquet.edits.ListSelectionStateEdit( completionStep, prevValue, nextValue );
		completionStep.setEdit( edit );
		return rv;
	}
	public static void simulatedMenuTransaction( Transaction transaction, java.util.List< MenuItemPrepModel > menuItemPrepModels ) {
		for( org.lgna.croquet.MenuItemPrepModel menuItemPrepModel : menuItemPrepModels ) {
			System.err.println( "todo: add step for: " + menuItemPrepModel );
			//org.lgna.croquet.steps.MenuItemPrepStep.createAndAddToTransaction( transaction, menuItemPrepModel, org.lgna.croquet.triggers.SimulatedTrigger.SINGLETON );
		}
	}

//	public static CascadeBlankStep createCascadeBlankStep( CascadeBlank model ) {
//		return null;
//	}
//	public static CascadeCancelStep createCascadeCancelStep( CascadeCancel model ) {
//		return null;
//	}
//	public static CascadeFillInPrepStep createCascadeFillInPrepStep( CascadeFillIn model ) {
//		return null;
//	}
//	public static CascadeMenuStep createCascadeMenuStep( CascadeMenu model ) {
//		return null;
//	}
//	public static CascadeRootStep createCascadeRootStep( CascadeRoot model ) {
//		return null;
//	}
//	public static CascadeSeparatorStep createCascadeSeparatorStep( CascadeSeparator model ) {
//		return new CascadeSeparatorStep( model, null );
//	}

	public static void handleStateChanged( BoundedRangeIntegerState boundedRangeIntegerState, javax.swing.event.ChangeEvent e ) {
//		org.lgna.croquet.steps.TransactionManager.handleStateChanged( BoundedRangeIntegerState.this, e );
//		org.lgna.croquet.steps.BoundedRangeIntegerStateChangeStep step;
//		if( this.previousValueIsAdjusting ) {
//			step = (org.lgna.croquet.steps.BoundedRangeIntegerStateChangeStep)org.lgna.croquet.steps.TransactionManager.getActiveTransaction().getCompletionStep();
//		} else {
//			step = org.lgna.croquet.steps.TransactionManager.addBoundedRangeIntegerStateChangeStep( BoundedRangeIntegerState.this );
//		}
//		this.previousValueIsAdjusting = boundedRangeModel.getValueIsAdjusting();
//		step.handleStateChanged( e );
//		BoundedRangeIntegerState.this.fireValueChanged( e );
//
//		if( this.previousValueIsAdjusting ) {
//			//pass
//		} else {
//			int nextValue = boundedRangeModel.getValue();
//			step.commitAndInvokeDo( new org.lgna.croquet.edits.BoundedRangeIntegerStateEdit( e, BoundedRangeIntegerState.this.previousValue, nextValue, false ) );
//			BoundedRangeIntegerState.this.previousValue = nextValue;
////				ModelContext< ? > popContext = ContextManager.popContext();
////				assert popContext == boundedRangeIntegerStateContext;
//		}
	}

	public static org.lgna.croquet.edits.BooleanStateEdit commitEdit( BooleanState booleanState, boolean value, org.lgna.croquet.triggers.Trigger trigger ) {
		org.lgna.croquet.history.BooleanStateChangeStep step = org.lgna.croquet.history.TransactionManager.addBooleanStateChangeStep( booleanState, trigger );
		org.lgna.croquet.edits.BooleanStateEdit rv = new org.lgna.croquet.edits.BooleanStateEdit( step, value );
		step.commitAndInvokeDo( rv );
		return rv;
	}
	public static void handleItemStateChanged( BooleanState booleanState, java.awt.event.ItemEvent e ) {
		if( Manager.isInTheMidstOfUndoOrRedo() ) {
			//pass
		} else {
			if( booleanState.isToBeIgnored() ) {
				//pass
			} else {
				commitEdit( booleanState, e.getStateChange() == java.awt.event.ItemEvent.SELECTED, new org.lgna.croquet.triggers.ItemEventTrigger( e ) );
			}
		}
	}
}
