package com.ripped.pixeldungeon.items.weapon.melee;

import com.ripped.pixeldungeon.actors.Char;
import com.ripped.pixeldungeon.actors.buffs.Buff;
import com.ripped.pixeldungeon.actors.buffs.Burning;
import com.ripped.pixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class PyroBlazefury extends MeleeWeapon {
	
	{
		name = "Pyro Blazefury";
		image = ItemSpriteSheet.PYRO_BLAZEFURY;
	}

	public PyroBlazefury () {
		super (1, 1f, 1f);
	}
	@Override
	public void proc( Char attacker, Char defender, int damage ) {
		if (Random.Int( 4 ) == 0) {
		Buff.affect( defender, Burning.class ).reignite( defender );
		super.proc( attacker, defender, damage );
	}}
	@Override
	public String  desc () {
		return "This hatchet has a blade with a hot edge, also extremely heavy. It sparks whenever it hits a surface.";
	}
}
