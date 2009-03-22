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
package org.alice.ide.templates;

/**
 * @author Dennis Cosgrove
 */
public abstract class StatementTemplate extends org.alice.ide.ast.StatementLikeSubstance {

	public StatementTemplate( Class< ? extends edu.cmu.cs.dennisc.alice.ast.Statement > cls ) {
		super( cls );
	}

	protected zoot.DragAndDropOperation dragAndDropOperation;

	@Override
	protected boolean isPressed() {
		return false;
	}
	//	protected zoot.ActionOperation createPopupOperation() {
	//		return new zoot.AbstractActionOperation() {
	//			public void perform( zoot.ActionContext actionContext ) {
	//				edu.cmu.cs.dennisc.print.PrintUtilities.println( "todo: handle popupOperation" );
	//			}
	//		};
	//	}
	protected zoot.DragAndDropOperation createDragAndDropOperation() {
		return new org.alice.ide.operations.AbstractDragAndDropOperation() {
			@Override
			protected zoot.ActionOperation createDropOperation() {
				return null;
			}
		};
	}
	@Override
	public void addNotify() {
		if( this.dragAndDropOperation != null ) {
			//pass
		} else {
			this.dragAndDropOperation = this.createDragAndDropOperation();
		}
		this.setDragAndDropOperation( this.dragAndDropOperation );
		super.addNotify();
	}
	@Override
	public void removeNotify() {
		super.removeNotify();
		this.setPopupOperation( null );
	}

//	protected edu.cmu.cs.dennisc.alice.ast.Statement getEmptyStatement() {
//		return this.emptyStatement;
//	}
}
