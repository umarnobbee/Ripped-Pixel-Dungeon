/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.ripped.pixeldungeon.actors.hero;

import com.ripped.pixeldungeon.Assets;
import com.ripped.pixeldungeon.Badges;
import com.ripped.pixeldungeon.Dungeon;
import com.ripped.pixeldungeon.items.TomeOfMastery;
import com.ripped.pixeldungeon.items.armor.ClothArmor;
import com.ripped.pixeldungeon.items.food.Food;
import com.ripped.pixeldungeon.items.potions.PotionOfHealing;
import com.ripped.pixeldungeon.items.potions.PotionOfStrength;
import com.ripped.pixeldungeon.items.rings.RingOfHerbalism;
import com.ripped.pixeldungeon.items.rings.RingOfShadows;
import com.ripped.pixeldungeon.items.scrolls.ScrollOfIdentify;
import com.ripped.pixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.ripped.pixeldungeon.items.wands.WandOfMagicMissile;
import com.ripped.pixeldungeon.items.weapon.melee.Club;
import com.ripped.pixeldungeon.items.weapon.melee.Dagger;
import com.ripped.pixeldungeon.items.weapon.melee.Nunchucks;
import com.ripped.pixeldungeon.items.weapon.melee.ShortSword;
import com.ripped.pixeldungeon.items.weapon.missiles.Boomerang;
import com.ripped.pixeldungeon.items.weapon.missiles.Dart;
import com.watabou.utils.Bundle;

public enum HeroClass {

	WARRIOR( "warrior" ), MAGE( "mage" ), ROGUE( "rogue" ), HUNTRESS( "huntress" ), PRIEST("priest") ;
	
	private String title;
	
	private HeroClass( String title ) {
		this.title = title;
	}
	
	public static final String[] WAR_PERKS = {
		"Warriors start with 11 points of Strength.",
		"Warriors start with a unique short sword. This sword can be later \"reforged\" to upgrade another melee weapon.",
		"Warriors are less proficient with missile weapons.",
		"Any piece of food restores some health when eaten.",
		"Potions of Strength are identified from the beginning.",
	};
	
	public static final String[] MAG_PERKS = {
		"Mages start with a unique Wand of Magic Missile. This wand can be later \"disenchanted\" to upgrade another wand.",
		"Mages recharge their wands faster.",
		"When eaten, any piece of food restores 1 charge for all wands in the inventory.",
		"Mages can use wands as a melee weapon.",
		"Scrolls of Identify are identified from the beginning."
	};
	
	public static final String[] ROG_PERKS = {
		"Rogues start with a Ring of Shadows+1.",
		"Rogues identify a type of a ring on equipping it.",
		"Rogues are proficient with light armor, dodging better while wearing one.",
		"Rogues are proficient in detecting hidden doors and traps.",
		"Rogues can go without food longer.",
		"Scrolls of Magic Mapping are identified from the beginning."
	};
	
	public static final String[] HUN_PERKS = {
		"Huntresses start with 15 points of Health.",
		"Huntresses start with a unique upgradeable boomerang.",
		"Huntresses are proficient with missile weapons and get damage bonus for excessive strength when using them.",
		"Huntresses gain more health from dewdrops.",
		"Huntresses sense neighbouring monsters even if they are hidden behind obstacles."
	};
	
	public static final String[] PRIEST_PERKS = {
			"Priest's start with a club with healing qualities.",
			"Priest's also start with 16 points of health.",
			"Known for their elemental will, Priest's also own a ring of Herbalism.",
			"Potions of Healing are identified from the beginning.",
			
	};
	
	public void initHero( Hero hero ) {
		
		hero.heroClass = this;
		
		switch (this) {
		case WARRIOR:
			initWarrior( hero );
			break;
			
		case MAGE:
			initMage( hero );
			break;
			
		case ROGUE:
			initRogue( hero );
			break;
			
	    case HUNTRESS:
			initHuntress( hero );
			break;
			
		case PRIEST:
			initPriest( hero );
			break;
		}
		
		hero.updateAwareness();
	}
		


