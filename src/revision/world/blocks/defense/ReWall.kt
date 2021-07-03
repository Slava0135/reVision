package revision.world.blocks.defense

import mindustry.content.Fx
import mindustry.graphics.Pal
import mindustry.world.blocks.defense.Wall

open class ReWall(name: String) : Wall(name) {

    var fraction = 25f
    var reload = 60f

    init {
        canOverdrive = true
    }

    inner class ReWallBuild : WallBuild() {

        var charge = 0f

        override fun updateTile() {
            if (this.damaged()) {
                charge += delta()
                if (charge > reload) {
                    charge = 0f
                    heal(maxHealth / fraction)
                    Fx.healBlockFull.at(x, y, size.toFloat(), Pal.heal)
                }
            }
        }
    }

}