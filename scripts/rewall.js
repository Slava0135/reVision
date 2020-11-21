const fraction = 25
const cooldown = 60;

const reWall = extendContent(Wall, "rewall", {});

reWall.buildType = () => extendContent(Wall.WallBuild, reWall, {
	updateTile() {
		if (this.damaged() & this.timer.get(0, cooldown)) {
            this.heal(this.maxHealth / fraction);
            Fx.healBlockFull.at(this.x, this.y, reWall.size, Pal.heal);
        }
	}
});