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
package org.alice.ide.operations.ast;

/**
 * @author Dennis Cosgrove
 */
public class DeclareMethodParameterOperation extends AbstractCodeOperation {
	private edu.cmu.cs.dennisc.alice.ast.MethodDeclaredInAlice method;

	public DeclareMethodParameterOperation( edu.cmu.cs.dennisc.alice.ast.MethodDeclaredInAlice method ) {
		this.method = method;
		this.setName( "Add Parameter..." );
	}
	@Override
	protected edu.cmu.cs.dennisc.alice.ast.CodeDeclaredInAlice getCode() {
		return this.method;
	}
	public void perform( edu.cmu.cs.dennisc.zoot.ActionContext actionContext ) {
		org.alice.ide.declarationpanes.CreateMethodParameterPane createMethodParameterPane = new org.alice.ide.declarationpanes.CreateMethodParameterPane( method, this.getIDE().getMethodInvocations( method ) );
		final edu.cmu.cs.dennisc.alice.ast.ParameterDeclaredInAlice parameter = createMethodParameterPane.showInJDialog( getIDE() );
		if( parameter != null ) {
			final int index = method.parameters.size();
			final java.util.Map< edu.cmu.cs.dennisc.alice.ast.MethodInvocation, edu.cmu.cs.dennisc.alice.ast.Argument > map = new java.util.HashMap< edu.cmu.cs.dennisc.alice.ast.MethodInvocation, edu.cmu.cs.dennisc.alice.ast.Argument >();
			actionContext.commitAndInvokeDo( new edu.cmu.cs.dennisc.zoot.AbstractEdit() {
				@Override
				public void doOrRedo( boolean isDo ) {
					org.alice.ide.ast.NodeUtilities.addParameter( map, method, parameter, index, getIDE().getMethodInvocations( method ) );
				}
				@Override
				public void undo() {
					org.alice.ide.ast.NodeUtilities.removeParameter( map, method, parameter, index, getIDE().getMethodInvocations( method ) );
				}
				@Override
				protected StringBuffer updatePresentation(StringBuffer rv, java.util.Locale locale) {
					rv.append( "declare:" );
					edu.cmu.cs.dennisc.alice.ast.Node.safeAppendRepr(rv, parameter, locale);
					return rv;
				}
			} );
		} else {
			actionContext.cancel();
		}
	}
}
