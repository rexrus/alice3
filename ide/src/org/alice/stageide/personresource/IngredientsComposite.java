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

package org.alice.stageide.personresource;

import java.util.Arrays;

/**
 * @author Dennis Cosgrove
 */
public class IngredientsComposite extends org.lgna.croquet.SimpleComposite<org.alice.stageide.personresource.views.IngredientsView> {
	private final org.lgna.croquet.Operation randomize = this.createActionOperation( this.createKey( "randomize" ), new Action() {
		public org.lgna.croquet.edits.Edit perform( org.lgna.croquet.history.CompletionStep<?> step, org.lgna.croquet.AbstractComposite.InternalActionOperation source ) throws org.lgna.croquet.CancelException {
			return createRandomEdit( step );
		}
	} );

	private final BodyTabComposite bodyTab = new BodyTabComposite();
	private final HeadTabComposite headTab = new HeadTabComposite();
	private final org.lgna.croquet.ListSelectionState<org.lgna.story.resources.sims2.LifeStage> lifeStageState = this.createListSelectionState( this.createKey( "lifeStageState" ), org.lgna.story.resources.sims2.LifeStage.class, edu.cmu.cs.dennisc.toolkit.croquet.codecs.EnumCodec.getInstance( org.lgna.story.resources.sims2.LifeStage.class ), 0, org.lgna.story.resources.sims2.LifeStage.ADULT, org.lgna.story.resources.sims2.LifeStage.CHILD );
	private final org.lgna.croquet.ListSelectionState<org.lgna.story.resources.sims2.Gender> genderState = this.createListSelectionStateForEnum( this.createKey( "genderState" ), org.lgna.story.resources.sims2.Gender.class, org.lgna.story.resources.sims2.Gender.getRandom() );
	private final SkinColorState skinColorState = new SkinColorState();
	private final org.lgna.croquet.TabSelectionState<org.lgna.croquet.SimpleTabComposite> bodyHeadTabState = this.createTabSelectionState( this.createKey( "bodyHeadTabState" ), 0, this.bodyTab, this.headTab );

