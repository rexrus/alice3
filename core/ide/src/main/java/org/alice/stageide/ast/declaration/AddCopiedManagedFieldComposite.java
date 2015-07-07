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
package org.alice.stageide.ast.declaration;

import org.alice.ide.ast.declaration.AddManagedFieldComposite;

/**
 * @author user
 */
public class AddCopiedManagedFieldComposite extends AddManagedFieldComposite {
	private static class SingletonHolder {
		private static AddCopiedManagedFieldComposite instance = new AddCopiedManagedFieldComposite();
	}

	public static AddCopiedManagedFieldComposite getInstance() {
		return SingletonHolder.instance;
	}

	private static org.lgna.project.ast.AbstractType<?, ?, ?> getDeclaringTypeFromInitializer( org.lgna.project.ast.Expression expression ) {
		if( expression instanceof org.lgna.project.ast.InstanceCreation ) {
			org.lgna.project.ast.InstanceCreation instanceCreation = (org.lgna.project.ast.InstanceCreation)expression;
			return instanceCreation.constructor.getValue().getDeclaringType();
		} else {
			return null;
		}
	}

	private final org.lgna.croquet.event.ValueListener<org.lgna.project.ast.Expression> initializerListener = new org.lgna.croquet.event.ValueListener<org.lgna.project.ast.Expression>() {
		@Override
		public void valueChanged( org.lgna.croquet.event.ValueEvent<org.lgna.project.ast.Expression> e ) {
			AddCopiedManagedFieldComposite.this.handleInitializerChanged( e.getNextValue() );
		}
	};

	private org.lgna.project.ast.InstanceCreation initialInstanceCreation;
	private org.lgna.project.ast.UserField fieldToCopy = null;

	private AddCopiedManagedFieldComposite() {
		super( java.util.UUID.fromString( "a14a3088-185c-4dfd-983c-af05e1d8dc14" ), new FieldDetailsBuilder()
				.valueComponentType( ApplicabilityStatus.DISPLAYED, null )
				.valueIsArrayType( ApplicabilityStatus.APPLICABLE_BUT_NOT_DISPLAYED, false )
				.initializer( ApplicabilityStatus.EDITABLE, null )
				.build() );
		this.getInitializerState().addAndInvokeNewSchoolValueListener( initializerListener );
	}

	@Override
	protected EditCustomization customize( org.lgna.croquet.history.CompletionStep<?> step, org.lgna.project.ast.UserType<?> declaringType, org.lgna.project.ast.UserField field, EditCustomization rv ) {
		edu.cmu.cs.dennisc.math.AffineMatrix4x4 initialTransform = null;
		org.lgna.croquet.DropSite dropSite = step.findDropSite();
		if( dropSite instanceof org.alice.stageide.sceneeditor.draganddrop.SceneDropSite ) {
			org.alice.stageide.sceneeditor.draganddrop.SceneDropSite sceneDropSite = (org.alice.stageide.sceneeditor.draganddrop.SceneDropSite)dropSite;
			initialTransform = sceneDropSite.getTransform();
		} else {
			org.lgna.project.ast.AbstractType<?, ?, ?> type = field.getValueType();
			org.lgna.project.ast.JavaType javaType = type.getFirstEncounteredJavaType();
			Class<?> cls = javaType.getClassReflectionProxy().getReification();
			if( org.lgna.story.SModel.class.isAssignableFrom( cls ) ) {
				initialTransform = org.lgna.story.implementation.alice.AliceResourceUtilties.getDefaultInitialTransform( org.lgna.story.implementation.alice.AliceResourceClassUtilities.getResourceClassForModelClass( (Class<? extends org.lgna.story.SModel>)cls ) );
			}
			else {
				initialTransform = null;
			}
		}
		initialTransform = this.updateInitialTransformIfNecessary( initialTransform );
		org.alice.ide.sceneeditor.AbstractSceneEditor sceneEditor = org.alice.ide.IDE.getActiveInstance().getSceneEditor();
		org.lgna.project.ast.Statement[] doStatements = sceneEditor.getDoStatementsForAddField( field, initialTransform );
		for( org.lgna.project.ast.Statement s : doStatements ) {
			rv.addDoStatement( s );
		}
		org.lgna.project.ast.Statement[] undoStatements = sceneEditor.getUndoStatementsForAddField( field );
		for( org.lgna.project.ast.Statement s : undoStatements ) {
			rv.addUndoStatement( s );
		}
		for( org.lgna.croquet.CustomItemState<org.lgna.project.ast.Expression> initialPropertyValueExpressionState : this.initialPropertyValuesToolPaletteCoreComposite.getInitialPropertyValueExpressionStates() ) {
			InitialPropertyValueExpressionCustomizer customizer = (InitialPropertyValueExpressionCustomizer)( (InternalCustomItemState<org.lgna.project.ast.Expression>)initialPropertyValueExpressionState ).getCustomizer();
			customizer.appendDoStatements( rv, field, initialPropertyValueExpressionState.getValue() );
		}
		return rv;
	}

