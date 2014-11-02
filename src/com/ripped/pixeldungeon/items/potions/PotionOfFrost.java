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
package com.ripped.pixeldungeon.items.potions;

import com.ripped.pixeldungeon.Assets;
import com.ripped.pixeldungeon.Dungeon;
import com.ripped.pixeldungeon.actors.blobs.Fire;
import com.ripped.pixeldungeon.actors.blobs.Freezing;
import com.ripped.pixeldungeon.levels.Level;
import com.ripped.pixeldungeon.utils.BArray;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class PotionOfFrost extends Potion {
	
	private static final int DISTANCE	= 2;
	
	{
		name = "Potion of Frost";
	}
	
	@Override
	protected void shatter( int cell ) {
		
		PathFinder.buildDistanceMap( cell, BArray.not( Level.losBlocking, null ), DISTANCE );
		
		Fire fire = (Fire)Dungeon.level.blobs.get( Fire.class );
		
		for (int i=0; i < Level.LENGTH; i++) {
			if (PathFinder.distance[i] < Integer.MAX_VALUE) {
				Freezing.affect( i, fire );
			}
		}
		
		splash( cell );
		Sample.INSTANCE.play( Assets.SND_SHATTER );
		
		setKnown();
	}
	
	@Override
	public String desc() {
		return 
			"Upon exposure to open air this chemical will evaporate into a freezing cloud, causing " +
			"any creature that contacts it to be frozen in place unable to act and move.";
	}
	
	@Override
	public int price() {
		return isKnown() ? 50 * quantity : super.price();
	}
}
