package revision.content

import arc.graphics.Color
import mindustry.content.StatusEffects.wet
import mindustry.ctype.ContentList
import mindustry.type.Liquid

class ReLiquids : ContentList {
    override fun load() {
        pollutedWater = object : Liquid("polluted-water") {
            init {
                temperature = 0.5f
                viscosity = 0.5f
                heatCapacity = 0.4f
                effect = wet
                color = Color.valueOf("645195")
            }
        }
        steam = object : Liquid("steam") {
            init {
                temperature = 1f
                viscosity = 1f
                heatCapacity = 0f
                effect = wet
                color = Color.valueOf("FFFFFF")
            }
        }
    }

    companion object {
        lateinit var pollutedWater: Liquid
        lateinit var steam: Liquid
    }
}