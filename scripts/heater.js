const heater = extendContent(GenericCrafter, "heater", {
	load() {
		this.super$load();
		this.regions = [];
		this.regions[0] = Core.atlas.find(this.name);
		this.regions[1] = Core.atlas.find(this.name + "-liquid");
		this.regions[2] = Core.atlas.find(this.name + "-top");
	}
});

heater.buildType = () => extendContent(GenericCrafter.GenericCrafterBuild, heater, {
	draw() {
	    Draw.rect(heater.regions[0], this.x, this.y);
	    //Drawf.liquid(heater.regions[1], this.x, this.y, this.liquids.get(outputLiquid.liquid) / this.liquidCapacity, this.outputLiquid.color);
	    Draw.rect(heater.regions[2], this.x, this.y);
	}
});