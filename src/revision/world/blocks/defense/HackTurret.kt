package revision.world.blocks.defense

import arc.Core
import arc.graphics.Color
import arc.graphics.g2d.TextureRegion
import arc.util.io.Reads
import arc.util.io.Writes
import mindustry.gen.Sounds
import mindustry.world.blocks.defense.turrets.BaseTurret
import mindustry.world.meta.Stat
import mindustry.world.meta.StatUnit

open class HackTurret(name: String) : BaseTurret(name) {

    lateinit var baseRegion: TextureRegion
    lateinit var laser: TextureRegion
    lateinit var laserEnd: TextureRegion

    var shootCone = 6f
    var shootLength = 5f
    var laserWidth = 0.6f
    var damage = 10
    var targetAir = true
    var targetGround = true
    var laserColor = Color.white
    var shootSound = Sounds.tractorbeam
    var shootSoundVolume = 0.9f

    init {
        rotateSpeed = 10f
        coolantMultiplier = 1f
        acceptCoolant = false
        expanded = true
    }

    override fun load() {
        super.load()
        baseRegion = Core.atlas.find("block-$size")
        laser = Core.atlas.find("$name-laser")
        laserEnd = Core.atlas.find("$name-laser-end")
    }

    override fun setStats() {
        super.setStats()
        stats.add(Stat.targetsAir, targetAir)
        stats.add(Stat.targetsGround, targetGround)
        stats.add(Stat.damage, damage * 60f, StatUnit.perSecond)
    }

    override fun icons(): Array<TextureRegion> {
        return arrayOf(baseRegion, region)
    }

    inner class HackBuild : BaseTurretBuild() {
        override fun write(write: Writes) {
            super.write(write)
            write.f(rotation)
        }

        override fun read(read: Reads, revision: Byte) {
            super.read(read, revision)
            rotation = read.f()
        }
    }
}