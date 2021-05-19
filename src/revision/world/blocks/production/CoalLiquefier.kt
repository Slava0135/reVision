package revision.world.blocks.production

import arc.Core
import arc.graphics.g2d.TextureRegion
import mindustry.content.Liquids
import mindustry.graphics.Drawf
import mindustry.world.blocks.production.GenericCrafter

open class CoalLiquefier(name: String) : GenericCrafter(name) {

    var liquidRegion: TextureRegion? = null

    override fun load() {
        super.load()
        liquidRegion = Core.atlas.find("$name-liquid")
    }

    override fun icons(): Array<TextureRegion> {
        return arrayOf(region)
    }

    inner class CoalLiquefierBuild : GenericCrafterBuild() {

        override fun draw() {
            super.draw()
            Drawf.liquid(liquidRegion, x, y, liquids.get(outputLiquid.liquid) / liquidCapacity, Liquids.water.color)
        }

    }
}