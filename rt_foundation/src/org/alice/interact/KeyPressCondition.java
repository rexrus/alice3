/**
 * Copyright (c) 2006-2009, Carnegie Mellon University. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * 
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 
 * 3. Products derived from the software may not be called "Alice",
 *    nor may "Alice" appear in their name, without prior written
 *    permission of Carnegie Mellon University.
 * 
 * 4. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    "This product includes software developed by Carnegie Mellon University"
 */
package org.alice.interact;

import java.awt.event.KeyEvent;

/**
 * @author David Culyba
 */
public class KeyPressCondition extends ModifierSensitiveCondition {

	protected int keyValue = 0;
	
	
	public KeyPressCondition( int keyValue )
	{
		this( keyValue, null );
	}
	
	public KeyPressCondition( int keyValue, ModifierMask modifierMask )
	{
		super(modifierMask);
		this.keyValue = keyValue;
	}
	
	@Override
	protected boolean testState( InputState state )
	{
		return (super.testState( state ) && state.isKeyDown( this.keyValue));
	}
	
	@Override
	public String toString()
	{
		return KeyEvent.getKeyText( this.keyValue );
	}

}
