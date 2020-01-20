health = 2400;
const heal = health / 50;
const cooldown = 30;
const largeReWall = extendContent(Wall, "large-rewall", {
	update(tile) {
		if (tile.entity.health() + heal < tile.entity.maxHealth() & tile.entity.timer.get(0, cooldown)) {
			tile.entity.health += heal;
		} else tile.entity.health = tile.entity.maxHealth();
	}
});

largeReWall.localizedName = "Large reWall";
