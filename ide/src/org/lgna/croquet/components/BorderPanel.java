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

package org.lgna.croquet.components;

/**
 * @author Dennis Cosgrove
 */
public class BorderPanel extends Panel {
	public enum Constraint {
		CENTER( java.awt.BorderLayout.CENTER ),
		
		NORTH( java.awt.BorderLayout.NORTH ),
		PAGE_START( java.awt.BorderLayout.PAGE_START ),
		
		SOUTH( java.awt.BorderLayout.SOUTH ),
		PAGE_END( java.awt.BorderLayout.PAGE_END ),
		
		WEST( java.awt.BorderLayout.WEST ),
		LINE_START( java.awt.BorderLayout.LINE_START ),

		EAST( java.awt.BorderLayout.EAST ),
		LINE_END( java.awt.BorderLayout.LINE_END );
		
		private String internal;
		private Constraint( String internal ) {
			this.internal = internal;
		}
		//todo: reduce visibility? /*package-private*/
		public String getInternal() {
			return this.internal;
		}
	}
	private final int hgap;
	private final int vgap;
	public BorderPanel() {
		this( null );
	}
	public BorderPanel( int hgap, int vgap ) {
		this( null, hgap, vgap );
	}
	public BorderPanel( org.lgna.croquet.Composite composite ) {
		this( composite, 0, 0 );
	}
	public BorderPanel( org.lgna.croquet.Composite composite, int hgap, int vgap ) {
		super( composite );
		this.hgap = hgap;
		this.vgap = vgap;
	}
	@Override
	protected final java.awt.LayoutManager createLayoutManager( javax.swing.JPanel jPanel ) {
		return new java.awt.BorderLayout( this.hgap, this.vgap );
	}
	public void addComponent( Component<?> child, Constraint constraint ) {
		this.internalAddComponent( child, constraint.internal );
	}
}
