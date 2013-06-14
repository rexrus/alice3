/*
 * Copyright (c) 2006-2010, Carnegie Mellon University. All rights reserved.
 */

package edu.cmu.cs.dennisc.nebulous;

/**
 * @author Dennis Cosgrove
 */
public class Manager {
	public static final double NEBULOUS_VERSION = 1.7;

	private static boolean s_isInitialized = false;
	private static boolean s_isLicensePromptDesired = true;
	private static final String IS_LICENSE_ACCEPTED_PREFERENCE_KEY = "isLicenseAccepted";

	private static java.util.List<java.io.File> s_pendingBundles;

	private static native void setVersion( double version );

	private static native void addBundlePath( String bundlePath );

	private static native void removeBundlePath( String bundlePath );

	private static native void setRawResourceDirectory( String rourcePath );

	private static native void unloadActiveModelData();

	private static native void unloadUnusedTextures( javax.media.opengl.GL gl );

	public static native void setDebugDraw( boolean debugDraw );

	private static void doInitializationIfNecessary() {
		try {
			initializeIfNecessary();
		} catch( edu.cmu.cs.dennisc.eula.LicenseRejectedException lre ) {
			javax.swing.JOptionPane.showMessageDialog( null, "license rejected" );
			//throw new RuntimeException( lre );
		} catch( Throwable t ) {
			javax.swing.JOptionPane.showMessageDialog( null, "failed to initialize art assets" );
			t.printStackTrace();
		}
	}

	private static java.util.List<java.io.File> getPendingBundles() {
		if( s_pendingBundles != null ) {
			//pass
		} else {
			s_pendingBundles = new java.util.LinkedList<java.io.File>();
		}
		return s_pendingBundles;
	}

	public static void unloadNebulousModelData() {
		if( isInitialized() ) {
			unloadActiveModelData();
		}
	}

	public static void unloadUnusedNebulousTextureData( javax.media.opengl.GL gl ) {
		if( isInitialized() ) {
			unloadUnusedTextures( gl );
		}
	}

	public static void initializeIfNecessary() throws edu.cmu.cs.dennisc.eula.LicenseRejectedException {
		if( isInitialized() ) {
			//pass
		} else {
			if( s_isLicensePromptDesired ) {
				edu.cmu.cs.dennisc.eula.EULAUtilities.promptUserToAcceptEULAIfNecessary(
						edu.cmu.cs.dennisc.nebulous.License.class,
						IS_LICENSE_ACCEPTED_PREFERENCE_KEY,
						"License Agreement: The Sims (TM) 2 Art Assets",
						edu.cmu.cs.dennisc.nebulous.License.TEXT,
						"The Sims (TM) 2 Art Assets" );
				java.util.prefs.Preferences userPreferences = java.util.prefs.Preferences.userNodeForPackage( License.class );
				boolean isLicenseAccepted = userPreferences.getBoolean( IS_LICENSE_ACCEPTED_PREFERENCE_KEY, false );
				if( isLicenseAccepted ) {
					//pass
				} else {
					s_isLicensePromptDesired = false;
				}
				if( isLicenseAccepted ) {
					userPreferences.putBoolean( IS_LICENSE_ACCEPTED_PREFERENCE_KEY, true );
					String platformSpecificLibraryName = edu.cmu.cs.dennisc.java.lang.SystemUtilities.getPlatformSpecificLibraryNameIfAppropriate( "jni_nebulous" );
					System.loadLibrary( platformSpecificLibraryName );
					for( java.io.File directory : Manager.getPendingBundles() ) {
						Manager.addBundlePath( directory.getAbsolutePath() );
					}
					Manager.setVersion( NEBULOUS_VERSION );

					s_isInitialized = true;
				} else {
					throw new edu.cmu.cs.dennisc.eula.LicenseRejectedException();
				}
			}
		}
	}

	public static boolean isInitialized() {
		return s_isInitialized;
	}

	public static void resetLicensePromptDesiredToTrue() {
		s_isLicensePromptDesired = true;
	}

	public static void setRawResourcePath( java.io.File file ) {
		doInitializationIfNecessary();
		Manager.setRawResourceDirectory( file.getAbsolutePath() );
	}

	public static void addBundle( java.io.File file ) {
		doInitializationIfNecessary();
		if( isInitialized() ) {
			Manager.addBundlePath( file.getAbsolutePath() );
		} else {
			Manager.getPendingBundles().add( file );
		}
	}

	public static void removeBundle( java.io.File file ) {
		doInitializationIfNecessary();
		if( isInitialized() ) {
			Manager.removeBundlePath( file.getAbsolutePath() );
		} else {
			Manager.getPendingBundles().remove( file );
		}
	}
}
