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
package edu.cmu.cs.dennisc.app;

/**
 * @author Dennis Cosgrove
 */
public class ApplicationRoot {
	private static final String DEFAULT_APPLICATION_ROOT_SYSTEM_PROPERTY = "org.alice.ide.rootDirectory";
	private static final String DEFAULT_APPLICATION_NAME = "Alice";

	private static final ApplicationRoot defaultInstance = new ApplicationRoot( DEFAULT_APPLICATION_ROOT_SYSTEM_PROPERTY, DEFAULT_APPLICATION_NAME );

	public static ApplicationRoot getDefaultinstance() {
		return defaultInstance;
	}

	private final java.io.File rootDirectory;

	//todo: make public?
	private ApplicationRoot( String rootDirectorySystemProperty, String applicationName ) {
		String rootDirectoryPath = System.getProperty( rootDirectorySystemProperty );
		//todo: fallback to System.getProperty( "user.dir" ) ???
		if( rootDirectoryPath != null ) {
			this.rootDirectory = new java.io.File( rootDirectoryPath );
			if( this.rootDirectory.exists() ) {
				//pass
			} else {
				StringBuilder sb = new StringBuilder();
				sb.append( "system property: " );
				sb.append( rootDirectorySystemProperty );
				sb.append( " is incorrectly set.\n" );
				sb.append( this.rootDirectory );
				sb.append( " does not exist.\n" );
				sb.append( applicationName );
				sb.append( " will not work until this is addressed." );
				javax.swing.JOptionPane.showMessageDialog( null, sb.toString(), "Application Root Error", javax.swing.JOptionPane.ERROR_MESSAGE );
				System.exit( -1 );
			}
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append( "system property: " );
			sb.append( rootDirectorySystemProperty );
			sb.append( " is not set.\n" );
			sb.append( applicationName );
			sb.append( " will not work until this is addressed." );
			javax.swing.JOptionPane.showMessageDialog( null, sb.toString(), "Application Root Error", javax.swing.JOptionPane.ERROR_MESSAGE );
			this.rootDirectory = null;
			System.exit( -1 );
		}
	}

	public java.io.File getPlatformDirectory() {
		return new java.io.File( this.rootDirectory, "platform" );
	}

	public java.io.File getArchitectureSpecificDirectory() {
		StringBuilder sb = new StringBuilder();
		if( edu.cmu.cs.dennisc.java.lang.SystemUtilities.isMac() ) {
			sb.append( "macosx-universal" );
		} else {
			Integer bitCount = edu.cmu.cs.dennisc.java.lang.SystemUtilities.getBitCount();
			if( bitCount != null ) {
				String bitCountText;
				switch( bitCount ) {
				case 32:
					bitCountText = "i586/";
					break;
				case 64:
					bitCountText = "amd64/";
					break;
				default:
					throw new RuntimeException( System.getProperty( "sun.arch.data.model" ) );
				}

				if( edu.cmu.cs.dennisc.java.lang.SystemUtilities.isWindows() ) {
					sb.append( "windows-" );
					sb.append( bitCountText );
				} else if( edu.cmu.cs.dennisc.java.lang.SystemUtilities.isLinux() ) {
					sb.append( "linux-" );
					sb.append( bitCountText );
				} else {
					throw new RuntimeException( System.getProperty( "os.name" ) );
				}
			} else {
				throw new RuntimeException( System.getProperty( "sun.arch.data.model" ) );
			}
		}
		return new java.io.File( getPlatformDirectory(), sb.toString() );
	}

	//	public java.io.File getCommand( String subPath ) {
	//		StringBuilder sb = new StringBuilder();
	//		sb.append( subPath );
	//		if( edu.cmu.cs.dennisc.java.lang.SystemUtilities.isWindows() ) {
	//			sb.append( ".exe" );
	//		}
	//		return new java.io.File( this.getArchitectureSpecificDirectory(), sb.toString() );
	//	}
}
