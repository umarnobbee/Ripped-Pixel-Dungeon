package com.ripped.pixeldungeon.items.armor;

import com.ripped.pixeldungeon.Assets;
import com.ripped.pixeldungeon.Dungeon;
import com.ripped.pixeldungeon.actors.Actor;
import com.ripped.pixeldungeon.actors.buffs.Buff;
import com.ripped.pixeldungeon.actors.buffs.Paralysis;
import com.ripped.pixeldungeon.actors.buffs.Poison;
import com.ripped.pixeldungeon.actors.hero.Hero;
import com.ripped.pixeldungeon.actors.hero.HeroClass;
import com.ripped.pixeldungeon.actors.mobs.Mob;
import com.ripped.pixeldungeon.effects.particles.ElmoParticle;
import com.ripped.pixeldungeon.levels.Level;
import com.ripped.pixeldungeon.sprites.ItemSpriteSheet;
import com.ripped.pixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class PriestArmor extends ClassArmor {
	
	private static final String AC_SPECIAL = "TIME STOPPER";
	
	private static final String TXT_NOT_PRIEST = "Only priests can use this armor!";
	
	{
		name = "priest tunic";
		image = ItemSpriteSheet.ARMOR_PRIEST;
	}
	
	@Override
	public String special () {
		return AC_SPECIAL;
	}
	
	@Override
	public String desc(){
		return
				"This tunic enchances the abilities of the priest. The priest can spell all enemies in view " +
				"to be paralysed and poisoned, althought it will take some of his life...";
	}
	
	@Override
	public void doSpecial(){
		for (Mob mob : Dungeon.level.mobs){
			if (Level.fieldOfView[mob.pos]){
				Buff.affect(mob, Poison.class);
				Buff.prolong ( mob, Paralysis.class, 3 );
			}
		}
				
		curUser.HP /= 2;
		
		curUser.spend( Actor.TICK );
		curUser.sprite.operate( curUser.pos );
		curUser.busy();
		
		curUser.sprite.centerEmitter().start( ElmoParticle.FACTORY, 0.15f, 4 );
		Sample.INSTANCE.play( Assets.SND_READ );
		
	}
	
	@Override
	public boolean doEquip (Hero  hero){
		if (hero.heroClass == HeroClass.PRIEST){
			return super.doEquip( hero );
		} else {
			GLog.w ( TXT_NOT_PRIEST );
			return false;
		}
	}	
}
