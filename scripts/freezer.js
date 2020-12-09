const freezer = extendContent(GenericCrafter, "freezer", {
	load() {
		this.super$load();
		this.regions = [];
		this.regions[0] = Core.atlas.find(this.name);
		this.regions[1] = Core.atlas.find(this.name + "-liquid");
		this.regions[2] = Core.atlas.find(this.name + "-top");
	}
});

freezer.buildType = () => extendContent(GenericCrafter.GenericCrafterBuild, freezer, {
	draw() {
	    Draw.rect(freezer.regions[0], this.x, this.y);
	    Drawf.liquid(freezer.regions[1], this.x, this.y, this.liquids.total() / this.liquidCapacity, this.liquids.current().color);
	    Draw.rect(freezer.regions[2], this.x, this.y);
	}
});