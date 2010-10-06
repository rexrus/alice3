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
package org.alice.ide.croquet.models.custom;

import org.alice.ide.cascade.customfillin.CustomInputPane;

/**
 * @author Dennis Cosgrove
 */
public abstract class CustomInputDialogOperation<E extends edu.cmu.cs.dennisc.alice.ast.Expression> extends org.alice.ide.operations.InputDialogWithPreviewOperation<CustomInputPane< E >> {
	private CustomInputPane< E > customInputPane;
	
	public CustomInputDialogOperation( java.util.UUID id, org.alice.ide.choosers.ValueChooser< E > chooser ) {
		super( edu.cmu.cs.dennisc.croquet.Application.INHERIT_GROUP, id );
		this.customInputPane = new CustomInputPane< E >( chooser );
	}
	
	public void EPIC_HACK_setChooserTypeDescription( String typeDescription ) {
		this.customInputPane.getValueChooser().setTypeDescription( typeDescription );
	}
	@Override
	protected CustomInputPane< E > prologue(edu.cmu.cs.dennisc.croquet.InputDialogOperationContext<CustomInputPane< E >> context) {
		return this.customInputPane;
	}
	@Override
	protected void epilogue(edu.cmu.cs.dennisc.croquet.InputDialogOperationContext<CustomInputPane< E >> context, boolean isOk) {
		if( isOk ) {
			context.finish();
		} else {
			context.cancel();
		}
	}
	@Override
	public String getTutorialNoteText( edu.cmu.cs.dennisc.croquet.ModelContext< ? > modelContext, edu.cmu.cs.dennisc.croquet.UserInformation userInformation ) {
		StringBuilder sb = new StringBuilder();
		edu.cmu.cs.dennisc.croquet.SuccessfulCompletionEvent successfulCompletionEvent = modelContext.getSuccessfulCompletionEvent();
		if( successfulCompletionEvent != null ) {
			//org.alice.ide.croquet.edits.ast.DeclareMethodEdit declareMethodEdit = (org.alice.ide.croquet.edits.ast.DeclareMethodEdit)successfulCompletionEvent.getEdit();
			sb.append( "1) Enter " );
			sb.append( "<strong>" );
			sb.append( "fill_in_expression_value_here" );
			sb.append( "</strong>" );
			sb.append( "<br>" );
			sb.append( "2) Press <strong>OK</strong>." );
		}
		return sb.toString();
	}
	
}
