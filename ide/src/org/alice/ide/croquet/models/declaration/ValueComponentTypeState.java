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

package org.alice.ide.croquet.models.declaration;

/**
 * @author Dennis Cosgrove
 */
public class ValueComponentTypeState extends org.lgna.croquet.DefaultCustomItemState< edu.cmu.cs.dennisc.alice.ast.AbstractType > {
	private final DeclarationOperation<?> owner;
	public ValueComponentTypeState( DeclarationOperation<?> owner, edu.cmu.cs.dennisc.alice.ast.AbstractType<?,?,?> initialValue ) {
		super( org.lgna.croquet.Application.INHERIT_GROUP, java.util.UUID.fromString( "7b2413e0-a945-49d1-800b-4fba4f0bc741" ), org.alice.ide.croquet.codecs.NodeCodec.getInstance( edu.cmu.cs.dennisc.alice.ast.AbstractType.class ), initialValue );
		this.owner = owner;
	}
	@Override
	protected java.util.List< org.lgna.croquet.CascadeBlankChild > updateBlankChildren( java.util.List< org.lgna.croquet.CascadeBlankChild > rv, org.lgna.croquet.cascade.BlankNode< edu.cmu.cs.dennisc.alice.ast.AbstractType > blankNode ) {
		for( edu.cmu.cs.dennisc.alice.ast.TypeDeclaredInJava type : org.alice.ide.IDE.getActiveInstance().getPrimeTimeSelectableTypesDeclaredInJava() ) {
			rv.add( org.alice.ide.croquet.models.ast.declaration.TypeFillIn.getInstance( type ) );
		}
		rv.add( org.lgna.croquet.CascadeLineSeparator.getInstance() );
		edu.cmu.cs.dennisc.alice.Project project = org.alice.ide.IDE.getActiveInstance().getProject();
		java.util.List< edu.cmu.cs.dennisc.alice.ast.TypeDeclaredInAlice > types = edu.cmu.cs.dennisc.alice.project.ProjectUtilities.getTypes( project );
		for( edu.cmu.cs.dennisc.alice.ast.TypeDeclaredInAlice type : types ) {
			rv.add( org.alice.ide.croquet.models.ast.declaration.TypeFillIn.getInstance( type ) );
		}
		return rv;
	}
}

