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
package org.alice.ide.preferencesinputpane;

/**
 * @author Dennis Cosgrove
 */
public class ConfigurationPreferencePaneProxy extends PreferenceProxy<org.alice.ide.preferences.programming.Configuration> {
	class ConfigurationSelectionOperation extends org.alice.ide.operations.AbstractItemSelectionOperation<org.alice.ide.preferences.programming.Configuration> {
		public ConfigurationSelectionOperation( org.alice.ide.preferences.programming.Configuration... panes ) {
			super( java.util.UUID.fromString( "68071aa4-895c-4d30-8b8a-5bbcc5262586" ), new org.lgna.croquet.Codec< org.alice.ide.preferences.programming.Configuration >() {
				public Class< org.alice.ide.preferences.programming.Configuration > getValueClass() {
					return org.alice.ide.preferences.programming.Configuration.class;
				}
				public org.alice.ide.preferences.programming.Configuration decodeValue( edu.cmu.cs.dennisc.codec.BinaryDecoder binaryDecoder ) {
					throw new RuntimeException( "todo" );
				}
				public void encodeValue( edu.cmu.cs.dennisc.codec.BinaryEncoder binaryEncoder, org.alice.ide.preferences.programming.Configuration t ) {
					throw new RuntimeException( "todo" );
				}
				public StringBuilder appendRepresentation( StringBuilder rv, org.alice.ide.preferences.programming.Configuration value, java.util.Locale locale ) {
					rv.append( value );
					return rv;
				}
			}, -1, panes );
		}
		
		@Override
		protected void handleSelectionChange(org.alice.ide.preferences.programming.Configuration value) {
			ConfigurationPreferencePaneProxy.this.preview.updateValues(value);
		}
	}

//	class ConfigurationComboBox extends edu.cmu.cs.dennisc.croquet.KComboBox {
//		public ConfigurationComboBox( org.alice.ide.preferences.programming.Configuration[] configurations ) {
//			super( new ConfigurationSelectionOperation( configurations ) );
//			//this.setCellRenderer( new PerspectiveListCellRenderer() );
//		}
//	}
	
	class ConfigurationPreview extends org.lgna.croquet.components.RowsSpringPanel {
		private org.lgna.croquet.components.Label isDefaultFieldNameGenerationDesiredLabel;
		private org.lgna.croquet.components.Label isSyntaxNoiseDesiredLabel;
		private void ensureLabelsExist() {
			if( this.isDefaultFieldNameGenerationDesiredLabel != null ) {
				//pass
			} else {
				this.isDefaultFieldNameGenerationDesiredLabel = new org.lgna.croquet.components.Label();
				this.isDefaultFieldNameGenerationDesiredLabel.setForegroundColor( java.awt.Color.GRAY );
			}
			if( this.isSyntaxNoiseDesiredLabel != null ) {
				//pass
			} else {
				this.isSyntaxNoiseDesiredLabel = new org.lgna.croquet.components.Label();
				this.isSyntaxNoiseDesiredLabel.setForegroundColor( java.awt.Color.GRAY );
			}
		}
		@Override
		protected java.util.List< org.lgna.croquet.components.Component< ? >[] > updateComponentRows( java.util.List< org.lgna.croquet.components.Component< ? >[] > rv ) {
			this.ensureLabelsExist();
			rv.add( 
					org.lgna.croquet.components.SpringUtilities.createRow( 
							org.lgna.croquet.components.SpringUtilities.createTrailingLabel( "isDefaultFieldNameGenerationDesired:" ), 
							this.isDefaultFieldNameGenerationDesiredLabel 
					) 
			);
			rv.add( 
					org.lgna.croquet.components.SpringUtilities.createRow( 
							org.lgna.croquet.components.SpringUtilities.createTrailingLabel( "isSyntaxNoiseDesired:" ), 
							this.isSyntaxNoiseDesiredLabel 
					) 
			);
			return rv;
		}
		
		public void updateValues( org.alice.ide.preferences.programming.Configuration value ) {
			this.ensureLabelsExist();
			this.isDefaultFieldNameGenerationDesiredLabel.setText( Boolean.toString( value.isDefaultFieldNameGenerationDesired() ).toUpperCase() );
			this.isSyntaxNoiseDesiredLabel.setText( Boolean.toString( value.isSyntaxNoiseDesired() ).toUpperCase() );
		}
	}
	private org.lgna.croquet.components.PageAxisPanel pane;
	private ConfigurationPreview preview;
	
