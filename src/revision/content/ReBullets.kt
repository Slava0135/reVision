package revision.content

import arc.graphics.Color
import mindustry.content.Fx.shootSmall
import mindustry.content.Fx.shootSmallSmoke
import mindustry.content.StatusEffects.burning
import mindustry.content.StatusEffects.freezing
import mindustry.ctype.ContentList
import mindustry.entities.bullet.BasicBulletType
import mindustry.entities.bullet.BulletType
import mindustry.entities.bullet.LiquidBulletType

class ReBullets : ContentList {

    override fun load() {

        scrapShredder = object : BasicBulletType(3f, 4f) {
            init {
                width = 5f
                height = 7f
                shootEffect = shootSmall
                smokeEffect = shootSmallSmoke
                ammoMultiplier = 10f
            }
        }

        coalShredder = object : BasicBulletType(3f, 2f) {
            init {
                width = 5f
                height = 7f
                frontColor = Color.orange
                backColor = Color.orange
                status = burning
                statusDuration = 50f
                shootEffect = shootSmall
                smokeEffect = shootSmallSmoke
                ammoMultiplier = 10f
            }
        }

        siliconShredder = object : BasicBulletType(3f, 4f) {
            init {
                width = 5f
                height = 7f
                shootEffect = shootSmall
                smokeEffect = shootSmallSmoke
                homingPower = 0.05f
                ammoMultiplier = 10f
            }
        }

        snowballShredder = object : BasicBulletType(3f, 0f) {
            init {
                width = 5f
                height = 7f
                frontColor = Color.valueOf("FFFFFF")
                backColor = Color.valueOf("FFFFFF")
                status = freezing
                statusDuration = 50f
                shootEffect = shootSmall
                smokeEffect = shootSmallSmoke
                ammoMultiplier = 10f
            }
        }

        pollutedTsunami = object : LiquidBulletType() {
            init {
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
        }

        pollutedWave = object : LiquidBulletType() {
            init {
                liquid = ReLiquids.pollutedWater
                knockback = 0.7f
                statusDuration = 300f
            }
        }
    }

    companion object {
        var scrapShredder: BulletType? = null
        var coalShredder: BulletType? = null
        var siliconShredder: BulletType? = null
        var snowballShredder: BulletType? = null
        var pollutedTsunami: BulletType? = null
        var pollutedWave: BulletType? = null
    }
}