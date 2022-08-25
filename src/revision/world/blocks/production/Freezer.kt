package revision.world.blocks.production

import arc.Core
import arc.graphics.g2d.Draw
import arc.graphics.g2d.TextureRegion
import mindustry.content.Liquids
import mindustry.graphics.Drawf
import mindustry.world.blocks.production.GenericCrafter

open class Freezer(name: String) : GenericCrafter(name) {

    lateinit var liquidRegion: TextureRegion
    lateinit var topRegion: TextureRegion

    override fun load() {
        super.load()
        liquidRegion = Core.atlas.find("$name-liquid")
        topRegion = Core.atlas.find("$name-top")
    }

    override fun icons(): Array<TextureRegion> {
        return arrayOf(region, topRegion)
    }

    inner class FreezerBuild : GenericCrafterBuild() {
        override fun draw() {
            super.draw()
            Drawf.liquid(liquidRegion, x, y, liquids.get(Liquids.water) / liquidCapacity, Liquids.water.color)
            Draw.rect(topRegion, x, y)
        }
    }
}