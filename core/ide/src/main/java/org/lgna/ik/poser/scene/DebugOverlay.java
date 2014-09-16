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
package org.lgna.ik.poser.scene;

import java.awt.Color;
import java.awt.image.BufferedImage;

import edu.cmu.cs.dennisc.renderer.event.RenderTargetDisplayChangeEvent;
import edu.cmu.cs.dennisc.renderer.event.RenderTargetInitializeEvent;
import edu.cmu.cs.dennisc.renderer.event.RenderTargetListener;
import edu.cmu.cs.dennisc.renderer.event.RenderTargetRenderEvent;
import edu.cmu.cs.dennisc.renderer.event.RenderTargetResizeEvent;

/**
 * @author Matt May
 */
public class DebugOverlay implements RenderTargetListener {

	private static final int DEFAULT_ALPHA = 256 / 2;
	private final OverlayFunction function;
	private boolean isPreservingAlpha;

	public DebugOverlay( OverlayFunction overlayFunction, boolean isPreservingAlpha ) {
		this.function = overlayFunction;
		this.isPreservingAlpha = isPreservingAlpha;
	}

	public DebugOverlay( OverlayFunction overlayFunction ) {
		this( overlayFunction, false );
	}

	@Override
	public void initialized( RenderTargetInitializeEvent e ) {
	}

	@Override
	public void cleared( RenderTargetRenderEvent e ) {
	}

	@Override
	public void rendered( RenderTargetRenderEvent e ) {
		drawOverlay( e );
	}

	@Override
	public void resized( RenderTargetResizeEvent e ) {
	}

	@Override
	public void displayChanged( RenderTargetDisplayChangeEvent e ) {
	}

	private void drawOverlay( RenderTargetRenderEvent e ) {
		BufferedImage image = getImage( e );
		e.getGraphics2D().drawImage( image, 0, 0, null );
	}

	private BufferedImage getImage( RenderTargetRenderEvent e ) {
		int width = e.getTypedSource().getWidth();
		final int height = e.getTypedSource().getHeight();
		final BufferedImage rv = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );
		int bottom = width * height;
		int progress = 0;
		for( int x = 0; x != width; ++x ) {
			final int finX = x;
			for( int y = 0; y != height; ++y ) {
				Color c = function.getColorForXY( finX, y );
				if( !isPreservingAlpha ) {
					c = new Color( c.getRed(), c.getGreen(), c.getBlue(), DEFAULT_ALPHA );
				}
				++progress;
				rv.setRGB( finX, y, c.getRGB() );
				double percent = (double)progress / (double)bottom;
				System.out.println( percent );
				System.out.println( width * height );
			}
		}
		System.out.println( "done" );
		return rv;
	}

}
