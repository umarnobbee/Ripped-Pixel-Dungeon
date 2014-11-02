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
package com.ripped.pixeldungeon.levels.features;

import com.ripped.pixeldungeon.Dungeon;
import com.ripped.pixeldungeon.actors.Char;
import com.ripped.pixeldungeon.actors.buffs.Barkskin;
import com.ripped.pixeldungeon.actors.buffs.Buff;
import com.ripped.pixeldungeon.actors.hero.Hero;
import com.ripped.pixeldungeon.actors.hero.HeroSubClass;
import com.ripped.pixeldungeon.effects.CellEmitter;
import com.ripped.pixeldungeon.effects.particles.LeafParticle;
import com.ripped.pixeldungeon.items.Dewdrop;
import com.ripped.pixeldungeon.items.Generator;
import com.ripped.pixeldungeon.items.rings.RingOfHerbalism.Herbalism;
import com.ripped.pixeldungeon.levels.Level;
import com.ripped.pixeldungeon.levels.Terrain;
import com.ripped.pixeldungeon.scenes.GameScene;
import com.watabou.utils.Random;

public class HighGrass {

	public static void trample( Level level, int pos, Char ch ) {
		
		Level.set( pos, Terrain.GRASS );
		GameScene.updateMap( pos );
		
		int herbalismLevel = 0;
		if (ch != null) {
			Herbalism herbalism = ch.buff( Herbalism.class );
			if (herbalism != null) {
				herbalismLevel = herbalism.level;
			}
		}
		
		// Seed
		if (herbalismLevel >= 0 && Random.Int( 18 ) <= Random.Int( herbalismLevel + 1 )) {
			level.drop( Generator.random( Generator.Category.SEED ), pos ).sprite.drop();
		}
		
		// Dew
		if (herbalismLevel >= 0 && Random.Int( 6 ) <= Random.Int( herbalismLevel + 1 )) {
			level.drop( new Dewdrop(), pos ).sprite.drop();
		}
		
		int leaves = 4;
		
		// Barkskin
		if (ch instanceof Hero && ((Hero)ch).subClass == HeroSubClass.WARDEN) {
			Buff.affect( ch, Barkskin.class ).level( ch.HT / 3 );
			leaves = 8;
		}
		if (ch instanceof Hero && ((Hero)ch).subClass == HeroSubClass.DRUID) {
			Buff.affect( ch, Barkskin.class ).level( ch.HT / 3 );
			leaves = 8;
		
		CellEmitter.get( pos ).burst( LeafParticle.LEVEL_SPECIFIC, leaves );
		Dungeon.observe();
	}
	}}
