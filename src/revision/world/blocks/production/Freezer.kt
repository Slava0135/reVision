package revision.world.blocks.production

import arc.Core
import arc.graphics.g2d.Draw
import arc.graphics.g2d.TextureRegion
import mindustry.content.Liquids
import mindustry.graphics.Drawf
import mindustry.type.Liquid
import mindustry.world.blocks.production.GenericCrafter
import mindustry.world.consumers.ConsumeType

open class Freezer(name: String) : GenericCrafter(name) {

    var liquidRegion: TextureRegion? = null
    var topRegion: TextureRegion? = null

    override fun load() {
        super.load()
        liquidRegion = Core.atlas.find("$name-liquid")
        topRegion = Core.atlas.find("$name-top")
    }

    inner class FreezerBuild : GenericCrafterBuild() {

        override fun draw() {
            super.draw()
            val liquid: Liquid = consumes.get(ConsumeType.liquid)
            Drawf.liquid(liquidRegion, x, y, liquids.get(liquid) / liquidCapacity, Liquids.water.color)
            Draw.rect(topRegion, x, y)
        }

    }
}