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
package test.video.views;

class PlayIcon implements javax.swing.Icon {
	public static void paint( java.awt.Component c, java.awt.Graphics g, javax.swing.ButtonModel buttonModel, java.awt.Stroke stroke, int x, int y, int width, int height ) {
		java.awt.Graphics2D g2 = (java.awt.Graphics2D)g;
		g.translate( x, y );
		Object prevAntialiasing = edu.cmu.cs.dennisc.java.awt.GraphicsUtilities.setAntialiasing( g2, java.awt.RenderingHints.VALUE_ANTIALIAS_ON );
		try {
			if( buttonModel.isEnabled() ) {
				if( buttonModel.isSelected() ) {
					//pass
				} else {
					if( buttonModel.isRollover() ) {
						float xCenter = width * 0.5f;
						float yCenter = height * 0.5f;
						g2.setPaint( new java.awt.Color( 255, 255, 191, 9 ) );
						for( float radius = 50.0f; radius < 80.0f; radius += 4.0f ) {
							java.awt.geom.Ellipse2D.Float shape = new java.awt.geom.Ellipse2D.Float( xCenter - radius, yCenter - radius, radius + radius, radius + radius );
							g2.fill( shape );
						}
					}

					java.awt.Stroke prevStroke = g2.getStroke();
					g2.setStroke( stroke );
					java.awt.geom.RoundRectangle2D.Float rr = new java.awt.geom.RoundRectangle2D.Float( 0, 0, width, height, width * 0.6f, height * 0.6f );
					g2.setColor( edu.cmu.cs.dennisc.java.awt.ColorUtilities.createGray( 70 ) );
					g2.fill( rr );
					g2.setColor( edu.cmu.cs.dennisc.java.awt.ColorUtilities.createGray( 220 ) );
					g2.draw( rr );

					int w = (int)( width * 0.4 );
					int h = (int)( height * 0.45 );
					int xFudge = width / 20;
					edu.cmu.cs.dennisc.java.awt.GraphicsUtilities.fillTriangle( g2, edu.cmu.cs.dennisc.java.awt.GraphicsUtilities.Heading.EAST, ( ( width - w ) / 2 ) + xFudge, ( height - h ) / 2, w, h );
					g2.setStroke( prevStroke );
				}
			}
		} finally {
			edu.cmu.cs.dennisc.java.awt.GraphicsUtilities.setAntialiasing( g2, prevAntialiasing );
			g.translate( -x, -y );
		}
	}

	private final java.awt.Stroke stroke;
	private final int width;
	private final int height;

	public PlayIcon( int width, int height ) {
		this.width = width;
		this.height = height;
		int size = Math.max( this.width, this.height );
		this.stroke = new java.awt.BasicStroke( size / 25.0f );
	}

	public int getIconWidth() {
		return this.width;
	}

	public int getIconHeight() {
		return this.height;
	}

	public void paintIcon( java.awt.Component c, java.awt.Graphics g, int x, int y ) {
		if( c instanceof javax.swing.AbstractButton ) {
			javax.swing.AbstractButton button = (javax.swing.AbstractButton)c;
			javax.swing.ButtonModel buttonModel = button.getModel();
			paint( c, g, buttonModel, this.stroke, x, y, this.width, this.height );
		}
	}
}

class PlayButtonUI extends javax.swing.plaf.basic.BasicToggleButtonUI {
	@Override
	public void paint( java.awt.Graphics g, javax.swing.JComponent c ) {
		//super.paint( g, c );
	}
}

class PlayLayout extends java.awt.BorderLayout {
	@Override
	public void layoutContainer( java.awt.Container target ) {
		super.layoutContainer( target );
		if( target instanceof javax.swing.AbstractButton ) {
			javax.swing.AbstractButton button = (javax.swing.AbstractButton)target;
			javax.swing.ButtonModel buttonModel = button.getModel();
			if( buttonModel.isSelected() ) {
				//pass
			} else {
				java.awt.Component centerComponent = this.getLayoutComponent( target, CENTER );
				if( centerComponent instanceof java.awt.Canvas ) {
					java.awt.Canvas canvas = (java.awt.Canvas)centerComponent;
					if( canvas.isEnabled() ) {
						//pass
					} else {
						canvas.setBounds( 0, 0, 0, 0 );
					}
				}
			}
		}
	}
}

class JPlayView extends javax.swing.JToggleButton {
	private static final int SIZE = 64;

	private final javax.swing.Icon icon = new PlayIcon( SIZE, SIZE );

	public JPlayView() {
		this.setModel( new javax.swing.JToggleButton.ToggleButtonModel() );
		this.setRolloverEnabled( true );
		this.setPreferredSize( new java.awt.Dimension( 640, 360 ) );
		this.setLayout( new PlayLayout() );
		this.setBackground( java.awt.Color.BLACK );
	}

	@Override
	protected void paintComponent( java.awt.Graphics g ) {
		super.paintComponent( g );
		if( this.isSelected() ) {
			//pass
		} else {
			edu.cmu.cs.dennisc.java.awt.GraphicsUtilities.paintIconCentered( this.icon, this, g );
		}
	}

	@Override
	public void updateUI() {
		this.setUI( new PlayButtonUI() );
	}
}

abstract class PlayView extends org.lgna.croquet.components.View<javax.swing.JToggleButton, test.video.VideoComposite> {
	private final java.awt.event.ItemListener itemListener = new java.awt.event.ItemListener() {
		public void itemStateChanged( java.awt.event.ItemEvent e ) {
			PlayView.this.handleItemStateChanged( e );
		}
	};

