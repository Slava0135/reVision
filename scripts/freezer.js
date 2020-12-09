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
	    var liquid = freezer.consumes.get(ConsumeType.liquid).liquid;
	    Drawf.liquid(freezer.regions[1], this.x, this.y, this.liquids.get(liquid) / freezer.liquidCapacity, Liquids.water.color);
	    Draw.rect(freezer.regions[2], this.x, this.y);
	}
});