const sec = 60;
damage = 10 / sec;
tileDamage = 10 / sec;
const thorns = extendContent(ShockMine, "thorns", {
	unitOn(tile, unit) {
		unit.damage(damage);
		tile.entity.damage(tileDamage);
	}
});