	private final edu.cmu.cs.dennisc.map.MapToMap<org.lgna.story.resources.sims2.LifeStage, org.lgna.story.resources.sims2.Gender, org.lgna.story.resources.sims2.PersonResource> mapToMap = edu.cmu.cs.dennisc.map.MapToMap.newInstance();
	private final org.lgna.croquet.State.ValueListener<org.lgna.story.resources.sims2.LifeStage> lifeStageListener = new org.lgna.croquet.State.ValueListener<org.lgna.story.resources.sims2.LifeStage>() {
		public void changing( org.lgna.croquet.State<org.lgna.story.resources.sims2.LifeStage> state, org.lgna.story.resources.sims2.LifeStage prevValue, org.lgna.story.resources.sims2.LifeStage nextValue, boolean isAdjusting ) {
			pushAtomic();
		}

		public void changed( org.lgna.croquet.State<org.lgna.story.resources.sims2.LifeStage> state, org.lgna.story.resources.sims2.LifeStage prevValue, org.lgna.story.resources.sims2.LifeStage nextValue, boolean isAdjusting ) {
			popAtomic();
			updateCameraPointOfView();
		}
	};
	private final org.lgna.croquet.State.ValueListener<org.lgna.story.resources.sims2.Gender> genderListener = new org.lgna.croquet.State.ValueListener<org.lgna.story.resources.sims2.Gender>() {
		public void changing( org.lgna.croquet.State<org.lgna.story.resources.sims2.Gender> state, org.lgna.story.resources.sims2.Gender prevValue, org.lgna.story.resources.sims2.Gender nextValue, boolean isAdjusting ) {
			pushAtomic();
		}

		public void changed( org.lgna.croquet.State<org.lgna.story.resources.sims2.Gender> state, org.lgna.story.resources.sims2.Gender prevValue, org.lgna.story.resources.sims2.Gender nextValue, boolean isAdjusting ) {
			popAtomic();
		}
	};
	private final org.lgna.croquet.State.ValueListener<java.awt.Color> skinColorListener = new org.lgna.croquet.State.ValueListener<java.awt.Color>() {
		public void changing( org.lgna.croquet.State<java.awt.Color> state, java.awt.Color prevValue, java.awt.Color nextValue, boolean isAdjusting ) {










			pushAtomic();
		}

		public void changed( org.lgna.croquet.State<java.awt.Color> state, java.awt.Color prevValue, java.awt.Color nextValue, boolean isAdjusting ) {
			popAtomic();
		}
	};
	private final org.lgna.croquet.State.ValueListener<org.lgna.story.resources.sims2.BaseFace> faceListener = new org.lgna.croquet.State.ValueListener<org.lgna.story.resources.sims2.BaseFace>() {
		public void changing( org.lgna.croquet.State<org.lgna.story.resources.sims2.BaseFace> state, org.lgna.story.resources.sims2.BaseFace prevValue, org.lgna.story.resources.sims2.BaseFace nextValue, boolean isAdjusting ) {
			pushAtomic();
		}

		public void changed( org.lgna.croquet.State<org.lgna.story.resources.sims2.BaseFace> state, org.lgna.story.resources.sims2.BaseFace prevValue, org.lgna.story.resources.sims2.BaseFace nextValue, boolean isAdjusting ) {
			//			handleCataclysm( false, true, false );
			popAtomic();
		}
	};
	private final org.lgna.croquet.State.ValueListener<org.lgna.story.resources.sims2.BaseEyeColor> baseEyeColorListener = new org.lgna.croquet.State.ValueListener<org.lgna.story.resources.sims2.BaseEyeColor>() {
		public void changing( org.lgna.croquet.State<org.lgna.story.resources.sims2.BaseEyeColor> state, org.lgna.story.resources.sims2.BaseEyeColor prevValue, org.lgna.story.resources.sims2.BaseEyeColor nextValue, boolean isAdjusting ) {
			pushAtomic();
		}

		public void changed( org.lgna.croquet.State<org.lgna.story.resources.sims2.BaseEyeColor> state, org.lgna.story.resources.sims2.BaseEyeColor prevValue, org.lgna.story.resources.sims2.BaseEyeColor nextValue, boolean isAdjusting ) {
			popAtomic();
		}
	};
	private final org.lgna.croquet.State.ValueListener<String> hairColorNameListener = new org.lgna.croquet.State.ValueListener<String>() {
		public void changing( org.lgna.croquet.State<String> state, String prevValue, String nextValue, boolean isAdjusting ) {
			pushAtomic();
		}

		public void changed( org.lgna.croquet.State<String> state, String prevValue, String nextValue, boolean isAdjusting ) {
			popAtomic();
		}
	};
	private final org.lgna.croquet.State.ValueListener<org.lgna.story.resources.sims2.Hair> hairListener = new org.lgna.croquet.State.ValueListener<org.lgna.story.resources.sims2.Hair>() {
		public void changing( org.lgna.croquet.State<org.lgna.story.resources.sims2.Hair> state, org.lgna.story.resources.sims2.Hair prevValue, org.lgna.story.resources.sims2.Hair nextValue, boolean isAdjusting ) {
			pushAtomic();
		}

		public void changed( org.lgna.croquet.State<org.lgna.story.resources.sims2.Hair> state, org.lgna.story.resources.sims2.Hair prevValue, org.lgna.story.resources.sims2.Hair nextValue, boolean isAdjusting ) {
			popAtomic();
		}
	};
	private final org.lgna.croquet.State.ValueListener<org.lgna.story.resources.sims2.FullBodyOutfit> fullBodyOutfitListener = new org.lgna.croquet.State.ValueListener<org.lgna.story.resources.sims2.FullBodyOutfit>() {
		public void changing( org.lgna.croquet.State<org.lgna.story.resources.sims2.FullBodyOutfit> state, org.lgna.story.resources.sims2.FullBodyOutfit prevValue, org.lgna.story.resources.sims2.FullBodyOutfit nextValue, boolean isAdjusting ) {
			pushAtomic();
		}

		public void changed( org.lgna.croquet.State<org.lgna.story.resources.sims2.FullBodyOutfit> state, org.lgna.story.resources.sims2.FullBodyOutfit prevValue, org.lgna.story.resources.sims2.FullBodyOutfit nextValue, boolean isAdjusting ) {
			popAtomic();
		}
	};
	private final org.lgna.croquet.State.ValueListener<Double> obesityLevelListener = new org.lgna.croquet.State.ValueListener<Double>() {
		public void changing( org.lgna.croquet.State<Double> state, Double prevValue, Double nextValue, boolean isAdjusting ) {
			pushAtomic();
		}

		public void changed( org.lgna.croquet.State<Double> state, Double prevValue, Double nextValue, boolean isAdjusting ) {
			popAtomic();
		}
	};

