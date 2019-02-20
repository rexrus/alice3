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
package org.alice.stageide.run.views.icons;

import edu.cmu.cs.dennisc.java.awt.ColorUtilities;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * @author Dennis Cosgrove
 */
public class RunIcon implements Icon {
	//private static final java.awt.Color ENABLED_CIRCLE_COLOR = java.awt.Color.GREEN.darker();
	//private static final java.awt.Color DISABLED_CIRCLE_COLOR = java.awt.Color.GRAY;

	private static final Color ROLLOVER_COLOR = new Color( 191, 255, 191 );
	private static final Color PRESSED_COLOR = new Color( 63, 127, 63 );
	private static final Color ENABLED_COLOR = ColorUtilities.interpolate( ROLLOVER_COLOR, PRESSED_COLOR, 0.5f );
	private static final Color DISABLED_COLOR = Color.GRAY;

	@Override
	public int getIconHeight() {
		return 24;
	}

	@Override
	public int getIconWidth() {
		return 24;
	}

	@Override
	public void paintIcon( Component c, Graphics g, int x, int y ) {
		if( c instanceof AbstractButton ) {
			ButtonModel buttonModel = ( (AbstractButton)c ).getModel();
			Graphics2D g2 = (Graphics2D)g;
			Color prevColor = g2.getColor();
			Object prevAntialiasing = g2.getRenderingHint( RenderingHints.KEY_ANTIALIASING );
			try {
				g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
				int w = this.getIconWidth();
				int h = this.getIconHeight();
				int offset = w / 5;
				int x0 = x + ( offset * 2 );
				int x1 = ( x + w ) - offset;

				int y0 = y + offset;
				int y1 = ( y + h ) - offset;
				int yC = ( y0 + y1 ) / 2;

				int[] xs = { x0, x1, x0 };
				int[] ys = { y0, yC, y1 };

				if( buttonModel.isEnabled() ) {
					if( buttonModel.isPressed() ) {
						g2.setColor( PRESSED_COLOR );
					} else {
						if( buttonModel.isRollover() || buttonModel.isArmed() ) {
							g2.setColor( ROLLOVER_COLOR );
						} else {
							g2.setColor( ENABLED_COLOR );
						}
					}
				} else {
					g2.setColor( DISABLED_COLOR );
				}

				g2.fillPolygon( xs, ys, 3 );

				g2.setColor( Color.DARK_GRAY );
				g2.drawPolygon( xs, ys, 3 );
			} finally {
				g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, prevAntialiasing );
				g2.setColor( prevColor );
			}
		}
	}
}
