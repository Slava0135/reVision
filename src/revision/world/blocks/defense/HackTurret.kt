package revision.world.blocks.defense

import arc.Core
import arc.graphics.Color
import arc.graphics.g2d.Draw
import arc.graphics.g2d.TextureRegion
import arc.math.Angles
import arc.math.Mathf
import arc.struct.Seq
import arc.util.Timer
import arc.util.io.Reads
import arc.util.io.Writes
import mindustry.Vars
import mindustry.entities.Units
import mindustry.entities.Units.Sortf
import mindustry.gen.Healthc
import mindustry.gen.Sounds
import mindustry.gen.Unit
import mindustry.graphics.Drawf
import mindustry.graphics.Layer
import mindustry.world.blocks.defense.turrets.BaseTurret
import mindustry.world.meta.Stat
import mindustry.world.meta.StatUnit

open class HackTurret(name: String) : BaseTurret(name) {

    val targets = Seq<Unit>()

    lateinit var baseRegion: TextureRegion
    lateinit var laser: TextureRegion
    lateinit var laserEnd: TextureRegion

    var shootCone = 6f
    var shootLength = 5f
    var laserWidth = 0.6f
    var damage = 0.5f
    var targetAir = true
    var targetGround = true
    var laserColor = Color.white
    var shootSound = Sounds.tractorbeam
    var shootSoundVolume = 0.9f

    var unitSort = Sortf { obj: Unit, x: Float, y: Float -> obj.dst2(x, y) }

    init {
        rotateSpeed = 10f
        canOverdrive = true

        Timer.schedule({ targets.removeAll { it.dead() } }, 0f, 1f)
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
        stats.add(Stat.damage, 60f * damage, StatUnit.perSecond)
    }

    override fun icons(): Array<TextureRegion> {
        return arrayOf(baseRegion, region)
    }

    inner class HackBuild : BaseTurretBuild() {

        var target: Unit? = null
        var lastX = 0f
        var lastY = 0f
        var progress = 0f
        var normalProgress = 0f

        override fun updateTile() {
            if (validateTarget()) {

                if (!Vars.headless) {
                    Vars.control.sound.loop(shootSound, this, shootSoundVolume)
                }

                val dest = angleTo(target)
                rotation = Angles.moveToward(rotation, dest, rotateSpeed * edelta())

                lastX = target!!.x
                lastY = target!!.y

                if (Angles.within(rotation, dest, shootCone)) {
                    progress += edelta() * damage
                    normalProgress = progress / (target as Healthc).maxHealth()
                    if (progress > (target as Healthc).maxHealth()) {
                        target!!.team(team())
                        reset()
                    }
                } else {
                    reset()
                }
            } else {
                reset()
                findTarget()
            }
        }

        private fun findTarget() {
            target =
                Units.bestEnemy(
                    team, x, y, range,
                    { e: Unit -> !e.dead() && (e.isGrounded || targetAir) && (!e.isGrounded || targetGround) && !e.spawnedByCore && e !in targets },
                    unitSort
                )
            target?.let {
                targets.add(it)
                lastX = target!!.x
                lastY = target!!.y
            }
        }

        private fun reset() {
            progress = 0f
            targets.remove(target)
            target = null
        }

        private fun validateTarget(): Boolean {
            return !Units.invalidateTarget(target, team, x, y, range) && efficiency > 0.02f
        }

        override fun onRemoved() {
            targets.remove(target)
            super.onRemoved()
        }

        override fun draw() {
            drawTurret()
            if (target != null) {
                drawLaser()
                drawProgress()
            }
        }

        private fun drawTurret() {
            Draw.rect(baseRegion, x, y)
            Drawf.shadow(region, x - size / 2f, y - size / 2f, rotation - 90)
            Draw.rect(region, x, y, rotation - 90)
        }

        private fun drawLaser() {
            Draw.z(Layer.bullet)
            val ang = angleTo(lastX, lastY)
            Draw.mixcol(laserColor, Mathf.absin(4f, 0.6f))
            Drawf.laser(
                laser, laserEnd,
                x + Angles.trnsx(ang, shootLength), y + Angles.trnsy(ang, shootLength),
                lastX, lastY, efficiency * laserWidth
            )
            Draw.mixcol()
        }

        private fun drawProgress() {
            Drawf.target(lastX, lastY, (target as Unit).hitSize, normalProgress, team.color)
        }

        override fun write(write: Writes) {
            super.write(write)
            write.f(rotation)
        }

        override fun read(read: Reads, revision: Byte) {
            super.read(read, revision)
            rotation = read.f()
        }

        override fun shouldConsume() = target != null
    }
}