/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2017 Evan Debenham
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

package com.trashboxbobylev.tormentpixeldungeon.items.weapon.melee;

import com.trashboxbobylev.tormentpixeldungeon.sprites.ItemSpriteSheet;

public class ChlorateEruption extends MeleeWeapon {

	{
		image = ItemSpriteSheet.CHLORATE_ERUPTION;

		tier = 7;
		ACC = 0.6f; //0.6x accuracy
		//also cannot surprise attack, see Hero.canSurpriseAttack
	}

	@Override
	public int max(int lvl) {
		return  Math.round(7*(tier+1)) +        //56 base, up from 40
				lvl*Math.round(1.5f*(tier+1));  //+12 per level, up from +8
	}
}
