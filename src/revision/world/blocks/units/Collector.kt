package revision.world.blocks.units

import arc.Events
import arc.math.Mathf
import arc.math.geom.Geometry
import arc.struct.Seq
import arc.util.io.Reads
import mindustry.Vars.tilesize
import mindustry.content.Fx
import mindustry.content.Items
import mindustry.game.EventType
import mindustry.gen.Building
import mindustry.graphics.Drawf
import mindustry.graphics.Pal
import mindustry.type.Item
import mindustry.world.Block

open class Collector(name: String) : Block(name) {

    val existing = Seq<Building>()
    val loaded = Seq<Building>()

    val range = 100f

    init {
        solid = true
        update = true
        destructible = true

        Events.on(EventType.UnitDestroyEvent::class.java) { event ->
            val u = event.unit
            Geometry.findClosest(u.x, u.y, existing)?.let {
                if (u.within(it, range)) {
                    val amount = Mathf.clamp(u.hitSize, 0f, it.getMaximumAccepted(Items.scrap).toFloat())
                    Fx.itemTransfer.at(u.x, u.y, amount, Items.scrap.color, it)
                    it.items.add(Items.scrap, Mathf.ceil(amount))
                }
            }
        }

        Events.on(EventType.WorldLoadEvent::class.java) {
            existing.clear()
            existing.addAll(loaded)
            loaded.clear()
        }
    }

    override fun drawPlace(x: Int, y: Int, rotation: Int, valid: Boolean) {
        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, range, Pal.placing)
    }

    inner class CollectorBuild : Building() {

        override fun updateTile() {
            dump(Items.scrap)
        }

        override fun created() {
            super.created()
            existing.add(this)
        }

        override fun onRemoved() {
            existing.remove(this)
            super.onRemoved()
        }

        override fun drawSelect() {
            Drawf.dashCircle(x, y, range, team.color)
        }

        override fun acceptItem(source: Building?, item: Item?) = false

        override fun read(read: Reads?) {
            super.read(read)
            loaded.add(this)
        }
    }
}