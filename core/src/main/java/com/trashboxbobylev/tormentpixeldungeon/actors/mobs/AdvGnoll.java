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

package com.trashboxbobylev.tormentpixeldungeon.actors.mobs;

import com.trashboxbobylev.tormentpixeldungeon.actors.Char;
import com.trashboxbobylev.tormentpixeldungeon.items.Gold;
import com.trashboxbobylev.tormentpixeldungeon.sprites.AdvGnollSprite;
import com.watabou.utils.Random;

public class AdvGnoll extends Mob {
	
	{
		spriteClass = AdvGnollSprite.class;
		
		HP = HT = 120;
		defenseSkill = 30;
		
		EXP = 16;
		maxLvl = 45;
		
		loot = Gold.class;
		lootChance = 0.5f;
	}
	
    @Override
    protected float attackDelay() {
        return 0.33f;
    }

	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 9, 18 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 48;
	}
	
	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 8);
	}
}
