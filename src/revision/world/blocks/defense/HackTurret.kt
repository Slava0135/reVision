package revision.world.blocks.defense

import arc.Core
import arc.graphics.Color
import arc.graphics.g2d.Draw
import arc.graphics.g2d.TextureRegion
import arc.math.Angles
import arc.math.Mathf
import arc.util.io.Reads
import arc.util.io.Writes
import mindustry.gen.Sounds
import mindustry.graphics.Drawf
import mindustry.graphics.Layer
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
        acceptCoolant = true
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

        var target: Unit? = null
        var lastX = 0f; var lastY = 0f
        var any = false
        var progress = 0f;

        override fun draw() {
            Draw.rect(baseRegion, x, y)
            Drawf.shadow(region, x - size / 2f, y - size / 2f, rotation - 90)
            Draw.rect(region, x, y, rotation - 90)

            if (any) {
                Draw.z(Layer.bullet)
                val ang = angleTo(lastX, lastY)
                Draw.mixcol(laserColor, Mathf.absin(4f, 0.6f))
                Drawf.laser(
                    team, laser, laserEnd,
                    x + Angles.trnsx(ang, shootLength), y + Angles.trnsy(ang, shootLength),
                    lastX, lastY, efficiency() * laserWidth
                )
                Draw.mixcol()
            }
        }

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