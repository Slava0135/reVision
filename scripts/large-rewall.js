const fraction = 25
const cooldown = 60;

const largeReWall = extendContent(Wall, "large-rewall", {});

largeReWall.buildType = () => extendContent(Wall.WallBuild, largeReWall, {
	updateTile() {
    	if (this.damaged() & this.timer.get(0, cooldown)) {
            this.heal(this.maxHealth / fraction);
            Fx.healBlockFull.at(this.x, this.y, largeReWall.size, Pal.heal);
        }
    }
});