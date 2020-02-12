health = 600;
const heal = health / 50;
const cooldown = 60;
const reWall = extendContent(Wall, "rewall", {
	update(tile) {
		if (tile.entity.health() < tile.entity.maxHealth() & tile.entity.timer.get(0, cooldown)) {
			tile.entity.healBy(heal);
			Effects.effect(Fx.healBlockFull, Tmp.c1.set(Color.valueOf("84f491")), tile.drawx(), tile.drawy(), tile.block().size);
		}
	}
});