package revision.content

import arc.graphics.Color
import mindustry.ctype.ContentList
import mindustry.type.Item

class ReItems : ContentList {
    override fun load() {
        snowball = object : Item("snowball", Color.valueOf("FFFFFF")) {
            init {
                alwaysUnlocked = true
                lowPriority = true
            }
        }
    }

    companion object {
        var snowball: Item? = null
    }
}