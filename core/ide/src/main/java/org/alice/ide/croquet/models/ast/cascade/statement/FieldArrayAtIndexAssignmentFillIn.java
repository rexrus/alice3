/**
 * Copyright (c) 2006-2012, Carnegie Mellon University. All rights reserved.
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
package org.alice.ide.croquet.models.ast.cascade.statement;

import org.alice.ide.ast.EmptyExpression;

/**
 * @author Dennis Cosgrove
 */
public final class FieldArrayAtIndexAssignmentFillIn extends org.alice.ide.croquet.models.cascade.ExpressionFillInWithExpressionBlanks<org.lgna.project.ast.AssignmentExpression> {
	private static java.util.Map<org.lgna.project.ast.UserField, FieldArrayAtIndexAssignmentFillIn> map = edu.cmu.cs.dennisc.java.util.Collections.newHashMap();

	public static synchronized FieldArrayAtIndexAssignmentFillIn getInstance( org.lgna.project.ast.UserField field ) {
		FieldArrayAtIndexAssignmentFillIn rv = map.get( field );
		if( rv != null ) {
			//pass
		} else {
			rv = new FieldArrayAtIndexAssignmentFillIn( field );
			map.put( field, rv );
		}
		return rv;
	}

	private final org.lgna.project.ast.AssignmentExpression transientValue;

	private FieldArrayAtIndexAssignmentFillIn( org.lgna.project.ast.UserField field ) {
		super( java.util.UUID.fromString( "3385e94e-3299-42d2-941f-435af105dee4" ),
				org.alice.ide.croquet.models.cascade.ExpressionBlank.getBlankForType( Integer.class, org.lgna.project.annotations.ArrayIndexDetails.SINGLETON ),
				org.alice.ide.croquet.models.cascade.ExpressionBlank.getBlankForType( field.valueType.getValue().getComponentType() ) );
		this.transientValue = org.lgna.project.ast.AstUtilities.createFieldArrayAssignment( new org.lgna.project.ast.ThisExpression(), field, new EmptyExpression( org.lgna.project.ast.JavaType.INTEGER_OBJECT_TYPE ), new EmptyExpression( field.valueType.getValue().getComponentType() ) );
	}

	private org.lgna.project.ast.UserField getField() {
		org.lgna.project.ast.ArrayAccess arrayAccess = (org.lgna.project.ast.ArrayAccess)this.transientValue.leftHandSide.getValue();
		org.lgna.project.ast.FieldAccess fieldAccess = (org.lgna.project.ast.FieldAccess)arrayAccess.array.getValue();
		return (org.lgna.project.ast.UserField)fieldAccess.field.getValue();
	}

	@Override
	protected org.lgna.project.ast.AssignmentExpression createValue( org.lgna.project.ast.Expression[] expressions ) {
		return org.lgna.project.ast.AstUtilities.createFieldArrayAssignment( this.getField(), expressions[ 0 ], expressions[ 1 ] );
	}

	@Override
	public org.lgna.project.ast.AssignmentExpression getTransientValue( org.lgna.croquet.imp.cascade.ItemNode<? super org.lgna.project.ast.AssignmentExpression, org.lgna.project.ast.Expression> node ) {
		return this.transientValue;
	}
}
