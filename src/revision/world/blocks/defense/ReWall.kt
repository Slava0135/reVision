package revision.world.blocks.defense

import mindustry.content.Fx
import mindustry.graphics.Pal
import mindustry.world.blocks.defense.Wall

open class ReWall(name: String) : Wall(name) {

    var fraction = 25f
    var cooldown = 60f

    inner class ReWallBuild : WallBuild() {
        override fun updateTile() {
            if (this.damaged() && timer.get(0, cooldown)) {
                heal(maxHealth / fraction)
                Fx.healBlockFull.at(x, y, size.toFloat(), Pal.heal)
            }
        }
    }

}