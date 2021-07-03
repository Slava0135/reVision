package revision.content

import arc.graphics.Color
import mindustry.ctype.ContentList
import mindustry.type.Item

class ReItems : ContentList {
    override fun load() {
        snowball = object : Item("snowball", Color.valueOf("FFFFFF")) {}.apply {
            alwaysUnlocked = true
            lowPriority = true
        }
    }

    companion object {
        lateinit var snowball: Item
    }
}