	private final org.lgna.croquet.State.ValueListener<Integer> skinToneShiftListener = new org.lgna.croquet.State.ValueListener<Integer>() {
		public void changing( org.lgna.croquet.State<Integer> state, Integer prevValue, Integer nextValue, boolean isAdjusting ) {
			pushAtomic();
		}

		public void changed( org.lgna.croquet.State<Integer> state, Integer prevValue, Integer nextValue, boolean isAdjusting ) {
			popAtomic();
		}
	};

	private final org.lgna.croquet.State.ValueListener<org.lgna.croquet.SimpleTabComposite> tabListener = new org.lgna.croquet.State.ValueListener<org.lgna.croquet.SimpleTabComposite>() {
		public void changing( org.lgna.croquet.State<org.lgna.croquet.SimpleTabComposite> state, org.lgna.croquet.SimpleTabComposite prevValue, org.lgna.croquet.SimpleTabComposite nextValue, boolean isAdjusting ) {
		}

		public void changed( org.lgna.croquet.State<org.lgna.croquet.SimpleTabComposite> state, org.lgna.croquet.SimpleTabComposite prevValue, org.lgna.croquet.SimpleTabComposite nextValue, boolean isAdjusting ) {
			updateCameraPointOfView();
		}
	};

	private static final javax.swing.Icon RANDOM_ICON = edu.cmu.cs.dennisc.javax.swing.IconUtilities.createImageIcon( IngredientsComposite.class.getResource( "images/random.png" ) );

	public IngredientsComposite() {
		super( java.util.UUID.fromString( "dd127381-09a8-4f78-bfd5-f3bffc1af98b" ) );
		this.randomize.setButtonIcon( RANDOM_ICON );
	}

	private void updateCameraPointOfView() {
		org.lgna.croquet.SimpleTabComposite nextValue = this.bodyHeadTabState.getValue();
		org.alice.stageide.personresource.views.PersonViewer personViewer = PersonResourceComposite.getInstance().getPreviewComposite().getView();
		org.lgna.story.resources.sims2.LifeStage lifeStage = lifeStageState.getValue();
		if( lifeStage != null ) {
			//pass
		} else {
			lifeStage = org.lgna.story.resources.sims2.LifeStage.ADULT;
		}
		if( nextValue == headTab ) {
			personViewer.setCameraToCloseUp( lifeStage );
		} else {
			personViewer.setCameraToFullView( lifeStage );
		}
	}

	@Override
	protected org.alice.stageide.personresource.views.IngredientsView createView() {
		return new org.alice.stageide.personresource.views.IngredientsView( this );
	}

	public org.lgna.croquet.Operation getRandomize() {
		return this.randomize;
	}

	public org.lgna.croquet.ListSelectionState<org.lgna.story.resources.sims2.LifeStage> getLifeStageState() {
		return this.lifeStageState;
	}

	public org.lgna.croquet.ListSelectionState<org.lgna.story.resources.sims2.Gender> getGenderState() {
		return this.genderState;
	}

	public org.lgna.croquet.ListSelectionState<org.lgna.story.resources.sims2.BaseFace> getBaseFaceState() {
		return this.headTab.getBaseFaceState();
	}
	public SkinColorState getSkinColorState() {
		return this.skinColorState;
	}

	public org.lgna.croquet.ListSelectionState<org.lgna.story.resources.sims2.Hair> getHairState() {
		return this.headTab.getHairState();
	}

	public org.lgna.croquet.ListSelectionState<String> getHairColorNameState() {
		return this.headTab.getHairColorNameState();
	}

	public org.lgna.croquet.ListSelectionState<org.lgna.story.resources.sims2.BaseEyeColor> getBaseEyeColorState() {
		return this.headTab.getBaseEyeColorState();
	}

	public org.lgna.croquet.ListSelectionState<org.lgna.story.resources.sims2.FullBodyOutfit> getFullBodyOutfitState() {
		return this.bodyTab.getFullBodyOutfitState();
	}

	public org.lgna.croquet.BoundedDoubleState getObesityLevelState() {
		return this.bodyTab.getObesityLevelState();
	}

	public org.lgna.croquet.TabSelectionState<org.lgna.croquet.SimpleTabComposite> getBodyHeadTabState() {
		return this.bodyHeadTabState;
	}