	private static void initWarrior( Hero hero ) {
		hero.STR = hero.STR + 1;
		
		(hero.belongings.weapon = new ShortSword()).identify();
		(hero.belongings.armor = new ClothArmor()).identify();
		new Dart( 8 ).identify().collect();
		new Food().identify().collect();
		if (Badges.isUnlocked( Badges.Badge.MASTERY_WARRIOR )) {
			new TomeOfMastery().collect();
		}
		
		Dungeon.quickslot = Dart.class;
		
		new PotionOfStrength().setKnown();
	}
	
	private static void initMage( Hero hero ) {	
		(hero.belongings.weapon = new Nunchucks()).identify();
		(hero.belongings.armor = new ClothArmor()).identify();
		new Food().identify().collect();
		
		WandOfMagicMissile wand = new WandOfMagicMissile();
		wand.identify().collect();
		
		if (Badges.isUnlocked( Badges.Badge.MASTERY_MAGE )) {
			new TomeOfMastery().collect();
		}
		
		Dungeon.quickslot = wand;
		
		new ScrollOfIdentify().setKnown();
	}
	
	private static void initRogue( Hero hero ) {
		(hero.belongings.weapon = new Dagger()).identify();
		(hero.belongings.armor = new ClothArmor()).identify();
		(hero.belongings.ring1 = new RingOfShadows()).upgrade().identify();
		new Dart( 8 ).identify().collect();
		new Food().identify().collect();
		
		hero.belongings.ring1.activate( hero );
		
		if (Badges.isUnlocked( Badges.Badge.MASTERY_ROGUE )) {
			new TomeOfMastery().collect();
		}
		
		Dungeon.quickslot = Dart.class;
		
		new ScrollOfMagicMapping().setKnown();
	}
	
	private static void initHuntress( Hero hero ) {
		
		hero.HP = (hero.HT -= 5);
		
		(hero.belongings.weapon = new Dagger()).identify();
		(hero.belongings.armor = new ClothArmor()).identify();
		Boomerang boomerang = new Boomerang();
		boomerang.identify().collect();
		new Food().identify().collect();
		
		hero.belongings.ring1.activate( hero );
		
		if (Badges.isUnlocked( Badges.Badge.MASTERY_HUNTRESS )) {
			new TomeOfMastery().collect();
		}
		
		Dungeon.quickslot = boomerang;
	}
	private static void initPriest(Hero hero) {
		
		hero.HP = (hero.HT -= 4);
		
		(hero.belongings.weapon = new Club()).identify();
		(hero.belongings.armor = new ClothArmor()).identify();
		(hero.belongings.ring1 = new RingOfHerbalism()).upgrade().identify();
		new Food().identify().collect();
		new PotionOfHealing().setKnown();
		new Dart( 8 ).identify().collect();
		
		if (Badges.isUnlocked( Badges.Badge.MASTERY_PRIEST)){
			new TomeOfMastery().collect();
		}
			
			Dungeon.quickslot = Dart.class;
		}
	
	public String title() {
		return title;
	}
	
	public String spritesheet() {
		
		switch (this) {
		case WARRIOR:
			return Assets.WARRIOR;
		case MAGE:
			return Assets.MAGE;
		case ROGUE:
			return Assets.ROGUE;
		case HUNTRESS:
			return Assets.HUNTRESS;
		case PRIEST:
			return Assets.PRIEST;
		}
		
		return null;
	}
	
	public String[] perks() {
		
		switch (this) {
		case WARRIOR:
			return WAR_PERKS;
		case MAGE:
			return MAG_PERKS;
		case ROGUE:
			return ROG_PERKS;
		case HUNTRESS:
			return HUN_PERKS;
		case PRIEST:
			return PRIEST_PERKS;
		}
		
		return null;
	}

	private static final String CLASS	= "class";
	
	public void storeInBundle( Bundle bundle ) {
		bundle.put( CLASS, toString() );
	}
	
	public static HeroClass restoreInBundle( Bundle bundle ) {
		String value = bundle.getString( CLASS );
		return value.length() > 0 ? valueOf( value ) : ROGUE;
	}
}