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

package edu.cmu.cs.dennisc.croquet;

/**
 * @author Dennis Cosgrove
 */
public abstract class Component<J extends javax.swing.JComponent> {
	private static java.util.Map< java.awt.Component, Component< ? > > map = edu.cmu.cs.dennisc.java.util.Collections.newWeakHashMap();
	protected static Component< ? > lookup( java.awt.Component component ) {
		if( component != null ) {
			return Component.map.get( component );
		} else {
			return null;
		}
	}
	
	private J jComponent;
	protected abstract J createJComponent();

	//todo: reduce visibility
	public final J getJComponent() {
		if( this.jComponent != null ) {
			//pass
		} else {
			this.jComponent = this.createJComponent();
		}
		return this.jComponent;
	}

	public java.util.Locale getLocale() {
		return this.getJComponent().getLocale();
	}
	public java.awt.Font getFont() {
		return this.getJComponent().getFont();
	}
	public void scaleFont( float scaleFactor ) {
		edu.cmu.cs.dennisc.java.awt.FontUtilities.setFontToScaledFont( this.getJComponent(), scaleFactor );
	}
	public void setFontSize( float fontSize ) {
		this.getFont().deriveFont( fontSize );
	}
	public void changeFont( edu.cmu.cs.dennisc.java.awt.font.TextAttribute< ? >... textAttributes ) {
		edu.cmu.cs.dennisc.java.awt.FontUtilities.setFontToDerivedFont( this.getJComponent(), textAttributes );
	}
	
	
	public java.awt.Color getForegroundColor() {
		return this.getJComponent().getForeground();
	}
	public void setForegroundColor( java.awt.Color color ) {
		this.getJComponent().setForeground( color );
	}
	public java.awt.Color getBackgroundColor() {
		return this.getJComponent().getBackground();
	}
	public void setBackgroundColor( java.awt.Color color ) {
		this.getJComponent().setBackground( color );
	}
	public boolean isVisible() {
		return this.getJComponent().isVisible();
	}
	public void setVisible( boolean isVisible ) {
		this.getJComponent().setVisible( isVisible );
	}
	public boolean isEnabled() {
		return this.getJComponent().isEnabled();
	}
	public void setEnabled( boolean isEnabled ) {
		this.getJComponent().setEnabled( isEnabled );
	}
	public boolean isOpaque() {
		return this.getJComponent().isOpaque();
	}
	public void setOpaque( boolean isOpaque ) {
		this.getJComponent().setOpaque( isOpaque );
	}
	public boolean getIgnoreRepaint() {
		return this.getJComponent().getIgnoreRepaint();
	}
	public void setIgnoreRepaint( boolean ignoreRepaint ) {
		this.getJComponent().setIgnoreRepaint( ignoreRepaint );
	}

	public String getToolTipText() {
		return this.getJComponent().getToolTipText();
	}
	public void setToolTipText( String toolTipText ) {
		this.getJComponent().setToolTipText( toolTipText );
	}
	public javax.swing.border.Border getBorder() {
		return this.getJComponent().getBorder();
	}
	public void setBorder( javax.swing.border.Border border ) {
		this.getJComponent().setBorder( border );
	}
	public java.awt.Cursor getCursor() {
		return this.getJComponent().getCursor();
	}
	public void setCursor( java.awt.Cursor cursor ) {
		this.getJComponent().setCursor( cursor );
	}

	public float getAlignmentX() {
		return this.getJComponent().getAlignmentX();
	}
	public void setAlignmentX( float alignmentX ) {
		this.getJComponent().setAlignmentX( alignmentX );
	}
	public float getAlignmentY() {
		return this.getJComponent().getAlignmentY();
	}
	public void setAlignmentY( float alignmentY ) {
		this.getJComponent().setAlignmentY( alignmentY );
	}

	private void scrollRectToVisible( java.awt.Rectangle rect ) {
		this.getJComponent().scrollRectToVisible( rect );
	}
	public void scrollToVisible() {
		this.scrollRectToVisible( javax.swing.SwingUtilities.getLocalBounds( this.getJComponent() ) );
	}

	public int getX() {
		return this.getJComponent().getX();
	}
	public int getY() {
		return this.getJComponent().getY();
	}
	public java.awt.Point getLocationOnScreen() {
		return this.getJComponent().getLocationOnScreen();
	}
	public java.awt.Insets getInsets() {
		return this.getJComponent().getInsets();
	}
	public java.awt.Rectangle getBounds() {
		return this.getJComponent().getBounds();
	}
	
	public <T extends Component<?>> java.util.List< T > findAllMatches( Class<T> cls, edu.cmu.cs.dennisc.pattern.Criterion< T > criterion ) {
		edu.cmu.cs.dennisc.print.PrintUtilities.println( "todo: findAllMatches" );
		return java.util.Collections.emptyList();
	}
	public <T extends Component<?>> java.util.List< T > findAllMatches( Class<T> cls ) {
		return this.findAllMatches( cls, null );
	}
	
