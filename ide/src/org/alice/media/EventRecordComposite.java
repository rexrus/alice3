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
package org.alice.media;

import javax.swing.SwingUtilities;

import org.alice.media.components.EventRecordView;
import org.alice.stageide.program.RunProgramContext;
import org.lgna.croquet.ActionOperation;
import org.lgna.croquet.WizardPageComposite;
import org.lgna.croquet.history.Transaction;
import org.lgna.croquet.triggers.Trigger;
import org.lgna.story.ImplementationAccessor;
import org.lgna.story.implementation.SceneImp;

import edu.cmu.cs.dennisc.matt.EventScript;

/**
 * @author Matt May
 */
public class EventRecordComposite extends WizardPageComposite<EventRecordView> {
	private final ExportToYouTubeWizardDialogComposite owner;

	private RunProgramContext programContext;
	
	private final ActionOperation playRecordedOperation = this.createActionOperation( new Action() {

		private boolean isPlaying = false;
		private EventScript script;

		public void perform( Transaction transaction, Trigger trigger ) {
			if( isPlaying ) {
				isPlaying = !isPlaying;
				programContext.getProgramImp().stopAnimator();
				playRecordedOperation.setName( "play" );
				script = ((SceneImp)ImplementationAccessor.getImplementation( programContext.getProgram().getActiveScene() )).getTranscript();
				System.out.println("stop");
			} else {
				isPlaying = !isPlaying;
				System.out.println("play");
				playRecordedOperation.setName( "stop" );
				//				programContext.getProgramImp().setFrameRate( ProgramImp.CLOCK_BASED_FRAME_RATE );
				programContext.getProgramImp().startAnimator();
			}
		}
	}, this.createKey( "recordEventsStopToggle" ) );
	

	
	public EventRecordComposite( ExportToYouTubeWizardDialogComposite owner ) {
		super( java.util.UUID.fromString( "35d34417-8c0c-4f06-b919-5945b336b596" ) );
		this.owner = owner;
		this.playRecordedOperation.setName( "play" );
	}
	
//	public void startUp( final org.lgna.project.ast.NamedUserType programType ) {
//		final EventRecordView recordView = this.getView();
//		new Thread() {
//			@Override
//			public void run() {
//				super.run();
//				org.lgna.croquet.components.BorderPanel lookingGlassContainer = recordView.getLookingGlassContainer();
//
//				programContext = new RunProgramContext( programType );
//				programContext.initializeInContainer( lookingGlassContainer.getAwtComponent() );
//				programContext.setActiveScene();
//			}
//		}.start();
//	}
	
	public ActionOperation getPlayRecordedOperation() {
		return this.playRecordedOperation;
	}

	@Override
	protected EventRecordView createView() {
		return new EventRecordView( this );
	}
}
