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

package com.trashboxbobylev.tormentpixeldungeon.levels.rooms.special;

import com.trashboxbobylev.tormentpixeldungeon.Dungeon;
import com.trashboxbobylev.tormentpixeldungeon.actors.hero.Belongings;
import com.trashboxbobylev.tormentpixeldungeon.actors.mobs.Mob;
import com.trashboxbobylev.tormentpixeldungeon.actors.mobs.npcs.Shopkeeper;
import com.trashboxbobylev.tormentpixeldungeon.items.Ankh;
import com.trashboxbobylev.tormentpixeldungeon.items.Bomb;
import com.trashboxbobylev.tormentpixeldungeon.items.Generator;
import com.trashboxbobylev.tormentpixeldungeon.items.Heap;
import com.trashboxbobylev.tormentpixeldungeon.items.Honeypot;
import com.trashboxbobylev.tormentpixeldungeon.items.Item;
import com.trashboxbobylev.tormentpixeldungeon.items.MerchantsBeacon;
import com.trashboxbobylev.tormentpixeldungeon.items.Stylus;
import com.trashboxbobylev.tormentpixeldungeon.items.Torch;
import com.trashboxbobylev.tormentpixeldungeon.items.Weightstone;
import com.trashboxbobylev.tormentpixeldungeon.items.armor.LeatherArmor;
import com.trashboxbobylev.tormentpixeldungeon.items.armor.MailArmor;
import com.trashboxbobylev.tormentpixeldungeon.items.armor.PlateArmor;
import com.trashboxbobylev.tormentpixeldungeon.items.armor.ScaleArmor;
import com.trashboxbobylev.tormentpixeldungeon.items.armor.CompositeArmor;
import com.trashboxbobylev.tormentpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.trashboxbobylev.tormentpixeldungeon.items.bags.Bag;
import com.trashboxbobylev.tormentpixeldungeon.items.bags.PotionBandolier;
import com.trashboxbobylev.tormentpixeldungeon.items.bags.ScrollHolder;
import com.trashboxbobylev.tormentpixeldungeon.items.bags.SeedPouch;
import com.trashboxbobylev.tormentpixeldungeon.items.bags.WandHolster;
import com.trashboxbobylev.tormentpixeldungeon.items.food.SmallRation;
import com.trashboxbobylev.tormentpixeldungeon.items.potions.Potion;
import com.trashboxbobylev.tormentpixeldungeon.items.potions.PotionOfHealing;
import com.trashboxbobylev.tormentpixeldungeon.items.scrolls.Scroll;
import com.trashboxbobylev.tormentpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.trashboxbobylev.tormentpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.trashboxbobylev.tormentpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.trashboxbobylev.tormentpixeldungeon.items.wands.Wand;
import com.trashboxbobylev.tormentpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.trashboxbobylev.tormentpixeldungeon.levels.Level;
import com.trashboxbobylev.tormentpixeldungeon.levels.Terrain;
import com.trashboxbobylev.tormentpixeldungeon.levels.painters.Painter;
import com.trashboxbobylev.tormentpixeldungeon.plants.Plant;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class ShopRoom extends SpecialRoom {

	private ArrayList<Item> itemsToSpawn;
	
	@Override
	public int minWidth() {
		if (itemsToSpawn == null) itemsToSpawn = generateItems();
		return Math.max(10, (int)(Math.sqrt(itemsToSpawn.size())+3));
	}
	
	@Override
	public int minHeight() {
		if (itemsToSpawn == null) itemsToSpawn = generateItems();
		return Math.max(10, (int)(Math.sqrt(itemsToSpawn.size())+3));
	}
	
	public void paint( Level level ) {
		
		Painter.fill( level, this, Terrain.WALL );
		Painter.fill( level, this, 1, Terrain.EMPTY_SP );

		placeShopkeeper( level );

		placeItems( level );
		
		for (Door door : connected.values()) {
			door.set( Door.Type.REGULAR );
		}

	}

	protected void placeShopkeeper( Level level ) {

		int pos = level.pointToCell(center());

		Mob shopkeeper = new Shopkeeper();
		shopkeeper.pos = pos;
		level.mobs.add( shopkeeper );

	}

	protected void placeItems( Level level ){

		if (itemsToSpawn == null)
			itemsToSpawn = generateItems();

		Point itemPlacement = new Point(entrance());
		if (itemPlacement.y == top){
			itemPlacement.y++;
		} else if (itemPlacement.y == bottom) {
			itemPlacement.y--;
		} else if (itemPlacement.x == left){
			itemPlacement.x++;
		} else {
			itemPlacement.x--;
		}

		for (Item item : itemsToSpawn) {

			if (itemPlacement.x == left+1 && itemPlacement.y != top+1){
				itemPlacement.y--;
			} else if (itemPlacement.y == top+1 && itemPlacement.x != right-1){
				itemPlacement.x++;
			} else if (itemPlacement.x == right-1 && itemPlacement.y != bottom-1){
				itemPlacement.y++;
			} else {
				itemPlacement.x--;
			}

			int cell = level.pointToCell(itemPlacement);

			if (level.heaps.get( cell ) != null) {
				do {
					cell = level.pointToCell(random());
				} while (level.heaps.get( cell ) != null || level.findMob( cell ) != null);
			}

			level.drop( item, cell ).type = Heap.Type.FOR_SALE;
		}

	}
	
	protected static ArrayList<Item> generateItems() {

		ArrayList<Item> itemsToSpawn = new ArrayList<>();
		
        itemsToSpawn.add( shopItem(Generator.Category.WEAPON) );
			itemsToSpawn.add( shopItem(Generator.Category.WEAPON));
			itemsToSpawn.add( shopItem(Generator.Category.ARMOR) );

		itemsToSpawn.add( new MerchantsBeacon() );


		itemsToSpawn.add(ChooseBag(Dungeon.hero.belongings));


		itemsToSpawn.add( new PotionOfHealing() );
		for (int i=0; i < 3; i++)
			itemsToSpawn.add( Generator.random( Generator.Category.POTION ) );

		itemsToSpawn.add( new ScrollOfIdentify() );
		itemsToSpawn.add( new ScrollOfRemoveCurse() );
		itemsToSpawn.add( new ScrollOfMagicMapping() );
		itemsToSpawn.add( Generator.random( Generator.Category.SCROLL ) );

		for (int i=0; i < 2; i++)
			itemsToSpawn.add( Random.Int(2) == 0 ?
					Generator.random( Generator.Category.POTION ) :
					Generator.random( Generator.Category.SCROLL ) );


		itemsToSpawn.add( new SmallRation() );
		itemsToSpawn.add( new SmallRation() );

		itemsToSpawn.add( new Bomb().random() );
		switch (Random.Int(5)){
			case 1:
				itemsToSpawn.add( new Bomb() );
				break;
			case 2:
				itemsToSpawn.add( new Bomb().random() );
				break;
			case 3:
			case 4:
				itemsToSpawn.add( new Honeypot() );
				break;
		}


		if (Dungeon.depth == 6) {
			itemsToSpawn.add( new Ankh() );
			itemsToSpawn.add( new Weightstone() );
		} else {
			itemsToSpawn.add(Random.Int(2) == 0 ? new Ankh() : new Weightstone());
		}


		TimekeepersHourglass hourglass = Dungeon.hero.belongings.getItem(TimekeepersHourglass.class);
		if (hourglass != null){
			int bags = 0;
			//creates the given float percent of the remaining bags to be dropped.
			//this way players who get the hourglass late can still max it, usually.
			switch (Dungeon.depth) {
				case 6:
					bags = (int)Math.ceil(( 5-hourglass.sandBags) * 0.20f ); break;
				case 11:
					bags = (int)Math.ceil(( 5-hourglass.sandBags) * 0.25f ); break;
				case 16:
					bags = (int)Math.ceil(( 5-hourglass.sandBags) * 0.50f ); break;
				case 21:
					bags = (int)Math.ceil(( 5-hourglass.sandBags) * 0.80f ); break;
			}

			for(int i = 1; i <= bags; i++){
				itemsToSpawn.add( new TimekeepersHourglass.sandBag());
				hourglass.sandBags ++;
			}
		}

		Item rare;
		switch (Random.Int(10)){
			case 0:
				rare = shopItem( Generator.Category.WAND );
				break;
			case 1:
				rare = shopItem(Generator.Category.RING);
				break;
			case 2:
				rare = Generator.random( Generator.Category.ARTIFACT );
				break;
			default:
				rare = new Stylus();
		}
		rare.cursed = rare.cursedKnown = false;
		itemsToSpawn.add( rare );

		//hard limit is 63 items + 1 shopkeeper, as shops can't be bigger than 8x8=64 internally
		if (itemsToSpawn.size() > 100)
			throw new RuntimeException("Shop attempted to carry more than 100 items!");

		Random.shuffle(itemsToSpawn);
		return itemsToSpawn;
	}

	protected static Bag ChooseBag(Belongings pack){

		int seeds = 0, scrolls = 0, potions = 0, wands = 0;

		//count up items in the main bag, for bags which haven't yet been dropped.
		for (Item item : pack.backpack.items) {
			if (!Dungeon.LimitedDrops.SEED_POUCH.dropped() && item instanceof Plant.Seed)
				seeds++;
			else if (!Dungeon.LimitedDrops.SCROLL_HOLDER.dropped() && item instanceof Scroll)
				scrolls++;
			else if (!Dungeon.LimitedDrops.POTION_BANDOLIER.dropped() && item instanceof Potion)
				potions++;
			else if (!Dungeon.LimitedDrops.WAND_HOLSTER.dropped() && item instanceof Wand)
				wands++;
		}

		//then pick whichever valid bag has the most items available to put into it.
		//note that the order here gives a perference if counts are otherwise equal
		if (seeds >= scrolls && seeds >= potions && seeds >= wands && !Dungeon.LimitedDrops.SEED_POUCH.dropped()) {
			Dungeon.LimitedDrops.SEED_POUCH.drop();
			return new SeedPouch();

		} else if (scrolls >= potions && scrolls >= wands && !Dungeon.LimitedDrops.SCROLL_HOLDER.dropped()) {
			Dungeon.LimitedDrops.SCROLL_HOLDER.drop();
			return new ScrollHolder();

		} else if (potions >= wands && !Dungeon.LimitedDrops.POTION_BANDOLIER.dropped()) {
			Dungeon.LimitedDrops.POTION_BANDOLIER.drop();
			return new PotionBandolier();

		} else if (!Dungeon.LimitedDrops.WAND_HOLSTER.dropped()) {
			Dungeon.LimitedDrops.WAND_HOLSTER.drop();
			return new WandHolster();
		}

		return null;
	}

    private static Item shopItem(Generator.Category cat){
        Item itemForShop;

        do {
            itemForShop = Generator.random(cat).identify();
        } while ((itemForShop.level() < 2 && itemForShop.cursed) || (itemForShop instanceof MissileWeapon));
        
        return itemForShop;
    }

}
