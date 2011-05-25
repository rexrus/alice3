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
package org.alice.ide.croquet.models.menubar;

/**
 * @author Dennis Cosgrove
 */
public class RecentProjectsMenuModel extends edu.cmu.cs.dennisc.croquet.MenuModel {
	private static class SingletonHolder {
		private static RecentProjectsMenuModel instance = new RecentProjectsMenuModel();
	}
	public static RecentProjectsMenuModel getInstance() {
		return SingletonHolder.instance;
	}
	private RecentProjectsMenuModel() {
		super( java.util.UUID.fromString( "f94dda45-71e1-48df-9291-a8681b08f1c0" ) );
	}
	
	@Override
	protected void handleShowing( org.lgna.croquet.components.MenuItemContainer menuItemContainer, javax.swing.event.PopupMenuEvent e ) {
		java.util.List<String> paths = org.alice.ide.preferences.GeneralPreferences.getSingleton().recentProjectPaths.getValue();
		final int N = paths.size();
		edu.cmu.cs.dennisc.croquet.MenuItemPrepModel[] models = new edu.cmu.cs.dennisc.croquet.MenuItemPrepModel[ N ];
		for( int i=0; i<N; i++ ) {
			String path = paths.get( i );
			java.io.File file = new java.io.File( path );
			models[ i ] = org.alice.ide.croquet.models.projecturi.OpenRecentProjectOperation.getInstance( file.toURI() ).getMenuItemPrepModel();
		}
		org.lgna.croquet.components.MenuItemContainerUtilities.addMenuElements( menuItemContainer, models );
		edu.cmu.cs.dennisc.print.PrintUtilities.println( "todo: RecentProjectsMenuModel handleMenuSelected" );
		super.handleShowing( menuItemContainer, e );
	}
	@Override
	protected void handleHiding( org.lgna.croquet.components.MenuItemContainer menuItemContainer, javax.swing.event.PopupMenuEvent e ) {
		menuItemContainer.forgetAndRemoveAllMenuItems();
		super.handleHiding( menuItemContainer, e );
	}
}
