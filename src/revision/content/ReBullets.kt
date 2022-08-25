package revision.content

import arc.graphics.Color
import mindustry.content.Fx.shootSmall
import mindustry.content.Fx.shootSmallSmoke
import mindustry.content.StatusEffects.burning
import mindustry.content.StatusEffects.freezing
import mindustry.entities.bullet.BasicBulletType
import mindustry.entities.bullet.BulletType
import mindustry.entities.bullet.LiquidBulletType

class ReBullets {
    companion object {
        lateinit var scrapShredder: BulletType
        lateinit var coalShredder: BulletType
        lateinit var siliconShredder: BulletType
        lateinit var snowballShredder: BulletType
        lateinit var pollutedTsunami: BulletType
        lateinit var pollutedWave: BulletType

        fun load() {
            scrapShredder = object : BasicBulletType(3f, 4f) {}.apply {
                width = 5f
                height = 7f
                shootEffect = shootSmall
                smokeEffect = shootSmallSmoke
                ammoMultiplier = 8f
            }
            coalShredder = object : BasicBulletType(3f, 2f) {}.apply {
                width = 5f
                height = 7f
                frontColor = Color.orange
                backColor = Color.orange
                status = burning
                statusDuration = 60f
                shootEffect = shootSmall
                smokeEffect = shootSmallSmoke
                ammoMultiplier = 8f
            }
            siliconShredder = object : BasicBulletType(3f, 4f) {}.apply {
                width = 5f
                height = 7f
                shootEffect = shootSmall
                smokeEffect = shootSmallSmoke
                homingPower = 0.05f
                ammoMultiplier = 8f
            }
            snowballShredder = object : BasicBulletType(3f, 0f) {}.apply {
                width = 5f
                height = 7f
                frontColor = Color.valueOf("FFFFFF")
                backColor = Color.valueOf("FFFFFF")
                status = freezing
                statusDuration = 60f
                shootEffect = shootSmall
                smokeEffect = shootSmallSmoke
                ammoMultiplier = 8f
            }
            pollutedTsunami = object : LiquidBulletType() {}.apply {
                liquid = ReLiquids.pollutedWater
                statusDuration = 300f
                lifetime = 49f
                speed = 4f
                knockback = 1.7f
                puddleSize = 8f
                drag = 0.001f
                ammoMultiplier = 0.4f
                damage = 0.2f
            }
            pollutedWave = object : LiquidBulletType() {}.apply {
                liquid = ReLiquids.pollutedWater
                knockback = 0.7f
                statusDuration = 300f
            }
        }
    }
}