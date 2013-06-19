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
package org.alice.ide.croquet.models.cascade;

/**
 * @author Dennis Cosgrove
 */
public final class TypeExpressionCascadeMenu extends ExpressionCascadeMenu<org.lgna.project.ast.Expression> {
	private static edu.cmu.cs.dennisc.java.util.InitializingIfAbsentMap<org.lgna.project.ast.AbstractType, TypeExpressionCascadeMenu> map = edu.cmu.cs.dennisc.java.util.Collections.newInitializingIfAbsentHashMap();

	public static TypeExpressionCascadeMenu getInstance( org.lgna.project.ast.AbstractType type ) {
		return map.getInitializingIfAbsent( type, new edu.cmu.cs.dennisc.java.util.InitializingIfAbsentMap.Initializer<org.lgna.project.ast.AbstractType, TypeExpressionCascadeMenu>() {
			public TypeExpressionCascadeMenu initialize( org.lgna.project.ast.AbstractType key ) {
				return new TypeExpressionCascadeMenu( key, null );
			}
		} );
	}

	public static TypeExpressionCascadeMenu getInstance( Class<?> cls ) {
		return getInstance( org.lgna.project.ast.JavaType.getInstance( cls ) );
	}

	private final org.lgna.project.ast.AbstractType<?, ?, ?> valueType;
	private final org.lgna.project.annotations.ValueDetails<?> details;

	private TypeExpressionCascadeMenu( org.lgna.project.ast.AbstractType<?, ?, ?> valueType, org.lgna.project.annotations.ValueDetails<?> details ) {
		super( java.util.UUID.fromString( "abafdc1c-7e12-4db4-94b2-17120c6a7110" ) );
		this.valueType = valueType;
		this.details = details;
	}

	@Override
	protected java.util.List<org.lgna.croquet.CascadeBlankChild> updateBlankChildren( java.util.List<org.lgna.croquet.CascadeBlankChild> rv, org.lgna.croquet.cascade.BlankNode<org.lgna.project.ast.Expression> blankNode ) {
		org.alice.ide.IDE ide = org.alice.ide.IDE.getActiveInstance();
		if( ide != null ) {
			ide.getExpressionCascadeManager().appendItems( rv, blankNode, this.valueType, this.details );
		}
		return rv;
	}

	@Override
	public javax.swing.Icon getMenuItemIcon( org.lgna.croquet.cascade.ItemNode<? super org.lgna.project.ast.Expression, org.lgna.project.ast.Expression> node ) {
		return org.alice.ide.common.TypeIcon.getInstance( this.valueType );
	}

}
