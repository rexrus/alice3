/*
 * Copyright (c) 2006-2009, Carnegie Mellon University. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * 
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 
 * 3. Products derived from the software may not be called "Alice",
 *    nor may "Alice" appear in their name, without prior written
 *    permission of Carnegie Mellon University.
 * 
 * 4. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    "This product includes software developed by Carnegie Mellon University"
 */
package alice.ide.codeeditor;

import alice.ide.ast.PotentiallyDraggablePane;

/**
 * @author Dennis Cosgrove
 */
public class ExpressionPropertyDropDownPane extends DropDownPane implements alice.ide.ast.DropReceptor {
	private edu.cmu.cs.dennisc.alice.ast.ExpressionProperty expressionProperty;
	public ExpressionPropertyDropDownPane( javax.swing.JComponent component, edu.cmu.cs.dennisc.alice.ast.ExpressionProperty expressionProperty, javax.swing.JComponent prefixPane ) {
		super( prefixPane, component, null );
		this.expressionProperty = expressionProperty;
	}
	public ExpressionPropertyDropDownPane( javax.swing.JComponent component, edu.cmu.cs.dennisc.alice.ast.ExpressionProperty expressionProperty ) {
		this( component, expressionProperty, null );
	}
	public edu.cmu.cs.dennisc.alice.ast.ExpressionProperty getExpressionProperty() {
		return this.expressionProperty;
	}
	
	protected edu.cmu.cs.dennisc.alice.ast.AbstractType getDesiredType() {
		return this.expressionProperty.getExpressionType();
	}
	@Override
	protected void handleLeftMousePress( java.awt.event.MouseEvent e ) {
		edu.cmu.cs.dennisc.print.PrintUtilities.println( "handleLeftMousePress:", e );
		super.handleLeftMousePress( e );
		getIDE().promptUserForExpression( this.getDesiredType(), this.expressionProperty.getValue(), e, new edu.cmu.cs.dennisc.task.TaskObserver< edu.cmu.cs.dennisc.alice.ast.Expression >() {
			public void handleCompletion( edu.cmu.cs.dennisc.alice.ast.Expression e ) {
				ExpressionPropertyDropDownPane.this.expressionProperty.setValue( e );
				ExpressionPropertyDropDownPane.this.revalidate();
				ExpressionPropertyDropDownPane.this.repaint();
				getIDE().unsetPreviousExpression();
				getIDE().markChanged( "expression menu" );
			}
			public void handleCancelation() {
				getIDE().unsetPreviousExpression();
			}			
		} );
	}
	public boolean isPotentiallyAcceptingOf( alice.ide.ast.PotentiallyDraggablePane source ) {
		return source instanceof alice.ide.ast.ExpressionLikeSubstance;
	}
	public void dragStarted( alice.ide.ast.PotentiallyDraggablePane source, java.awt.event.MouseEvent e ) {
	}
	public void dragEntered( alice.ide.ast.PotentiallyDraggablePane source, java.awt.event.MouseEvent e ) {
		source.setDropProxyLocationAndShowIfNecessary( new java.awt.Point( 0, 0 ), this.getMainComponent(), this.getBounds().height );
	}
	public void dragUpdated( alice.ide.ast.PotentiallyDraggablePane source, java.awt.event.MouseEvent e ) {
	}
	public void dragDropped( final alice.ide.ast.PotentiallyDraggablePane source, final java.awt.event.MouseEvent e ) {
		class Worker extends org.jdesktop.swingworker.SwingWorker< edu.cmu.cs.dennisc.alice.ast.Expression, Object > {
			private alice.ide.ast.DropAndDropEvent dragAndDropEvent;
			private StatementListPropertyPane statementListPropertyPane;

			public Worker() {
				this.dragAndDropEvent = new alice.ide.ast.DropAndDropEvent( source, ExpressionPropertyDropDownPane.this, e );
			}
			@Override
			protected edu.cmu.cs.dennisc.alice.ast.Expression doInBackground() throws java.lang.Exception {
				PotentiallyDraggablePane potentiallyDraggablePane = this.dragAndDropEvent.getTypedSource();
				if( potentiallyDraggablePane instanceof AccessiblePane ) {
					AccessiblePane accessiblePane = (AccessiblePane)source;
//					try {
						edu.cmu.cs.dennisc.alice.ast.Expression expression = accessiblePane.createExpression( this.dragAndDropEvent );
						return expression;
//					} catch( Throwable t ) {
//						this.cancel( true );
//						throw new RuntimeException( t );
//					}
				} else {
					return null;
				}
			}
			@Override
			protected void done() {
				try {
					edu.cmu.cs.dennisc.alice.ast.Expression expression = this.get();
					if( expression != null ) {
						ExpressionPropertyDropDownPane.this.expressionProperty.setValue( expression );
						getIDE().markChanged( "expression dropped" );
					} else {
						//todo?
					}
				} catch( InterruptedException ie ) {
					throw new RuntimeException( ie );
				} catch( java.util.concurrent.ExecutionException ee ) {
					throw new RuntimeException( ee );
				} finally {
					source.hideDropProxyIfNecessary();
				}
			}
		}
		Worker worker = new Worker();
		worker.execute();
	}
	public void dragExited( alice.ide.ast.PotentiallyDraggablePane source, java.awt.event.MouseEvent e, boolean isDropRecipient ) {
		source.hideDropProxyIfNecessary();
	}
	public void dragStopped( alice.ide.ast.PotentiallyDraggablePane source, java.awt.event.MouseEvent e ) {
	}
	public java.awt.Component getAWTComponent() {
		return this;
	}
}