	abstract class PreferencesActionOperation extends org.alice.ide.operations.ActionOperation {
		public PreferencesActionOperation( java.util.UUID individualId ) {
			super( org.alice.ide.operations.preferences.PreferencesOperation.PREFERENCES_GROUP, individualId );
		}
	}
	class EditVariantOperation extends PreferencesActionOperation {
		public EditVariantOperation() {
			super( java.util.UUID.fromString( "6035083a-a50e-43bb-a527-5c680ce25a0d" ) );
		}
		@Override
		protected void localize() {
			super.localize();
			this.setName( "Edit..." );
		}
		@Override
		protected final void perform(org.lgna.croquet.steps.ActionOperationStep step) {
		}
	}
	class RemoveVariantOperation extends PreferencesActionOperation {
		public RemoveVariantOperation() {
			super( java.util.UUID.fromString( "b1e27e44-814b-42aa-a6d1-b6a8520b2ff8" ) );
		}
		@Override
		protected void localize() {
			super.localize();
			this.setName( "Remove" );
		}
		@Override
		protected final void perform(org.lgna.croquet.steps.ActionOperationStep step) {
		}
	}
	class NewVariantOperation extends PreferencesActionOperation {
		public NewVariantOperation() {
			super( java.util.UUID.fromString( "fac28f32-3beb-4c47-bc73-ff73b34fc597" ) );
		}
		@Override
		protected void localize() {
			super.localize();
			this.setName( "New..." );
		}
		@Override
		protected final void perform(org.lgna.croquet.steps.ActionOperationStep step) {
		}
	}
	class ImportVariantOperation extends PreferencesActionOperation {
		public ImportVariantOperation() {
			super( java.util.UUID.fromString( "5cf110be-9d53-40dd-8461-b8d0183ae10d" ) );
		}
		@Override
		protected void localize() {
			super.localize();
			this.setName( "Import..." );
		}
		@Override
		protected final void perform(org.lgna.croquet.steps.ActionOperationStep step) {
		}
	}
	public ConfigurationPreferencePaneProxy( edu.cmu.cs.dennisc.preference.Preference<org.alice.ide.preferences.programming.Configuration> preference ) {
		super( preference );
		org.alice.ide.preferences.programming.Configuration[] configurations = org.alice.ide.preferences.ProgrammingPreferences.getSingleton().getBuiltInPreferenceNodes();
		ConfigurationSelectionOperation configurationSelectionOperation = new ConfigurationSelectionOperation( configurations );
		org.lgna.croquet.components.ComboBox<org.alice.ide.preferences.programming.Configuration> activeConfigurationComboBox = configurationSelectionOperation.getPrepModel().createComboBox();
		configurationSelectionOperation.setSelectedItem( configurations[ 0 ] );
		EditVariantOperation editVariantOperation = new EditVariantOperation();
		editVariantOperation.setEnabled( false );
		editVariantOperation.setToolTipText( "coming soon" );
		RemoveVariantOperation removeVariantOperation = new RemoveVariantOperation();
		removeVariantOperation.setEnabled( false );
		removeVariantOperation.setToolTipText( "coming soon" );
		NewVariantOperation newVariantOperation = new NewVariantOperation();
		newVariantOperation.setEnabled( false );
		newVariantOperation.setToolTipText( "coming soon" );
		
		ImportVariantOperation importVariantOperation = new ImportVariantOperation();
		importVariantOperation.setEnabled( false );
		importVariantOperation.setToolTipText( "coming soon" );

		org.lgna.croquet.components.LineAxisPanel northTopPane = new org.lgna.croquet.components.LineAxisPanel( 
				activeConfigurationComboBox, 
				editVariantOperation.createButton(), 
				removeVariantOperation.createButton() );
		org.lgna.croquet.components.LineAxisPanel northBottomPane = new org.lgna.croquet.components.LineAxisPanel( 
				newVariantOperation.createButton(), 
				importVariantOperation.createButton(), 
				org.lgna.croquet.components.BoxUtilities.createHorizontalGlue() );

		this.preview = new ConfigurationPreview();
		this.preview.updateValues( configurationSelectionOperation.getSelectedItem() );
		this.pane = new org.lgna.croquet.components.PageAxisPanel(
				new org.lgna.croquet.components.Label( "active variant:" ),
				org.lgna.croquet.components.BoxUtilities.createVerticalSliver( 4 ),  
				northTopPane, 
				org.lgna.croquet.components.BoxUtilities.createVerticalSliver( 4 ),  
				northBottomPane,
				org.lgna.croquet.components.BoxUtilities.createVerticalSliver( 32 ),
				//edu.cmu.cs.dennisc.croquet.CroquetUtilities.createLabel( "preview:" ),
				//javax.swing.Box.createVerticalStrut( 4 ),  
				this.preview
		);
		this.pane.setBorder( javax.swing.BorderFactory.createEmptyBorder( 4, 8, 4, 4 ) );
	}
	@Override
	public org.lgna.croquet.components.Component< ? > getComponent() {
		return this.pane;
	}
	@Override
	public void setAndCommitValue() {
		edu.cmu.cs.dennisc.print.PrintUtilities.println( "todo: ConfigurationPreferencePaneProxy" );
	}
}
