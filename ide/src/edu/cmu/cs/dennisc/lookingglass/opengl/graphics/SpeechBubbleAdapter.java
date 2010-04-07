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
package edu.cmu.cs.dennisc.lookingglass.opengl.graphics;

public class SpeechBubbleAdapter extends BubbleAdapter< edu.cmu.cs.dennisc.scenegraph.graphics.SpeechBubble > {
//	protected abstract java.awt.Stroke getStroke();
	
	private static double sine( double t, double theta0, double theta1 ) {
		double theta = theta0 + t * ( theta1 - theta0 ); 
		return Math.sin( theta );
	}
	
	private java.awt.geom.Area getPortionOfPath( java.awt.geom.GeneralPath path, double portion ) {
		java.awt.geom.Area area = new java.awt.geom.Area( path );
		java.awt.geom.Rectangle2D bounds = area.getBounds2D();
		double maskBottom = bounds.getY() + bounds.getHeight();

		double stylizedPathPortion = sine( portion, -Math.PI/2, 0 ) + 1;
		stylizedPathPortion *= stylizedPathPortion;
		double maskHeight = bounds.getHeight() * stylizedPathPortion;

		java.awt.geom.Rectangle2D mask = new java.awt.geom.Rectangle2D.Double( bounds.getX(), maskBottom-maskHeight, bounds.getWidth(), maskHeight );
		area.intersect(  new java.awt.geom.Area( mask ) );
		return area;
	}
	

	@Override
	protected void render( 
			edu.cmu.cs.dennisc.lookingglass.Graphics2D g2, 
			edu.cmu.cs.dennisc.lookingglass.LookingGlass lookingGlass, 
			java.awt.Rectangle actualViewport, 
			edu.cmu.cs.dennisc.scenegraph.AbstractCamera camera, 
			edu.cmu.cs.dennisc.java.awt.MultilineText multilineText, 
			java.awt.Font font, 
			java.awt.Color textColor, 
			float wrapWidth,
			java.awt.Color fillColor, 
			java.awt.Color outlineColor,
			java.awt.geom.Point2D.Float originOfTail,
			java.awt.geom.Point2D.Float bodyConnectionLocationOfTail,
			java.awt.geom.Point2D.Float textBoundsOffset,
			double portion ) {
		assert originOfTail != null;
		assert bodyConnectionLocationOfTail != null;
		
		g2.setFont( font );
		java.awt.geom.Dimension2D textSize = multilineText.getDimension( g2, wrapWidth );
		java.awt.geom.Rectangle2D textBounds = new java.awt.geom.Rectangle2D.Double( 
				textBoundsOffset.x, 
				textBoundsOffset.y, 
				textSize.getWidth(), 
				textSize.getHeight() );

		
//		java.awt.Stroke stroke = g2.getStroke();
//		g2.setStroke( getStroke() );
		g2.setFont( font );
		
		float targetX = (float)bodyConnectionLocationOfTail.getX();
		float targetY = (float)bodyConnectionLocationOfTail.getY();

		float originX = (float)originOfTail.getX();
		float originY = (float)originOfTail.getY();
		
		float controlX = targetX;
		//float controlY = Math.max( originY, targetY+320 );
		float controlY = originY;
		float topOffsetX = 6f;
		float topOffsetY = -2f;

		java.awt.geom.GeneralPath path = new java.awt.geom.GeneralPath();
		path.moveTo( originX, originY );
		path.quadTo( controlX, controlY, targetX + topOffsetX, targetY + topOffsetY );
		path.lineTo( targetX - topOffsetX, targetY + topOffsetY );
		path.quadTo( controlX, controlY, originX, originY );
		path.closePath();

		final double IPAD = font.getSize2D()*0.333f;
		//edu.cmu.cs.dennisc.print.PrintUtilities.println( "IPAD:", IPAD );
		java.awt.geom.RoundRectangle2D.Double roundRect = new java.awt.geom.RoundRectangle2D.Double();
		roundRect.x = textBounds.getX() + bodyConnectionLocationOfTail.getX() + textBoundsOffset.getX() - IPAD;
		roundRect.y = textBounds.getY() + bodyConnectionLocationOfTail.getY() + textBoundsOffset.getY() - textBounds.getHeight() - IPAD;
		roundRect.width =  textBounds.getWidth() + IPAD + IPAD;
		roundRect.height =  textBounds.getHeight() + IPAD + IPAD;
		roundRect.arcwidth = IPAD;
		roundRect.archeight = IPAD;

		java.awt.geom.Area area;
		if( portion < 1.0 ) {
			area = getPortionOfPath( path, portion );
		} else {
			area = new java.awt.geom.Area( path );
			area.add( new java.awt.geom.Area( roundRect ) );
		}

		assert area != null;
		g2.setTransform( new java.awt.geom.AffineTransform() );
		
		g2.setColor( fillColor );
		g2.fill( area );
		g2.setColor( outlineColor );
		g2.draw( area );
		
		if( portion < 1.0 ) {
			//pass
		} else {
			g2.setFont( font );
			
			double xT = bodyConnectionLocationOfTail.getX();
			double yT = bodyConnectionLocationOfTail.getY() - textBounds.getHeight();
			g2.translate( xT, yT );
//			g2.setPaint( java.awt.Color.RED );
//			g2.draw( textBounds );
//			g2.setPaint( java.awt.Color.BLACK );
			multilineText.paint( g2, wrapWidth, edu.cmu.cs.dennisc.java.awt.TextAlignment.LEADING, textBounds );
			g2.translate( -xT, -yT );
//			int xPixel = (int)( bodyConnectionLocationOfTail.getX() + textBoundsOffset.getX() );
//			int yPixel = (int)( bodyConnectionLocationOfTail.getY() + textBoundsOffset.getY() - 10 );
//			g2.setColor( textColor );
//			g2.drawString( text, xPixel, yPixel );
		}

//		g2.setStroke( stroke );
	}
}