	public PlayView( test.video.VideoComposite composite ) {
		super( composite );
		this.getAwtComponent().addItemListener( this.itemListener );
	}

	protected abstract void handleItemStateChanged( java.awt.event.ItemEvent e );

	@Override
	protected javax.swing.JToggleButton createAwtComponent() {
		return new JPlayView();
	}
}

/**
 * @author Dennis Cosgrove
 */
public class VideoView extends PlayView {
	private java.io.File file;
	private edu.cmu.cs.dennisc.video.VideoPlayer videoPlayer;
	private final javax.swing.JSlider jSlider = new javax.swing.JSlider( 0, 100, 0 );
	//	{
	//		@Override
	//		public void updateUI() {
	//			this.setUI( new javax.swing.plaf.basic.BasicSliderUI( this ) {
	//				@Override
	//				public void paintTrack( java.awt.Graphics g ) {
	//					super.paintTrack( g );
	//					java.awt.Graphics2D g2 = (java.awt.Graphics2D)g;
	//					g2.setColor( java.awt.Color.BLUE );
	//					g2.fill( this.trackRect );
	//					edu.cmu.cs.dennisc.java.util.logging.Logger.outln( this.trackRect );
	//				}
	//			} );
	//		}
	//	};

	private boolean isIgnoringSliderValueChanges;

	private final edu.cmu.cs.dennisc.video.event.MediaListener mediaListener = new edu.cmu.cs.dennisc.video.event.MediaListener() {
		public void newMedia() {
		}

		public void positionChanged( float f ) {
			isIgnoringSliderValueChanges = true;
			jSlider.setValue( (int)( f * 100 ) );
			isIgnoringSliderValueChanges = false;
		}

		public void playing() {
		}

		public void finished() {
			videoPlayer.getVideoSurface().setEnabled( false );
			getAwtComponent().setSelected( false );
		}

		public void stopped() {
		}

		public void error() {
		}
	};

	private final java.awt.event.MouseListener mouseListener = new java.awt.event.MouseListener() {
		public void mouseEntered( java.awt.event.MouseEvent e ) {
		}

		public void mouseExited( java.awt.event.MouseEvent e ) {
		}

		public void mousePressed( java.awt.event.MouseEvent e ) {
			getAwtComponent().setSelected( getAwtComponent().isSelected() == false );
		}

		public void mouseReleased( java.awt.event.MouseEvent e ) {
		}

		public void mouseClicked( java.awt.event.MouseEvent e ) {
		}
	};

	private final javax.swing.event.ChangeListener sliderValueChangeListener = new javax.swing.event.ChangeListener() {
		public void stateChanged( javax.swing.event.ChangeEvent e ) {
			if( isIgnoringSliderValueChanges ) {
				//pass
			} else {
				if( videoPlayer.isPlaying() ) {
					videoPlayer.pause();
				}
				videoPlayer.setPosition( jSlider.getValue() * 0.01f );
			}
		}
	};

	public VideoView( test.video.VideoComposite composite ) {
		super( composite );

		this.jSlider.addChangeListener( this.sliderValueChangeListener );
		this.getAwtComponent().add( this.jSlider, java.awt.BorderLayout.PAGE_END );
	}

	public void setFile( java.io.File file ) {
		if( file != null ) {
			if( file.exists() ) {
				//pass
			} else {
				edu.cmu.cs.dennisc.java.util.logging.Logger.severe( file );
			}
		}
		this.file = file;
	}

	public edu.cmu.cs.dennisc.video.VideoPlayer getVideoPlayer() {
		if( this.videoPlayer != null ) {
			//pass
		} else {
			this.videoPlayer = edu.cmu.cs.dennisc.video.VideoUtilties.createVideoPlayer();

			java.awt.Canvas videoSurface = this.videoPlayer.getVideoSurface();
			java.awt.Component component;
			if( videoSurface != null ) {
				videoSurface.addMouseListener( this.mouseListener );
				component = videoSurface;
			} else {
				component = new javax.swing.JLabel( "error" );
			}
			this.getAwtComponent().add( component, java.awt.BorderLayout.CENTER );
			this.revalidateAndRepaint();
			this.videoPlayer.addMediaListener( this.mediaListener );
		}
		return this.videoPlayer;
	}

	private void play() {
		if( this.file != null ) {
			edu.cmu.cs.dennisc.video.VideoPlayer videoPlayer = this.getVideoPlayer();
			videoPlayer.getVideoSurface().setEnabled( true );
			this.revalidateAndRepaint();
			if( videoPlayer.isPlayable() ) {
				//pass
			} else {
				videoPlayer.prepareMedia( this.file );
			}
			videoPlayer.playResume();
		}
	}

	private void pause() {
		if( this.videoPlayer != null ) {
			this.videoPlayer.pause();
			this.revalidateAndRepaint();
		}
	}

	private void setPlaying( boolean isPlaying ) {
		edu.cmu.cs.dennisc.java.util.logging.Logger.outln( isPlaying );
		if( isPlaying ) {
			this.play();
		} else {
			this.pause();
		}
	}

	@Override
	protected void handleItemStateChanged( java.awt.event.ItemEvent e ) {
		boolean isPlaying = e.getStateChange() == java.awt.event.ItemEvent.SELECTED;
		this.setPlaying( isPlaying );
	}
}
