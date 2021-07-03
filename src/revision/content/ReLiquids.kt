package revision.content

import arc.graphics.Color
import mindustry.content.StatusEffects.wet
import mindustry.ctype.ContentList
import mindustry.type.Liquid

class ReLiquids : ContentList {
    override fun load() {
        pollutedWater = object : Liquid("polluted-water") {}.apply {
            temperature = 0.5f
            viscosity = 0.5f
            heatCapacity = 0.4f
            effect = wet
            color = Color.valueOf("645195")
        }
    }

    companion object {
        lateinit var pollutedWater: Liquid
    }
}