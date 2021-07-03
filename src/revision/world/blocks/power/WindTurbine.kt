package revision.world.blocks.power

import arc.Core
import arc.graphics.g2d.Draw
import arc.graphics.g2d.Fill
import arc.graphics.g2d.Lines
import arc.graphics.g2d.TextureRegion
import arc.math.geom.Point2
import arc.struct.EnumSet
import mindustry.Vars
import mindustry.Vars.tilesize
import mindustry.graphics.Drawf
import mindustry.graphics.Pal
import mindustry.world.Edges
import mindustry.world.blocks.power.PowerGenerator
import mindustry.world.meta.StatUnit

open class WindTurbine(name: String) : PowerGenerator(name) {

    lateinit var fanRegion: TextureRegion
    lateinit var topRegion: TextureRegion

    override fun load() {
        super.load()
        fanRegion = Core.atlas.find("$name-fan")
        topRegion = Core.atlas.find("$name-top")
    }

    init {
        flags = EnumSet.of()
    }

    override fun drawPlace(x: Int, y: Int, rotation: Int, valid: Boolean) {
        Draw.color(Pal.placing)
        Lines.stroke(size.toFloat())
        Lines.square(x * tilesize + offset, y * tilesize + offset, (tilesize / 2f) * (size + 4).toFloat())
    }

    override fun setStats() {
        super.setStats()
        stats.remove(generationType)
        stats.add(generationType, powerProduction * 60f, StatUnit.powerSecond)
    }

    override fun icons(): Array<TextureRegion> {
        return arrayOf(region, fanRegion, topRegion)
    }

    inner class WindTurbineBuild : GeneratorBuild() {

        var time = 0f
        var count = 0

        override fun updateTile() {
            count = (count + 1) % 60
            if (count == 1) {
                val bot = (-((size - 1) / 2f)).toInt() - 1
                val top = ((size - 1) / 2f + 0.5f).toInt() + 1
                val edges = Edges.getEdges(size) + Edges.getEdges(size + 2) +
                        arrayOf(Point2(bot, bot), Point2(bot, top), Point2(top, top), Point2(top, bot)) +
                        arrayOf(Point2(bot-1, bot-1), Point2(bot-1, top+1), Point2(top+1, top+1), Point2(top+1, bot-1))
                val base = edges.size
                val occupied = edges.count {
                    Vars.world.tile(tile.x + it.x, tile.y + it.y).solid()
                }
                productionEfficiency = if (enabled) 1f - occupied.toFloat() / base else 0f
            }
            time += delta() * productionEfficiency
        }

        override fun drawCracks() {}

        override fun draw() {
            Draw.rect(region, x, y)
            super.drawCracks()
            Draw.rect(fanRegion, x, y, time)
            Draw.rect(topRegion, x, y)
        }

        override fun drawSelect() {
            drawPlace(tileX(), tileY(), 0, true)
        }
    }
}