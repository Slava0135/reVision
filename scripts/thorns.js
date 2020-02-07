const damage = 5;
const tileDamage = 25;
const cooldown = 30;
const thorns = extendContent(ShockMine, "thorns", {
    unitOn(tile, unit) {
        if (tile.entity.timer.get(0, cooldown)) {
            unit.damage(damage);
            tile.entity.damage(tileDamage);
        }
    }
});