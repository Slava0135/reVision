health = 2400;
const heal = health / 50;
const cooldown = 60;
const largeReWall = extendContent(Wall, "large-rewall", {
	update(tile) {
		if (tile.entity.health() < tile.entity.maxHealth() & tile.entity.timer.get(0, cooldown)) {
			if (tile.entity.health() + heal < tile.entity.maxHealth()) {
				tile.entity.health += heal;
			} else tile.entity.health = tile.entity.maxHealth();
		}
	}
});