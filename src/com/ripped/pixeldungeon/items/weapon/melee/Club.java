package com.ripped.pixeldungeon.items.weapon.melee;

import com.ripped.pixeldungeon.items.weapon.enchantments.Healing;
import com.ripped.pixeldungeon.sprites.ItemSpriteSheet;

public class Club extends MeleeWeapon {
	
	{
		name = "club";
		image = ItemSpriteSheet.CLUB;
	}
	
	public Club () {
		super (1, 1f, 1f);
		enchant(new Healing());
	}
	
	@Override
	public String desc () {
		return "Made by a cleric, this club possesses healing abilities. It is very firm.";
	}
}