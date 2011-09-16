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

package org.alice.stageide;

import org.lgna.story.resourceutilities.StorytellingResources;

/**
 * @author Dennis Cosgrove
 */
public enum StoryApiConfigurationManager implements org.alice.ide.ApiConfigurationManager {
	SINGLETON;
	public boolean isDeclaringTypeForManagedFields( org.lgna.project.ast.UserType< ? > type ) {
		return type.isAssignableTo( org.lgna.story.Scene.class );
	}
	public boolean isInstanceFactoryDesiredForType( org.lgna.project.ast.AbstractType< ?, ?, ? > type ) {
		return type.isAssignableTo( org.lgna.story.Entity.class );
	}
	public java.util.List< org.lgna.project.ast.JavaType > getTopLevelGalleryTypes() {
		return StorytellingResources.getInstance().getTopLevelGalleryTypes();
	}
	public org.lgna.project.ast.AbstractType< ?, ?, ? > getGalleryResourceParentFor( org.lgna.project.ast.AbstractType< ?, ?, ? > type ) {
		return StorytellingResources.getInstance().getGalleryResourceParentFor( type );
	}
	public java.util.List< org.lgna.project.ast.AbstractDeclaration > getGalleryResourceChildrenFor( org.lgna.project.ast.AbstractType< ?, ?, ? > type ) {
		return StorytellingResources.getInstance().getGalleryResourceChildrenFor(type);
	}
	public org.lgna.croquet.CascadeMenuModel< org.alice.ide.instancefactory.InstanceFactory > getInstanceFactorySubMenuForThis() {
		return null;
	}
	
	public org.lgna.croquet.CascadeMenuModel< org.alice.ide.instancefactory.InstanceFactory > getInstanceFactorySubMenuForThisFieldAccess( org.lgna.project.ast.UserField field ) {
		org.lgna.project.ast.AbstractType< ?,?,? > type = field.getValueType();
		if( org.alice.stageide.instancefactory.JointedMenuModel.isJointed( type ) ) {
			return org.alice.stageide.instancefactory.JointedMenuModel.getInstance( field );
		} else {
			return null;
		}
	}

	public org.lgna.project.ast.AbstractConstructor getGalleryResourceConstructorFor( org.lgna.project.ast.AbstractType< ?, ?, ? > argumentType ) {
		java.util.List< org.lgna.project.ast.NamedUserType > types = org.alice.ide.typemanager.TypeManager.getNamedUserTypesFor( getTopLevelGalleryTypes() );
		for( org.lgna.project.ast.AbstractType< ?, ?, ? > type : types ) {
			org.lgna.project.ast.AbstractConstructor constructor = type.getDeclaredConstructors().get( 0 );
			java.util.ArrayList< ? extends org.lgna.project.ast.AbstractParameter > parameters = constructor.getParameters();
			if( parameters.size() == 1 ) {
				if( parameters.get( 0 ).getValueType().isAssignableFrom( argumentType ) ) {
					return constructor;
				}
			}
		}
		return null;
	}

	protected org.alice.ide.ast.components.DeclarationNameLabel createDeclarationNameLabel( org.lgna.project.ast.AbstractField field ) {
		//todo: better name
		class ThisFieldAccessNameLabel extends org.alice.ide.ast.components.DeclarationNameLabel {
			public ThisFieldAccessNameLabel( org.lgna.project.ast.AbstractField field ) {
				super( field );
			}
			@Override
			protected String getNameText() {
				if( org.alice.ide.croquet.models.ui.preferences.IsIncludingThisForFieldAccessesState.getInstance().getValue() ) {
					return "this." + super.getNameText();
				} else {
					return super.getNameText();
				}
			}
		}
		return new ThisFieldAccessNameLabel( field );
	}

	public org.lgna.croquet.components.JComponent< ? > createReplacementForFieldAccessIfAppropriate( org.lgna.project.ast.FieldAccess fieldAccess ) {
		org.lgna.project.ast.Expression fieldExpression = fieldAccess.expression.getValue();
		if( fieldExpression instanceof org.lgna.project.ast.ThisExpression || fieldExpression instanceof org.alice.ide.ast.CurrentThisExpression ) {
			org.lgna.project.ast.AbstractField field = fieldAccess.field.getValue();
			org.lgna.project.ast.AbstractType< ?,?,? > declaringType = field.getDeclaringType();
			if( declaringType != null && declaringType.isAssignableTo( org.lgna.story.Scene.class ) ) {
				if( field.getValueType().isAssignableTo( org.lgna.story.Entity.class ) ) {
					return this.createDeclarationNameLabel( field );
				}
			}
		}
		return null;
	}
	
}