	private org.lgna.croquet.edits.Edit createRandomEdit( org.lgna.croquet.history.CompletionStep<?> step ) {
		org.lgna.story.resources.sims2.LifeStage lifeStage;
		if( this.lifeStageState.isEnabled() ) {
			lifeStage = null;
		} else {
			lifeStage = this.lifeStageState.getValue();
		}
		org.lgna.story.resources.sims2.PersonResource nextPersonResource = org.alice.stageide.personresource.RandomPersonUtilities.createRandomResource( lifeStage );
		return new org.alice.stageide.personresource.edits.SetPersonResourceEdit( step, nextPersonResource );
	}

	private void addListeners() {
		this.getLifeStageState().addValueListener( this.lifeStageListener );
		this.getGenderState().addValueListener( this.genderListener );
		this.getSkinColorState().addValueListener( this.skinColorListener );
		this.getBaseFaceState().addValueListener( this.faceListener );		this.getBaseEyeColorState().addValueListener( this.baseEyeColorListener );
		this.getHairColorNameState().addValueListener( this.hairColorNameListener );
		this.getHairState().addValueListener( this.hairListener );
		this.getFullBodyOutfitState().addValueListener( this.fullBodyOutfitListener );
		this.getObesityLevelState().addValueListener( this.obesityLevelListener );
	}

	private void removeListeners() {
		this.getLifeStageState().removeValueListener( this.lifeStageListener );
		this.getGenderState().removeValueListener( this.genderListener );
		this.getSkinColorState().removeValueListener( this.skinColorListener );
		this.getBaseFaceState().removeValueListener( this.faceListener );
		this.getBaseEyeColorState().removeValueListener( this.baseEyeColorListener );
		this.getHairColorNameState().removeValueListener( this.hairColorNameListener );
		this.getHairState().removeValueListener( this.hairListener );
		this.getFullBodyOutfitState().removeValueListener( this.fullBodyOutfitListener );
		this.getObesityLevelState().removeValueListener( this.obesityLevelListener );
	}

	private int activeCount = 0;

	private void addListenersIfAppropriate() {
		if( activeCount > 0 ) {
			this.addListeners();
		}
	}

	private void removeListenersIfAppropriate() {
		if( activeCount > 0 ) {
			this.removeListeners();
		}
	}

	@Override
	public void handlePreActivation() {
		super.handlePreActivation();
		if( activeCount == 0 ) {
			this.addListeners();

			this.bodyHeadTabState.addAndInvokeValueListener( this.tabListener );
		} else {
			edu.cmu.cs.dennisc.java.util.logging.Logger.severe( this, this.activeCount );
		}
		this.activeCount++;
	}

	@Override
	public void handlePostDeactivation() {
		this.activeCount--;
		if( activeCount == 0 ) {
			this.bodyHeadTabState.removeValueListener( this.tabListener );
			this.removeListeners();
		}
		if( activeCount != 0 ) {
			edu.cmu.cs.dennisc.java.util.logging.Logger.severe( "todo" );
		}
		super.handlePostDeactivation();
	}

	private static org.lgna.story.resources.sims2.LifeStage getLifeStage( org.lgna.story.resources.sims2.PersonResource personResource ) {
		return personResource != null ? personResource.getLifeStage() : null;
	}

	private static org.lgna.story.resources.sims2.Gender getGender( org.lgna.story.resources.sims2.PersonResource personResource ) {
		return personResource != null ? personResource.getGender() : null;
	}

	private static org.lgna.story.resources.sims2.Hair getHair( org.lgna.story.resources.sims2.PersonResource personResource ) {
		return personResource != null ? personResource.getHair() : null;
	}

	private static String getHairColorName( org.lgna.story.resources.sims2.PersonResource personResource ) {
		if( personResource != null ) {
			org.lgna.story.resources.sims2.Hair hair = personResource.getHair();
			return hair != null ? hair.toString() : null;
		} else {
			return null;
		}
	}

	//	private static final String[] getHairColors( org.lgna.story.resources.sims2.LifeStage lifeStage ) {
	//		return lifeStage != null ? lifeStage.getHairColors() : null;
	//	}

	private org.lgna.story.resources.sims2.PersonResource prevPersonResource;
	private int atomicCount = 0;

	private void syncPersonImpAndMaps() {
		PersonImp personImp = PersonResourceComposite.getInstance().getPreviewComposite().getView().getPerson();
		if( personImp != null ) {
			personImp.updateNebPerson();
		}
		this.getView().repaint();

		this.prevPersonResource = this.createResourceFromStates();

		if( this.prevPersonResource != null ) {
			this.mapToMap.put( this.prevPersonResource.getLifeStage(), this.prevPersonResource.getGender(), this.prevPersonResource );
		}
	}

