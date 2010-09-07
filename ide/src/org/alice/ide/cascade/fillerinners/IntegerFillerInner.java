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
package org.alice.ide.cascade.fillerinners;

/**
 * @author Dennis Cosgrove
 */
public class IntegerFillerInner extends AbstractNumberFillerInner {
	private static final edu.cmu.cs.dennisc.alice.ast.TypeExpression INTEGER_UTILITIES_TYPE_EXPRESSION = org.alice.ide.ast.NodeUtilities.createTypeExpression( org.alice.integer.IntegerUtilities.class );
	public IntegerFillerInner() {
		super( edu.cmu.cs.dennisc.alice.ast.TypeDeclaredInJava.INTEGER_OBJECT_TYPE, edu.cmu.cs.dennisc.alice.ast.IntegerLiteral.class );
	}
	@Override
	public void addFillIns( edu.cmu.cs.dennisc.cascade.Blank blank ) {
		final edu.cmu.cs.dennisc.alice.ast.Expression previousExpression = org.alice.ide.IDE.getSingleton().getCascadeManager().createCopyOfPreviousExpression();
		final boolean isTop = blank.getParentFillIn() == null;
		if( isTop ) {
			if( previousExpression instanceof edu.cmu.cs.dennisc.alice.ast.ArithmeticInfixExpression ) {
				edu.cmu.cs.dennisc.alice.ast.ArithmeticInfixExpression previousArithmeticInfixExpression = (edu.cmu.cs.dennisc.alice.ast.ArithmeticInfixExpression)previousExpression;
				final edu.cmu.cs.dennisc.alice.ast.Expression leftOperand = previousArithmeticInfixExpression.leftOperand.getValue();
				edu.cmu.cs.dennisc.alice.ast.ArithmeticInfixExpression.Operator prevOperator = previousArithmeticInfixExpression.operator.getValue();
				final edu.cmu.cs.dennisc.alice.ast.Expression rightOperand = previousArithmeticInfixExpression.rightOperand.getValue();
				final edu.cmu.cs.dennisc.alice.ast.AbstractType<?,?,?> expressionType = previousArithmeticInfixExpression.expressionType.getValue();
				for( edu.cmu.cs.dennisc.alice.ast.ArithmeticInfixExpression.Operator operator : PRIME_TIME_INTEGER_ARITHMETIC_OPERATORS ) {
					if( operator != prevOperator ) {
						blank.addFillIn( new org.alice.ide.cascade.LabeledExpressionFillIn( new edu.cmu.cs.dennisc.alice.ast.ArithmeticInfixExpression( leftOperand, operator, rightOperand, expressionType ), "(replace operator)" ) );
					}
				}
				blank.addFillIn( new edu.cmu.cs.dennisc.cascade.MenuFillIn( "divide, remainder" ) {
					@Override
					protected void addChildrenToBlank( edu.cmu.cs.dennisc.cascade.Blank blank ) {
						for( edu.cmu.cs.dennisc.alice.ast.ArithmeticInfixExpression.Operator operator : TUCKED_AWAY_INTEGER_ARITHMETIC_OPERATORS ) {
							blank.addFillIn( new org.alice.ide.cascade.LabeledExpressionFillIn( new edu.cmu.cs.dennisc.alice.ast.ArithmeticInfixExpression( leftOperand, operator, rightOperand, expressionType ), "(replace operator)" ) );
						}
					}
				} );
				blank.addSeparator();
				blank.addFillIn( new org.alice.ide.cascade.LabeledExpressionFillIn( leftOperand, "(reduce to left operand only)" ) );
				blank.addFillIn( new org.alice.ide.cascade.LabeledExpressionFillIn( rightOperand, "(reduce to right operand only)" ) );
				blank.addSeparator();
			}
		}
		
		for( int i=0; i<4; i++ ) {
			blank.addFillIn( new IntegerLiteralFillIn( i ) );
			//this.addExpressionFillIn( blank, i );
		}
		blank.addSeparator();
		if( isTop ) {
			blank.addFillIn( new edu.cmu.cs.dennisc.cascade.MenuFillIn( "Random" ) {
				@Override
				protected void addChildrenToBlank(edu.cmu.cs.dennisc.cascade.Blank blank) {
					addNodeChildForMethod( blank, RANDOM_UTILITIES_TYPE_EXPRESSION, "nextIntegerFrom0ToNExclusive", Integer.class );
					addNodeChildForMethod( blank, RANDOM_UTILITIES_TYPE_EXPRESSION, "nextIntegerFromAToBExclusive", Integer.class, Integer.class );
					addNodeChildForMethod( blank, RANDOM_UTILITIES_TYPE_EXPRESSION, "nextIntegerFromAToBInclusive", Integer.class, Integer.class );
				}
			} );
			blank.addSeparator();
			blank.addFillIn( new edu.cmu.cs.dennisc.cascade.MenuFillIn( "Real Number" ) {
				@Override
				protected void addChildrenToBlank(edu.cmu.cs.dennisc.cascade.Blank blank) {
					addNodeChildForMethod( blank, INTEGER_UTILITIES_TYPE_EXPRESSION, "toFlooredInteger", Double.class );
					addNodeChildForMethod( blank, INTEGER_UTILITIES_TYPE_EXPRESSION, "toRoundedInteger", Double.class );
					addNodeChildForMethod( blank, INTEGER_UTILITIES_TYPE_EXPRESSION, "toCeilingedInteger", Double.class );
				}
			} );
			blank.addSeparator();
			blank.addFillIn( new edu.cmu.cs.dennisc.cascade.MenuFillIn( "Math" ) {
				@Override
				protected void addChildrenToBlank(edu.cmu.cs.dennisc.cascade.Blank blank) {
					if( previousExpression != null ) {
						for( edu.cmu.cs.dennisc.alice.ast.ArithmeticInfixExpression.Operator operator : PRIME_TIME_INTEGER_ARITHMETIC_OPERATORS ) {
							blank.addFillIn( new org.alice.ide.cascade.MostlyDeterminedArithmeticInfixExpressionFillIn( previousExpression, operator, Integer.class, Integer.class ) );
						}
						blank.addFillIn( new edu.cmu.cs.dennisc.cascade.MenuFillIn( "divide, remainder" ) {
							@Override
							protected void addChildrenToBlank( edu.cmu.cs.dennisc.cascade.Blank blank ) {
								for( edu.cmu.cs.dennisc.alice.ast.ArithmeticInfixExpression.Operator operator : TUCKED_AWAY_INTEGER_ARITHMETIC_OPERATORS ) {
									blank.addFillIn( new org.alice.ide.cascade.MostlyDeterminedArithmeticInfixExpressionFillIn( previousExpression, operator, Integer.class, Integer.class ) );
								}
							}
						} );
						blank.addSeparator();
					}
					for( edu.cmu.cs.dennisc.alice.ast.ArithmeticInfixExpression.Operator operator : PRIME_TIME_INTEGER_ARITHMETIC_OPERATORS ) {
						blank.addFillIn( new org.alice.ide.cascade.IncompleteArithmeticExpressionFillIn( edu.cmu.cs.dennisc.alice.ast.TypeDeclaredInJava.INTEGER_OBJECT_TYPE, operator, edu.cmu.cs.dennisc.alice.ast.TypeDeclaredInJava.INTEGER_OBJECT_TYPE ) );
					}
					blank.addFillIn( new edu.cmu.cs.dennisc.cascade.MenuFillIn( "divide, remainder" ) {
						@Override
						protected void addChildrenToBlank( edu.cmu.cs.dennisc.cascade.Blank blank ) {
							for( edu.cmu.cs.dennisc.alice.ast.ArithmeticInfixExpression.Operator operator : TUCKED_AWAY_INTEGER_ARITHMETIC_OPERATORS ) {
								blank.addFillIn( new org.alice.ide.cascade.IncompleteArithmeticExpressionFillIn( edu.cmu.cs.dennisc.alice.ast.TypeDeclaredInJava.INTEGER_OBJECT_TYPE, operator, edu.cmu.cs.dennisc.alice.ast.TypeDeclaredInJava.INTEGER_OBJECT_TYPE ) );
							}
						}
					} );
					blank.addSeparator();
					blank.addSeparator();
					addNodeChildForMethod( blank, MATH_TYPE_EXPRESSION, "abs", Integer.TYPE );
					blank.addSeparator();
					addNodeChildForMethod( blank, MATH_TYPE_EXPRESSION, "min", Integer.TYPE, Integer.TYPE );
					addNodeChildForMethod( blank, MATH_TYPE_EXPRESSION, "max", Integer.TYPE, Integer.TYPE );
				}
			} );
		}
		blank.addSeparator();
		blank.addFillIn( new org.alice.ide.cascade.customfillin.CustomIntegerFillIn() );
	}
}
