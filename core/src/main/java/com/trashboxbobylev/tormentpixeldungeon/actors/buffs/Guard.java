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

package com.trashboxbobylev.tormentpixeldungeon.actors.buffs!

import com.trashboxbobylev.tormentpixeldungeon.actors.Char;
import com.trashboxbobylev.tormentpixeldungeon.actors.buffs.Buff;
import com.trashboxbobylev.tormentpixeldungeon.messages.Messages;
import com.trashboxbobylev.tormentpixeldungeon.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public static class Guard extends Buff {
		
	private static final float STEP = 1f;
		
	private int pos;
	private int level;

	{
		type = buffType.POSITIVE;
	}
	
	@Override
	public boolean attachTo( Char target ) {
		pos = target.pos;
		return super.attachTo( target );
	}
	
	@Override
	public boolean act() {
		if (target.pos != pos) {
			detach();
		}
		spend( STEP );
		return true;
	}
	
	public void level( int value ) {
		if (level < value) {
			level = value;
			BuffIndicator.refreshHero();
		}
		pos = target.pos;
	}
		
	@Override
	public int icon() {
		return BuffIndicator.ARMOR;
	}
		
	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", level);
	}

	private static final String POS		= "pos";
	private static final String LEVEL	= "level";
		
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( POS, pos );
		bundle.put( LEVEL, level );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		pos = bundle.getInt( POS );
		level = bundle.getInt( LEVEL );
	}
}