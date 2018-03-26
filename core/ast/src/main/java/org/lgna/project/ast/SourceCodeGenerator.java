/*******************************************************************************
 * Copyright (c) 2018 Carnegie Mellon University. All rights reserved.
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
 *******************************************************************************/
package org.lgna.project.ast;

import org.lgna.project.code.CodeOrganizer;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;

public abstract class SourceCodeGenerator {

	public SourceCodeGenerator( Map<String, CodeOrganizer.CodeOrganizerDefinition> codeOrganizerDefinitionMap,
															CodeOrganizer.CodeOrganizerDefinition defaultCodeDefinitionOrganizer ) {
		codeOrganizerDefinitions = Collections.unmodifiableMap( codeOrganizerDefinitionMap );
		defaultCodeOrganizerDefn = defaultCodeDefinitionOrganizer;
	}

	StringBuilder getCodeStringBuilder() {
		return codeStringBuilder;
	}

	int pushStatementDisabled() {
		return statementDisabledCount++;
	}

	int popStatementDisabled() {
		return statementDisabledCount--;
	}

	boolean isLambdaSupported() {
		return true;
	}

	void appendBoolean( boolean b ) {
		codeStringBuilder.append( b );
	}

	void appendChar( char c ) {
		codeStringBuilder.append( c );
	}

	void appendSpace() {
		appendChar( ' ' );
	}

	void appendSemicolon() {
		appendChar( ';' );
	}

	void appendString( String s ) {
		codeStringBuilder.append( s );
	}
	void appendExpression( Expression expression ) {
		expression.appendCode( this );
	}

	void appendAccessLevel( AccessLevel accessLevel ) {
		accessLevel.appendCode( this );
	}

	abstract void appendInt( int n );

	abstract void appendFloat( float f );

	abstract void appendDouble( double d );

	abstract void appendResourceExpression( ResourceExpression resourceExpression );

	abstract void appendTypeName( AbstractType<?, ?, ?> type );

	abstract void appendCallerExpression( Expression callerExpression, AbstractMethod method );

	AbstractType<?, ?, ?> peekTypeForLambda() {
		return typeForLambdaStack.peek();
	}

	void pushTypeForLambda( AbstractType<?, ?, ?> type ) {
		typeForLambdaStack.push( type );
	}

	AbstractType<?, ?, ?> popTypeForLambda() {
		return typeForLambdaStack.pop();
	}

	abstract void appendParameters( Code code );

	abstract void appendMethodHeader( AbstractMethod method );

	abstract void appendArguments( ArgumentOwner argumentOwner );

	@Deprecated
	abstract void todo( Object o );

	public String getText() {
		return String.valueOf( getCodeStringBuilder() );
	}

	abstract String getTextWithImports();

	CodeOrganizer getNewCodeOrganizerForTypeName( String typeName ) {
		return new CodeOrganizer( codeOrganizerDefinitions.getOrDefault( typeName, defaultCodeOrganizerDefn ) );
	}

	abstract protected String getMemberPrefix( AbstractMember member );

	abstract protected String getMemberPostfix( AbstractMember member );

	final void appendMemberPrefix( AbstractMember member ) {
		codeStringBuilder.append( getMemberPrefix( member ) );
	}

	final void appendMemberPostfix( AbstractMember member ) {
		codeStringBuilder.append( getMemberPostfix( member ) );
	}

	protected abstract String getSectionPrefix( AbstractType<?, ?, ?> declaringType, String sectionName,
																							boolean shouldCollapse );

	protected abstract String getSectionPostfix( AbstractType<?, ?, ?> declaringType, String sectionName,
																							 boolean shouldCollapse );

	final void appendSectionPrefix( AbstractType<?, ?, ?> declaringType, String sectionName, boolean shouldCollapse ) {
		codeStringBuilder.append( getSectionPrefix( declaringType, sectionName, shouldCollapse ) );
	}

	final void appendSectionPostfix( AbstractType<?, ?, ?> declaringType, String sectionName, boolean shouldCollapse ) {
		codeStringBuilder.append( getSectionPostfix( declaringType, sectionName, shouldCollapse ) );
	}

	private String getMethodPrefix( UserMethod method ) {
		return getMemberPrefix( method );
	}

	private String getMethodPostfix( UserMethod method ) {
		return getMemberPostfix( method );
	}

	final void appendMethodPrefix( UserMethod method ) {
		codeStringBuilder.append( getMethodPrefix( method ) );
	}

	final void appendMethodPostfix( UserMethod method ) {
		codeStringBuilder.append( getMethodPostfix( method ) );
	}

	private String getFieldPrefix( UserField field ) {
		return getMemberPrefix( field );
	}

	private String getFieldPostfix( UserField field ) {
		return getMemberPostfix( field );
	}

	final void appendFieldPrefix( UserField field ) {
		codeStringBuilder.append( getFieldPrefix( field ) );
	}

	final void appendFieldPostfix( UserField field ) {
		codeStringBuilder.append( getFieldPostfix( field ) );
	}

	abstract String localizedComment( AbstractType<?, ?, ?> type, String itemName, Locale locale );

	public String getLocalizedComment( AbstractType<?, ?, ?> type, String itemName, Locale locale ) {
		return localizedComment( type, itemName, locale );
	}

	private final StringBuilder codeStringBuilder = new StringBuilder();

	private final Stack<AbstractType<?, ?, ?>> typeForLambdaStack = new Stack<>();

	private int statementDisabledCount;

	private final Map<String, CodeOrganizer.CodeOrganizerDefinition> codeOrganizerDefinitions;
	private final CodeOrganizer.CodeOrganizerDefinition defaultCodeOrganizerDefn;
}
