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
package org.alice.ide.cascade.fillerinners;

/**
 * @author Dennis Cosgrove
 */
public class ConstantsOwningFillerInner extends ExpressionFillerInner {
	public ConstantsOwningFillerInner( edu.cmu.cs.dennisc.alice.ast.AbstractType type ) {
		super( type, edu.cmu.cs.dennisc.alice.ast.FieldAccess.class );
	}
	public ConstantsOwningFillerInner( Class<?> cls ) {
		this( edu.cmu.cs.dennisc.alice.ast.TypeDeclaredInJava.get( cls ) );
	}
	@Override
	public void addFillIns( cascade.Blank blank ) {
		edu.cmu.cs.dennisc.alice.ast.AbstractType type = this.getType();
		java.util.List< edu.cmu.cs.dennisc.alice.ast.AbstractField > constants = new java.util.LinkedList< edu.cmu.cs.dennisc.alice.ast.AbstractField >();
 		for( edu.cmu.cs.dennisc.alice.ast.AbstractField field : type.getDeclaredFields() ) {
 			if( field.isPublicAccess() && field.isStatic() && field.isFinal() ) {
 				constants.add( field );
 			}
 		}
 		for( edu.cmu.cs.dennisc.alice.ast.AbstractField constant : constants ) {
 			this.addExpressionFillIn( blank, new edu.cmu.cs.dennisc.alice.ast.TypeExpression( type ), constant );
 		}
	}
}
