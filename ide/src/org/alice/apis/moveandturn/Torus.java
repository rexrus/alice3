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
package org.alice.apis.moveandturn;

/**
 * @author Dennis Cosgrove
 */
public class Torus extends Model {
	private edu.cmu.cs.dennisc.scenegraph.Torus m_sgTorus = new edu.cmu.cs.dennisc.scenegraph.Torus();

	@Override
	protected void createSGGeometryIfNecessary() {
	}
	@Override
	protected edu.cmu.cs.dennisc.scenegraph.Geometry getSGGeometry() {
		return m_sgTorus;
	}
	public Double getMajorRadius() {
		return m_sgTorus.majorRadius.getValue();
	}
	public void setMajorRadius( Number majorRadius, Number duration, Style style ) {
		perform( new edu.cmu.cs.dennisc.animation.interpolation.DoubleAnimation( adjustDurationIfNecessary( duration ), style, getMajorRadius(), majorRadius.doubleValue() ) {
			@Override
			protected void updateValue( Double d ) {
				m_sgTorus.majorRadius.setValue( d );
			}
		} );
	}
	public void setMajorRadius( Number majorRadius, Number duration ) {
		setMajorRadius( majorRadius, duration, DEFAULT_STYLE );
	}
	public void setMajorRadius( Number majorRadius ) {
		setMajorRadius( majorRadius, DEFAULT_DURATION );
	}

	public Double getMinorRadius() {
		return m_sgTorus.minorRadius.getValue();
	}
	public void setMinorRadius( Number minorRadius, Number duration, Style style ) {
		perform( new edu.cmu.cs.dennisc.animation.interpolation.DoubleAnimation( adjustDurationIfNecessary( duration ), style, getMinorRadius(), minorRadius.doubleValue() ) {
			@Override
			protected void updateValue( Double d ) {
				m_sgTorus.minorRadius.setValue( d );
			}
		} );
	}
	public void setMinorRadius( Number minorRadius, Number duration ) {
		setMinorRadius( minorRadius, duration, DEFAULT_STYLE );
	}
	public void setMinorRadius( Number minorRadius ) {
		setMinorRadius( minorRadius, DEFAULT_DURATION );
	}
}
