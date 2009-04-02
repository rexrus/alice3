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
package org.alice.ide.createdeclarationpanes;

/**
 * @author Dennis Cosgrove
 */
public class CreateParameterPane extends CreateDeclarationPane<edu.cmu.cs.dennisc.alice.ast.ParameterDeclaredInAlice> {
	private edu.cmu.cs.dennisc.alice.ast.CodeDeclaredInAlice ownerCode;
	private TypePane typePane = new TypePane() {
		@Override
		protected void handleComponentTypeChange(edu.cmu.cs.dennisc.alice.ast.AbstractType type) {
			//pass
		}
		@Override
		protected void handleIsArrayChange(boolean isArray) {
			//pass
		}
	};
	
	public CreateParameterPane( edu.cmu.cs.dennisc.alice.ast.CodeDeclaredInAlice ownerCode ) {
		this.ownerCode = ownerCode;
		this.setBackground( org.alice.ide.IDE.getParameterColor() );
	}
	@Override
	protected java.awt.Component createIsFinalComponent() {
		return null;
	}
	@Override
	protected java.awt.Component createValueTypeComponent() {
		return this.typePane;
	}
	@Override
	protected java.lang.String getValueTypeText() {
		return "value type:";
	}
	@Override
	protected java.awt.Component[] createDeclarationRow() {
		return null;
	}
	@Override
	protected java.awt.Component createInitializerComponent() {
		return null;
	}
	@Override
	protected java.awt.Component createPreviewComponent() {
		return null;
	}
	@Override
	protected String getTitleDefault() {
		return "Declare Parameter";
	}
	
	@Override
	protected edu.cmu.cs.dennisc.alice.ast.ParameterDeclaredInAlice getActualInputValue() {
		return new edu.cmu.cs.dennisc.alice.ast.ParameterDeclaredInAlice( this.getDeclarationName(), this.typePane.getValueType() );
	}
}