	private void updateHairColorName( org.lgna.story.resources.sims2.LifeStage lifeStage, org.lgna.story.resources.sims2.Gender gender, org.lgna.story.resources.sims2.Hair hair, String hairColorName ) {
		if( hairColorName != null ) {
			//pass
		} else {
			hairColorName = this.getHairColorNameState().getValue();
		}
		this.getHairColorNameState().setValueTransactionlessly( null );
		org.alice.stageide.personresource.data.HairColorNameListData data = this.headTab.getHairColorNameData();
		data.setHair( hair );
		if( hairColorName != null ) {
			if( data.contains( hairColorName ) ) {
				//pass
			} else {
				hairColorName = null;
			}
		}
		if( hairColorName != null ) {
			//pass
		} else {
			if( hair != null ) {
				hairColorName = hair.toString();
			}
			else {
				org.lgna.story.resources.sims2.PersonResource personResource = this.mapToMap.get( lifeStage, gender );
				if( personResource != null ) {
					org.lgna.story.resources.sims2.Hair personHair = personResource.getHair();
					if( personHair != null ) {
						hairColorName = personHair.toString();
					}
				}
			}
		}
		if( hairColorName != null ) {
			this.getHairColorNameState().setValueTransactionlessly( hairColorName );
		} else {
			this.getHairColorNameState().setRandomSelectedValue();
		}
	}

	private void updateHair( org.lgna.story.resources.sims2.LifeStage lifeStage, org.lgna.story.resources.sims2.Gender gender, org.lgna.story.resources.sims2.Hair hair ) {
		this.getHairState().setValueTransactionlessly( null );

		String hairColorName;
		if( hair != null ) {
			hairColorName = hair.toString();
		} else {
			hairColorName = this.getHairColorNameState().getValue();
			if( hairColorName != null ) {
				org.lgna.story.resources.sims2.PersonResource previousPersonResource = this.mapToMap.get( lifeStage, gender );
				if( previousPersonResource != null ) {
					org.lgna.story.resources.sims2.Hair previousHairValue = previousPersonResource.getHair();
					if( previousHairValue != null ) {
						Class<?> cls = previousHairValue.getClass();
						if( cls.isEnum() ) {
							try {
								hair = (org.lgna.story.resources.sims2.Hair)cls.getField( hairColorName ).get( null );
							} catch( Exception e ) {
								edu.cmu.cs.dennisc.java.util.logging.Logger.throwable( e, previousHairValue );
								hair = null;
							}
						}
					}
				}
			}
		}
		this.headTab.getHairData().setLifeStageGenderAndColorName( lifeStage, gender, hairColorName );

		if( hair != null ) {
			this.getHairState().setValueTransactionlessly( hair );
		} else {
			this.getHairState().setRandomSelectedValue();
		}
	}

	private void updateOutfit( org.lgna.story.resources.sims2.LifeStage lifeStage, org.lgna.story.resources.sims2.Gender gender, org.lgna.story.resources.sims2.FullBodyOutfit fullBodyOutfit ) {
		this.getFullBodyOutfitState().setValueTransactionlessly( null );
		this.bodyTab.getFullBodyOutfitData().setLifeStageAndGender( lifeStage, gender );
		if( fullBodyOutfit != null ) {
			//pass
		} else {
			org.lgna.story.resources.sims2.PersonResource previousPersonResource = this.mapToMap.get( lifeStage, gender );
			if( previousPersonResource != null ) {
				fullBodyOutfit = (org.lgna.story.resources.sims2.FullBodyOutfit)previousPersonResource.getOutfit();
			}
		}
		if( fullBodyOutfit != null ) {
			this.getFullBodyOutfitState().setValueTransactionlessly( fullBodyOutfit );
		} else {
			this.getFullBodyOutfitState().setRandomSelectedValue();
		}
	}

	public void pushAtomic() {
		if( this.atomicCount == 0 ) {
		}
		this.atomicCount++;
	}

