package revision.content

import arc.graphics.Color
import mindustry.type.Item

class ReItems {
    companion object {
        lateinit var snowball: Item

        fun load() {
            snowball = Item("snowball", Color.valueOf("FFFFFF")).apply {
                alwaysUnlocked = true
                lowPriority = true
            }
        }
    }
}