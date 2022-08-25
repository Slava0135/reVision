package revision.world.blocks.defense

import arc.Core
import arc.math.Mathf
import mindustry.Vars
import mindustry.content.Fx
import mindustry.graphics.Pal
import mindustry.ui.Bar
import mindustry.world.blocks.defense.Wall
import mindustry.world.meta.Attribute
import mindustry.world.meta.Stat
import mindustry.world.meta.StatUnit

open class ReWall(name: String) : Wall(name) {

    var healRatio = 1 / 25f
    var healInterval = 60f

    init {
        canOverdrive = true
    }

    override fun setStats() {
        super.setStats()
        stats.add(Stat.health, (60f / healInterval) * (healRatio * health), StatUnit.perSecond)
    }

    override fun setBars() {
        super.setBars()
        addBar("efficiency") { entity: ReWallBuild ->
            Bar(
                { Core.bundle.formatFloat("bar.efficiency", 100 * entity.efficiency(), 1) },
                { Pal.ammo },
                { entity.efficiency() }
            )
        }
    }

    inner class ReWallBuild : WallBuild() {
        var timeElapsed = 0f

        override fun updateTile() {
            if (this.damaged()) {
                timeElapsed += delta()
                if (timeElapsed > healInterval) {
                    timeElapsed = 0f
                    heal(efficiency() * healRatio * maxHealth)
                    Fx.healBlockFull.at(x, y, block.size.toFloat(), Pal.heal, this.block)
                }
            }
        }

        override fun efficiency() =
            Mathf.maxZero(Attribute.light.env() + if (Vars.state.rules.lighting) 1f - Vars.state.rules.ambientLight.a else 1f)
    }

}