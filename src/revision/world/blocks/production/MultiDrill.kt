package revision.world.blocks.production

import arc.Core
import arc.graphics.Blending
import arc.graphics.Color
import arc.graphics.g2d.Draw
import arc.graphics.g2d.Lines
import arc.graphics.g2d.TextureRegion
import arc.math.Mathf
import arc.math.geom.Point2
import arc.struct.ObjectFloatMap
import arc.struct.ObjectIntMap
import arc.util.Time
import mindustry.Vars
import mindustry.content.Fx
import mindustry.game.Team
import mindustry.gen.Building
import mindustry.gen.Sounds
import mindustry.graphics.Pal
import mindustry.type.Item
import mindustry.world.Block
import mindustry.world.Edges
import mindustry.world.Tile
import mindustry.world.meta.BlockGroup
import mindustry.world.meta.Stat
import mindustry.world.meta.StatUnit

open class MultiDrill(name: String) : Block(name) {

    val oreCount = ObjectIntMap<Item>()

    val hardnessDrillMultiplier = 50f
    val drillTime = 280f
    val liquidBoostIntensity = 1.8f

    val warmupSpeed = 0.01f
    val rotateSpeed = 6f

    val drillEffect = Fx.mineHuge
    val updateEffect = Fx.pulverizeRed
    val updateEffectChance = 0.03f

    val heatColor = Color.valueOf("ff5512")

    lateinit var rimRegion: TextureRegion
    lateinit var rotatorRegion: TextureRegion
    lateinit var topRegion: TextureRegion

    init {
        update = true
        solid = true
        group = BlockGroup.drills
        hasLiquids = true
        hasItems = true
        ambientSound = Sounds.drill
        ambientSoundVolume = 0.018f
    }

    override fun load() {
        super.load()
        rimRegion = Core.atlas.find("$name-rim")
        rotatorRegion = Core.atlas.find("$name-rotator")
        topRegion = Core.atlas.find("$name-top")
    }

    override fun icons(): Array<TextureRegion> {
        return arrayOf(region, rotatorRegion, topRegion)
    }

    override fun canPlaceOn(tile: Tile?, team: Team?, rotation: Int): Boolean {
        if (tile != null) {
            for (other in tile.getLinkedTilesAs(this, tempTiles)) {
                if (canMine(other)) {
                    return true
                }
            }
            for (edge in Edges.getInsideEdges(size + 2)) {
                val other = Vars.world.tile(tile.x + edge.x, tile.y + edge.y)
                if (canMine(other)) {
                    return true
                }
            }
        }
        return false
    }

    override fun drawPlace(x: Int, y: Int, rotation: Int, valid: Boolean) {
        super.drawPlace(x, y, rotation, valid)

        val tile = Vars.world.tile(x, y) ?: return
        countOre(tile)

        var off = 0
        for (ore in oreCount.keys()) {
            val dx: Float = x * Vars.tilesize + offset - 16
            val dy = y * Vars.tilesize + offset + size * Vars.tilesize / 2f
            Draw.mixcol(Color.darkGray, 1f)
            val itemRegion = ore.fullIcon
            Draw.rect(itemRegion, dx + off, dy - 1)
            Draw.reset()
            Draw.rect(itemRegion, dx + off, dy)
            off += 8
        }
        Draw.reset()

        Draw.color(Pal.placing)
        Lines.stroke(size.toFloat() / 2)
        Lines.square(x * Vars.tilesize + offset, y * Vars.tilesize + offset, (Vars.tilesize / 2f) * (size + 2).toFloat())
    }

    fun countOre(tile: Tile) {
        oreCount.clear()

        for (other in tile.getLinkedTilesAs(this, tempTiles)) {
            if (canMine(other)) {
                oreCount.increment(other.drop(), 0, 1)
            }
        }

        val bot = (-((size - 1) / 2f)).toInt() - 1
        val top = ((size - 1) / 2f + 0.5f).toInt() + 1

        val edges = Edges.getEdges(size) + arrayOf(Point2(bot, bot), Point2(bot, top), Point2(top, top), Point2(top, bot))
        for (edge in edges) {
            val other = Vars.world.tile(tile.x + edge.x, tile.y + edge.y)
            if (canMine(other)) {
                oreCount.increment(other.drop(), 0, 1)
            }
        }
    }