	@Deprecated
	public java.awt.Point convertPoint( java.awt.Point pt, java.awt.Component destination ) {
		return javax.swing.SwingUtilities.convertPoint( this.getJComponent(), pt, destination );
	}
	public java.awt.Point convertPoint( java.awt.Point pt, Component< ? > destination ) {
		return javax.swing.SwingUtilities.convertPoint( this.getJComponent(), pt, destination.getJComponent() );
	}
	@Deprecated
	public java.awt.Rectangle convertRectangle( java.awt.Rectangle rectangle, java.awt.Component destination ) {
		return javax.swing.SwingUtilities.convertRectangle( this.getJComponent(), rectangle, destination );
	}
	public java.awt.Rectangle convertRectangle( java.awt.Rectangle rectangle, Component< ? > destination ) {
		return javax.swing.SwingUtilities.convertRectangle( this.getJComponent(), rectangle, destination.getJComponent() );
	}
	@Deprecated
	public java.awt.event.MouseEvent convertMouseEvent( java.awt.event.MouseEvent e, java.awt.Component destination ) {
		return edu.cmu.cs.dennisc.javax.swing.SwingUtilities.convertMouseEvent( this.getJComponent(), e, destination );
	}
	public java.awt.event.MouseEvent convertMouseEvent( java.awt.event.MouseEvent e, Component< ? > destination ) {
		return edu.cmu.cs.dennisc.javax.swing.SwingUtilities.convertMouseEvent( this.getJComponent(), e, destination.getJComponent() );
	}
	
	public boolean isAncestorOf( Component< ? > other ) {
		return this.getJComponent().isAncestorOf( other.getJComponent() );
	}
	
	@Deprecated
	public Component< ? > getParent() {
		return Component.lookup( this.getJComponent().getParent() );
	}
	@Deprecated
	public Component<?> getComponent( int i ) {
		return Component.lookup( this.getJComponent().getComponent( i ) );
	}
	@Deprecated
	public Component<?>[] getComponents() {
		java.awt.Component[] components = this.getJComponent().getComponents();
		final int N = components.length;
		Component< ? >[] rv = new Component< ? >[ N ];
		for( int i=0; i<N; i++ ) {
			rv[ i ] = Component.lookup( components[ i ] );
		}
		return rv;
	}
	public int getComponentCount() {
		return this.getJComponent().getComponentCount();
	}

	protected void adding() {
	}
	protected void added() {
	}
	protected void removing() {
	}
	protected void removed() {
	}
	
	@Deprecated
	protected void forgetAndRemoveAllComponents() {
		edu.cmu.cs.dennisc.print.PrintUtilities.println( "todo: forgetAndRemoveAllComponents" );
		//throw new RuntimeException( "todo" );
	}
	
	public int getWidth() {
		return this.getJComponent().getWidth();
	}
	public int getHeight() {
		return this.getJComponent().getHeight();
	}
	
	protected void repaint() {
		this.getJComponent().repaint();
	}
	private void revalidate() {
		this.getJComponent().revalidate();
	}
	public void revalidateAndRepaint() {
		this.revalidate();
		this.repaint();
	}
	
	@Deprecated
	public void addMouseListener( java.awt.event.MouseListener listener ) {
		this.getJComponent().addMouseListener( listener );
	}
	@Deprecated
	public void removeMouseListener( java.awt.event.MouseListener listener ) {
		this.getJComponent().removeMouseListener( listener );
	}
	@Deprecated
	public void addMouseMotionListener( java.awt.event.MouseMotionListener listener ) {
		this.getJComponent().addMouseMotionListener( listener );
	}
	@Deprecated
	public void removeMouseMotionListener( java.awt.event.MouseMotionListener listener ) {
		this.getJComponent().removeMouseMotionListener( listener );
	}
	@Deprecated
	public void addMouseWheelListener( java.awt.event.MouseWheelListener listener ) {
		this.getJComponent().addMouseWheelListener( listener );
	}
	@Deprecated
	public void removeMouseWheelListener( java.awt.event.MouseWheelListener listener ) {
		this.getJComponent().removeMouseWheelListener( listener );
	}
	
	
	@Deprecated 
	public void setPreferredSize( java.awt.Dimension preferredSize ) {
		this.getJComponent().setPreferredSize( preferredSize );
	}

	protected void internalAddComponent( Component<?> component ) {
		assert component != null;
		component.adding();
		this.getJComponent().add( component.getJComponent() );
		component.added();
	}
	protected void internalAddComponent( Component<?> component, Object constraints ) {
		assert component != null;
		component.adding();
		this.getJComponent().add( component.getJComponent(), constraints );
		component.added();
	}
	protected void internalRemoveComponent( Component<?> component ) {
		assert component != null;
		component.removing();
		this.getJComponent().remove( component.getJComponent() );
		component.removed();
	}
}