	private void setInitializerInitialValue( org.lgna.project.ast.InstanceCreation initialInstanceCreation ) {
		this.initialInstanceCreation = initialInstanceCreation;
	}

	public void setFieldToBeCopied( org.lgna.project.ast.UserField fieldToCopy ) {
		this.fieldToCopy = fieldToCopy;
		//		this.setInitializerInitialValue( resourceKey != null ? resourceKey.createInstanceCreation() : null );
	}

	@Override
	protected org.lgna.project.ast.Expression getInitializerInitialValue() {
		return this.initialInstanceCreation;
	}

	@Override
	protected org.lgna.project.ast.AbstractType<?, ?, ?> getValueComponentTypeInitialValue() {
		return getDeclaringTypeFromInitializer( this.getInitializer() );
	}

	private void handleInitializerChanged( org.lgna.project.ast.Expression nextValue ) {
		org.lgna.project.ast.AbstractType<?, ?, ?> type = getDeclaringTypeFromInitializer( nextValue );
		this.getValueComponentTypeState().setValueTransactionlessly( type );
		this.getNameState().setValueTransactionlessly( this.getNameInitialValue() );
		this.refreshStatus();
		final org.lgna.croquet.views.AbstractWindow<?> root = this.getView().getRoot();
		if( root != null ) {
			java.awt.Dimension preferredSize = root.getAwtComponent().getPreferredSize();
			java.awt.Dimension size = root.getSize();
			if( ( preferredSize.width > size.width ) || ( preferredSize.height > size.height ) ) {
				root.pack();
			}
		}
	}

	private class InitializerContext implements org.alice.ide.cascade.ExpressionCascadeContext {
		@Override
		public org.lgna.project.ast.Expression getPreviousExpression() {
			//todo: investigate
			//org.lgna.project.ast.UserField field = getPreviewValue();
			//return field.initializer.getValue();
			return getInitializer();
			//return org.alice.ide.IDE.getActiveInstance().createCopy( getInitializer() );
		}

		@Override
		public org.alice.ide.ast.draganddrop.BlockStatementIndexPair getBlockStatementIndexPair() {
			return null;
		}
	}

	private class ResourceKeyInitializerCustomizer implements ItemStateCustomizer<org.lgna.project.ast.Expression> {
		private org.alice.ide.cascade.ExpressionCascadeContext pushedContext;

		@Override
		public org.lgna.croquet.CascadeFillIn getFillInFor( org.lgna.project.ast.Expression value ) {
			return null;
		}

		@Override
		public void prologue( org.lgna.croquet.triggers.Trigger trigger ) {
			this.pushedContext = new InitializerContext();
			org.alice.ide.IDE.getActiveInstance().getExpressionCascadeManager().pushContext( this.pushedContext );
		}

		@Override
		public void epilogue() {
			org.alice.ide.IDE.getActiveInstance().getExpressionCascadeManager().popAndCheckContext( this.pushedContext );
			this.pushedContext = null;
		}

		@Override
		public void appendBlankChildren( java.util.List<org.lgna.croquet.CascadeBlankChild> blankChildren, org.lgna.croquet.imp.cascade.BlankNode<org.lgna.project.ast.Expression> blankNode ) {
			org.lgna.project.ast.Expression initializer = getInitializer();
			if( initializer instanceof org.lgna.project.ast.InstanceCreation ) {
				org.lgna.project.ast.InstanceCreation instanceCreation = (org.lgna.project.ast.InstanceCreation)initializer;
				org.lgna.project.ast.AbstractConstructor constructor = instanceCreation.constructor.getValue();
				blankChildren.add( org.alice.ide.croquet.models.declaration.InstanceCreationFillInWithGalleryResourceParameter.getInstance( constructor ) );
				blankChildren.add( org.lgna.croquet.CascadeLineSeparator.getInstance() );
			}
		}
	}

	@Override
	protected org.lgna.croquet.AbstractComposite.ItemStateCustomizer<org.lgna.project.ast.Expression> createInitializerCustomizer() {
		return new ResourceKeyInitializerCustomizer();
	}

	@Override
	protected void handlePostHideDialog( org.lgna.croquet.history.CompletionStep<?> completionStep ) {
		super.handlePostHideDialog( completionStep );
		this.initialInstanceCreation = null;
	}
}
