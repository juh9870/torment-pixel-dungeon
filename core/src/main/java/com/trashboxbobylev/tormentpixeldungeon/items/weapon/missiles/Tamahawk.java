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

package com.trashboxbobylev.tormentpixeldungeon.items.weapon.missiles;

import com.trashboxbobylev.tormentpixeldungeon.actors.Char;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.Bleeding;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.Buff;
import com.trashboxbobylev.tormentpixeldungeon.items.Item;
import com.trashboxbobylev.tormentpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Tamahawk extends MissileWeapon {

	{
		image = ItemSpriteSheet.TOMAHAWK;

	}

	@Override
	public int min(int lvl) {
		return 4;
	}

	@Override
	public int max(int lvl) {
		return 20;
	}

	@Override
	public int STRReq(int lvl) {
		return 17;
	}

	public Tamahawk() {
		this( 1 );
	}
	
	public Tamahawk( int number ) {
		super();
		quantity = number;
	}
	
	@Override
	public int proc( Char attacker, Char defender, int damage ) {
		Buff.affect( defender, Bleeding.class ).set( damage );
		return super.proc( attacker, defender, damage );
	}
	
	@Override
	public Item random() {
		quantity = Random.Int( 10, 23 );
		return this;
	}
	
	@Override
	public int price() {
		return 7 * quantity;
	}
}
