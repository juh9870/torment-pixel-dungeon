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

public class Katars extends MeleeWeapon {

	{
		image = ItemSpriteSheet.KATARS;

		tier = 4;
		DLY = 0.5f; //2x speed
        ACC = 1.1f; //10% boost to accuracy
	}

	@Override
	public int max(int lvl) {
		return  3*(tier+1) +    //15 base, down from 25
				lvl*(tier-1);       //+3 per level, down from +5
	}

}