	public void popAtomic() {
		this.atomicCount--;
		if( this.atomicCount == 0 ) {
			this.removeListenersIfAppropriate();
			try {
				org.lgna.story.resources.sims2.LifeStage prevLifeStage = getLifeStage( this.prevPersonResource );
				org.lgna.story.resources.sims2.LifeStage nextLifeStage = this.getLifeStageState().getValue();
				boolean isLifeStageChanged = prevLifeStage != nextLifeStage;

				org.lgna.story.resources.sims2.Gender nextGender = this.getGenderState().getValue();
				org.lgna.story.resources.sims2.Gender prevGender = getGender( this.prevPersonResource );
				boolean isGenderChanged = prevGender != nextGender;

				org.lgna.story.resources.sims2.Hair nextHair = this.getHairState().getValue();
				org.lgna.story.resources.sims2.Hair prevHair = getHair( this.prevPersonResource );
				boolean isHairChanged = nextHair != prevHair;

				if( isLifeStageChanged || isHairChanged ) {
					String[] nextColors = org.alice.stageide.personresource.data.HairColorNameListData.getHairColors( nextHair );
					String[] prevColors = org.alice.stageide.personresource.data.HairColorNameListData.getHairColors( prevHair );

					if( !Arrays.equals( nextColors, prevColors ) ) {
						this.updateHairColorName( nextLifeStage, nextGender, nextHair, null );
					}
				}

				String prevHairColorName = getHairColorName( this.prevPersonResource );
				final String nextHairColorName = this.getHairColorNameState().getValue();
				boolean isHairColorChanged = edu.cmu.cs.dennisc.equivalence.EquivalenceUtilities.areNotEquivalent( prevHairColorName, nextHairColorName );

				if( isLifeStageChanged || isGenderChanged ) {
					this.updateHair( nextLifeStage, nextGender, null );
				}
				else if( isHairColorChanged ) {
					if( isHairChanged ) {
						this.updateHair( nextLifeStage, nextGender, nextHair );
					}
					else {
						this.updateHair( nextLifeStage, nextGender, null );
					}
				}
				if( isLifeStageChanged || isGenderChanged ) {
					this.updateOutfit( nextLifeStage, nextGender, null );
				}
			} finally {
				this.addListenersIfAppropriate();
			}
			this.syncPersonImpAndMaps();
		}
	}

	public org.lgna.story.resources.sims2.PersonResource createResourceFromStates() {
		org.lgna.story.resources.sims2.LifeStage lifeStage = this.getLifeStageState().getValue();
		org.lgna.story.resources.sims2.Gender gender = this.getGenderState().getValue();

		java.awt.Color awtSkinColor = this.getSkinColorState().getValue();
		org.lgna.story.Color skinColor = org.lgna.story.ImplementationAccessor.createColor( new edu.cmu.cs.dennisc.color.Color4f( awtSkinColor ) );

		org.lgna.story.resources.sims2.EyeColor eyeColor = this.getBaseEyeColorState().getValue();
		org.lgna.story.resources.sims2.Outfit outfit = this.getFullBodyOutfitState().getValue();
		org.lgna.story.resources.sims2.Hair hair = this.getHairState().getValue();
		org.lgna.story.resources.sims2.BaseFace face = this.getBaseFaceState().getValue();
		double obesityLevel = this.getObesityLevelState().getValue();
		if( lifeStage != null ) {
			return lifeStage.createResource( gender, skinColor, eyeColor, hair, obesityLevel, outfit, face );
		} else {
			return null;
		}
	}

	public void setStates( org.lgna.story.resources.sims2.PersonResource personResource ) {
		this.removeListenersIfAppropriate();
		try {
			this.getLifeStageState().setValueTransactionlessly( personResource.getLifeStage() );
			this.getGenderState().setValueTransactionlessly( personResource.getGender() );
			this.getBaseEyeColorState().setValueTransactionlessly( (org.lgna.story.resources.sims2.BaseEyeColor)personResource.getEyeColor() );
			this.getSkinColorState().setValueTransactionlessly( org.lgna.story.ImplementationAccessor.getColor4f( personResource.getSkinColor() ).getAsAWTColor() );

			org.lgna.story.resources.sims2.Hair hair = personResource.getHair();
			this.updateOutfit( personResource.getLifeStage(), personResource.getGender(), (org.lgna.story.resources.sims2.FullBodyOutfit)personResource.getOutfit() );
			this.updateHairColorName( personResource.getLifeStage(), personResource.getGender(), hair, hair != null ? hair.toString() : null );
			this.updateHair( personResource.getLifeStage(), personResource.getGender(), hair );
			this.getObesityLevelState().setValueTransactionlessly( personResource.getObesityLevel() );
			this.getBaseFaceState().setValueTransactionlessly( (org.lgna.story.resources.sims2.BaseFace)personResource.getFace() );
		} finally {
			this.addListenersIfAppropriate();
		}
		this.syncPersonImpAndMaps();
	}
}
