/*******************************************************************************
 * Copyright (c) 2006, 2015, Carnegie Mellon University. All rights reserved.
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
package org.alice.stageide.oneshot;

import edu.cmu.cs.dennisc.map.MapToMap;
import org.alice.ide.instancefactory.InstanceFactory;
import org.lgna.project.ast.Expression;
import org.lgna.project.ast.JavaMethod;
import org.lgna.project.ast.JavaType;

import java.util.UUID;

/**
 * @author Dennis Cosgrove
 */
public class SetOpacityMethodInvocationFillIn extends OneShotJavaMethodInvocationFillIn {
	private static MapToMap<InstanceFactory, JavaMethod, SetOpacityMethodInvocationFillIn> mapToMap = MapToMap.newInstance();

	public static SetOpacityMethodInvocationFillIn getInstance( InstanceFactory instanceFactory, JavaMethod method ) {
		return mapToMap.getInitializingIfAbsent( instanceFactory, method, new MapToMap.Initializer<InstanceFactory, JavaMethod, SetOpacityMethodInvocationFillIn>() {
			@Override
			public SetOpacityMethodInvocationFillIn initialize( InstanceFactory instanceFactory, JavaMethod method ) {
				return new SetOpacityMethodInvocationFillIn( instanceFactory, method );
			}
		} );
	}

	public static SetOpacityMethodInvocationFillIn getInstance( InstanceFactory instanceFactory, JavaType type, String methodName, Class<?>... parameterClses ) {
		JavaMethod method = type.getDeclaredMethod( methodName, parameterClses );
		assert method != null : methodName;
		return getInstance( instanceFactory, method );
	}

	public static SetOpacityMethodInvocationFillIn getInstance( InstanceFactory instanceFactory, Class<?> cls, String methodName, Class<?>... parameterClses ) {
		return getInstance( instanceFactory, JavaType.getInstance( cls ), methodName, parameterClses );
	}

	private SetOpacityMethodInvocationFillIn( InstanceFactory instanceFactory, JavaMethod method ) {
		super( UUID.fromString( "52f83a5b-9005-4d21-b6a0-3dd96211dc33" ), instanceFactory, method );
	}

	@Override
	protected MethodInvocationEditFactory createMethodInvocationEditFactory( InstanceFactory instanceFactory, JavaMethod method, Expression[] argumentExpressions ) {
		return new SetOpacityMethodInvocationEditFactory( instanceFactory, method, argumentExpressions );
	}
}
