package revision.world.blocks.defense

import arc.graphics.g2d.Draw
import arc.graphics.g2d.Fill
import mindustry.gen.Building
import mindustry.gen.Unit
import mindustry.world.Block

open class Thorns(name: String) : Block(name) {

    val timerDamage = timers++

    var cooldown = 30f
    var damage = 8f
    var tileDamage = 25f

    inner class ThornsBuild : Building() {

        override fun draw() {
            super.draw()
            Draw.color(team.color)
            Draw.alpha(0.22f)
            Fill.rect(x, y, 2f, 2f)
            Draw.color()
        }

        override fun unitOn(unit: Unit?) {
            if (timer.get(timerDamage, cooldown)) {
                unit!!.damage(damage)
                damage(tileDamage)
            }
        }
    }

}