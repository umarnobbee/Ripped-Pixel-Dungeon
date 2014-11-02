package com.ripped.pixeldungeon.items.food;

import com.ripped.pixeldungeon.actors.buffs.Bleeding;
import com.ripped.pixeldungeon.actors.buffs.Buff;
import com.ripped.pixeldungeon.actors.buffs.Cripple;
import com.ripped.pixeldungeon.actors.buffs.Hunger;
import com.ripped.pixeldungeon.actors.buffs.Invisibility;
import com.ripped.pixeldungeon.actors.buffs.Levitation;
import com.ripped.pixeldungeon.actors.buffs.Poison;
import com.ripped.pixeldungeon.actors.buffs.Weakness;
import com.ripped.pixeldungeon.actors.hero.Hero;
import com.ripped.pixeldungeon.sprites.ItemSpriteSheet;
import com.ripped.pixeldungeon.utils.GLog;
import com.watabou.utils.Random;

public class AgedMeat extends Food {
	
	{
		name = "shocked meat";
		image = ItemSpriteSheet.SHOCKEDMEAT;
		energy = Hunger.STARVING - Hunger.HUNGRY;
	}
	@Override
	public void execute( Hero hero, String action ) {
		
		super.execute( hero, action );
		
		if (action.equals( AC_EAT )) {
			
			switch (Random.Int( 3 )) {
			case 0:
				GLog.i( "Yummy!!" );
				Buff.detach( hero, Poison.class );
				Buff.detach( hero, Cripple.class );
				Buff.detach( hero, Weakness.class );
				Buff.detach( hero, Bleeding.class );
				break;
			case 1:
				GLog.i( "Too much energy..feels like I'm floating...");
				Buff.affect( hero, Levitation.class, Levitation.DURATION );
				break;
			case 2:
				GLog.i("Agh..I got shocked...can't see myself..");
				Buff.affect( hero, Invisibility.class, Invisibility.DURATION );
				break;
		
			}}
		}
	
	@Override
	public String info () {
		return "This meat has aged many years..Maybe I can try it....";  
	}
	
	@Override
	public int price () {
		return 5 *quantity;
	}
	
}