    fun canMine(tile: Tile?) = tile?.drop() != null

    override fun setStats() {
        super.setStats()
        stats.add(Stat.drillSpeed, 60f / drillTime * size * size, StatUnit.itemsSecond)
        stats.add(Stat.boostEffect, liquidBoostIntensity * liquidBoostIntensity, StatUnit.timesSpeed)
    }

    inner class MultiDrillBuild : Building() {

        val ores = ObjectIntMap<Item>()
        val oreProgress = ObjectFloatMap<Item>()

        var timeDrilled = 0f
        var warmup = 0f

        override fun shouldAmbientSound() = efficiency > 0.01f && items.total() < itemCapacity

        override fun ambientVolume() = efficiency * (size * size) / 4f

        override fun drawSelect() {
            var off = 0
            for (ore in ores.keys()) {
                val dx = x - size * Vars.tilesize / 2f
                val dy = y + size * Vars.tilesize / 2f
                Draw.mixcol(Color.darkGray, 1f)
                val itemRegion = ore.fullIcon
                Draw.rect(itemRegion, dx + off, dy - 1)
                Draw.reset()
                Draw.rect(itemRegion, dx + off, dy)
                off += 8
            }
            Draw.reset()
            Draw.color(Pal.placing)
            Lines.stroke(size.toFloat() / 2)
            Lines.square(tileX() * Vars.tilesize + offset, tileY() * Vars.tilesize + offset, (Vars.tilesize / 2f) * (size + 2).toFloat())
        }

        override fun drawCracks() {}

        override fun onProximityUpdate() {
            countOre(tile)
            ores.clear()
            oreProgress.clear()
            for (ore in oreCount) {
                ores.put(ore.key, ore.value)
            }
        }

        override fun updateTile() {
            if (ores.isEmpty) return

            if (timer(timerDump, dumpTime.toFloat())) {
                items.each { item, _ -> dump(item) }
            }

            timeDrilled += warmup * delta()

            if (items.total() < ores.size * itemCapacity && canConsume()) {
                val speed = Mathf.lerp(1f, liquidBoostIntensity, optionalEfficiency) * efficiency
                warmup = Mathf.approachDelta(warmup, speed, warmupSpeed)

                for (ore in ores) {
                    oreProgress.increment(ore.key, 0f, delta() * ore.value * speed * warmup)
                }

                if (Mathf.chanceDelta((updateEffectChance * warmup).toDouble())) {
                    updateEffect.at(x + Mathf.range(size * 2f), y + Mathf.range(size * 2f))
                }
            } else {
                warmup = Mathf.lerpDelta(warmup, 0f, warmupSpeed)
                return
            }

            for (ore in ores) {
                val delay = drillTime + hardnessDrillMultiplier * ore.key.hardness
                if (oreProgress.get(ore.key, 0f) >= delay && items.get(ore.key) < itemCapacity) {
                    offload(ore.key)
                    oreProgress.increment(ore.key, 0f, -delay)
                    drillEffect.at(x + Mathf.range(size), y + Mathf.range(size), ore.key.color)
                }
            }
        }

        override fun draw() {
            val s = 0.3f
            val ts = 0.6f

            Draw.rect(region, x, y)
            super.drawCracks()

            Draw.color(heatColor)
            Draw.alpha(warmup * ts * (1f - s + Mathf.absin(Time.time, 3f, s)))
            Draw.blend(Blending.additive)
            Draw.rect(rimRegion, x, y)
            Draw.blend()
            Draw.color()

            Draw.rect(rotatorRegion, x, y, timeDrilled * rotateSpeed)

            Draw.rect(topRegion, x, y)
        }
    }
}