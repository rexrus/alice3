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

package org.lgna.story;

import org.lgna.project.annotations.ValueTemplate;

/**
 * @author Dennis Cosgrove
 */
public final class Color implements Paint {
	public static final Color BLACK = new Color( edu.cmu.cs.dennisc.color.Color4f.BLACK );
	public static final Color BLUE = new Color( edu.cmu.cs.dennisc.color.Color4f.BLUE );
	public static final Color CYAN = new Color( edu.cmu.cs.dennisc.color.Color4f.CYAN );
	public static final Color DARK_GRAY = new Color( edu.cmu.cs.dennisc.color.Color4f.DARK_GRAY );
	public static final Color GRAY = new Color( edu.cmu.cs.dennisc.color.Color4f.GRAY );
	public static final Color GREEN = new Color( edu.cmu.cs.dennisc.color.Color4f.GREEN );
	public static final Color LIGHT_GRAY = new Color( edu.cmu.cs.dennisc.color.Color4f.LIGHT_GRAY );
	public static final Color MAGENTA = new Color( edu.cmu.cs.dennisc.color.Color4f.MAGENTA );
	public static final Color ORANGE = new Color( edu.cmu.cs.dennisc.color.Color4f.ORANGE );
	public static final Color PINK = new Color( edu.cmu.cs.dennisc.color.Color4f.PINK );
	public static final Color RED = new Color( edu.cmu.cs.dennisc.color.Color4f.RED );
	public static final Color WHITE = new Color( edu.cmu.cs.dennisc.color.Color4f.WHITE );
	public static final Color YELLOW = new Color( edu.cmu.cs.dennisc.color.Color4f.YELLOW );
	public static final Color LIGHT_BLUE = new Color( 149.0/255.0, 166.0/255.0, 216.0/255.0 );
	public static final Color DARK_BLUE = new Color( 0/255.0, 0/255.0, 150.0/255.0 );
	
	public static final Color PURPLE = new Color( edu.cmu.cs.dennisc.color.Color4f.PURPLE );
	public static final Color BROWN = new Color( edu.cmu.cs.dennisc.color.Color4f.BROWN );

	private final edu.cmu.cs.dennisc.color.Color4f internal;
	public Color( 
			@ValueTemplate(detailsEnumCls = org.lgna.story.annotation.PortionDetails.class)Number red, 
			@ValueTemplate(detailsEnumCls = org.lgna.story.annotation.PortionDetails.class)Number green, 
			@ValueTemplate(detailsEnumCls = org.lgna.story.annotation.PortionDetails.class)Number blue ) {
		this( new edu.cmu.cs.dennisc.color.Color4f( red.floatValue(), green.floatValue(), blue.floatValue(), 1.0f ) );
	}
	private Color( edu.cmu.cs.dennisc.color.Color4f internal ) {
		this.internal = internal;
	}
	/*package-private*/ static Color createInstance( edu.cmu.cs.dennisc.color.Color4f internal ) {
		return internal != null ? new Color( internal ) : null;
	}
	/*package-private*/ edu.cmu.cs.dennisc.color.Color4f getInternal() {
		return this.internal;
	}
	/*package-private*/ static edu.cmu.cs.dennisc.color.Color4f getInternal( Color color ) {
		return color != null ? color.internal : null;
	}
	
	
	@Override
	public boolean equals( Object obj ) {
		if( obj instanceof Color ) {
			Color other = (Color)obj;
			return this.internal.equals( other.internal );
		} else {
			return false;
		}
	}
	@Override
	public int hashCode() {
		return this.internal.hashCode();
	}

	public Double getRed() {
		return (double)this.internal.red;
	}
	public Double getGreen() {
		return (double)this.internal.green;
	}
	public Double getBlue() {
		return (double)this.internal.blue;
	}
}
