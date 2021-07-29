package revision.world.blocks.defense

import arc.Core
import arc.func.Floatp
import arc.func.Prov
import arc.math.Mathf
import mindustry.Vars
import mindustry.content.Fx
import mindustry.graphics.Pal
import mindustry.ui.Bar
import mindustry.world.blocks.defense.OverdriveProjector.OverdriveBuild
import mindustry.world.blocks.defense.Wall
import mindustry.world.meta.Attribute
import mindustry.world.meta.Stat
import mindustry.world.meta.StatUnit

open class ReWall(name: String) : Wall(name) {

    var fraction = 25f
    var reload = 60f

    init {
        canOverdrive = true
    }

    override fun setStats() {
        super.setStats()
        stats.add(Stat.health, (60f / reload) * (health / fraction), StatUnit.perSecond)
    }

    override fun setBars() {
        super.setBars()
        bars.add(
            "efficiency"
        ) { entity: OverdriveBuild ->
            Bar(
                { Core.bundle.format("bar.efficiency", (entity.realBoost() * 100).toInt()) },
                { Pal.accent },
                { entity.realBoost() })
        }
    }

    inner class ReWallBuild : WallBuild() {

        var charge = 0f

        override fun updateTile() {
            if (this.damaged()) {
                charge += delta()
                if (charge > reload) {
                    charge = 0f
                    heal(efficiency() * maxHealth / fraction)
                    Fx.healBlockFull.at(x, y, size.toFloat(), Pal.heal)
                }
            }
        }

        override fun efficiency() = Mathf.maxZero(Attribute.light.env() + if (Vars.state.rules.lighting) 1f - Vars.state.rules.ambientLight.a else 1f)
    }

}