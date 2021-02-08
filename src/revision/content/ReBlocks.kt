package revision.content

import mindustry.content.Fx.*
import mindustry.content.Items
import mindustry.content.Liquids
import mindustry.ctype.ContentList
import mindustry.gen.Sounds.door
import mindustry.gen.Sounds.release
import mindustry.type.Category
import mindustry.type.ItemStack
import mindustry.type.ItemStack.with
import mindustry.type.LiquidStack
import mindustry.world.Block
import mindustry.world.blocks.defense.turrets.ItemTurret
import mindustry.world.blocks.production.Drill
import mindustry.world.blocks.production.GenericCrafter
import revision.world.blocks.defense.ReWall
import revision.world.blocks.defense.Thorns
import revision.world.blocks.production.Freezer
import revision.world.blocks.production.Heater

class ReBlocks : ContentList {

    override fun load() {

        filter = object : GenericCrafter("filter") {
            init {
                requirements(Category.crafting, with(Items.copper, 125, Items.lead, 100, Items.graphite, 50, Items.titanium, 50))

                size = 3
                health = 180
                craftTime = 60f
                liquidCapacity = 240f
                craftEffect = steam
                ambientSound = release

                hasPower = true
                hasLiquids = true
                hasItems = true

                outputLiquid = LiquidStack(Liquids.water, 60f)
                outputItem = ItemStack(Items.thorium, 1)

                consumes.power(1f)
                consumes.item(Items.coal, 2)
                consumes.liquid(ReLiquids.pollutedWater, 1f)

                alwaysUnlocked = true
            }
        }

        freezer = object : Freezer("freezer") {
            init {
                requirements(Category.crafting, with(Items.copper, 125, Items.lead, 100, Items.graphite, 50, Items.metaglass, 50))

                size = 3
                health = 120
                craftTime = 30f
                liquidCapacity = 120f
                craftEffect = pickup
                ambientSound = release

                hasPower = true
                hasLiquids = true
                hasItems = true

                outputItem = ItemStack(ReItems.snowball, 3)

                consumes.power(1f)
                consumes.liquid(Liquids.water, 0.4f)

                alwaysUnlocked = true
            }
        }

        heater = object : Heater("heater") {
            init {
                requirements(Category.crafting, with(Items.copper, 75, Items.lead, 50, Items.metaglass, 50))

                size = 2
                health = 60
                craftTime = 20f
                liquidCapacity = 60f
                craftEffect = steam
                ambientSound = release

                hasPower = true
                hasLiquids = true
                hasItems = true

                outputLiquid = LiquidStack(Liquids.water, 4f)

                consumes.power(0.5f)
                consumes.item(ReItems.snowball, 1)

                alwaysUnlocked = true
            }
        }

        largeReWall = object : ReWall("large-rewall") {
            init {
                requirements(Category.defense, with(Items.silicon, 12, Items.lead, 8, Items.titanium, 4))

                size = 2
                update = true
                health = 2400

                alwaysUnlocked = true
            }
        }

        reWall = object : ReWall("rewall") {
            init {
                requirements(Category.defense, with(Items.silicon, 3, Items.lead, 2, Items.titanium, 1))

                size = 1
                update = true
                health = 600

                alwaysUnlocked = true
            }
        }

        scraper = object : Drill("scraper") {
            init {
                requirements(Category.production, with(Items.scrap, 4))

                size = 1
                health = 40
                tier = 2
                drillTime = 600f
                hasLiquids = false
                liquidBoostIntensity = 1f
                drawMineItem = false

                alwaysUnlocked = true
            }
        }

        shredder = object : ItemTurret("shredder") {
            init {
                requirements(Category.turret, with(Items.scrap, 35))

                ammo(
                    Items.scrap, ReBullets.scrapShredder,
                    Items.coal, ReBullets.coalShredder,
                    Items.silicon, ReBullets.siliconShredder,
                    ReItems.snowball, ReBullets.snowballShredder
                )

                size = 1
                health = 250
                range = 100f
                maxAmmo = 30
                inaccuracy = 30f
                rotateSpeed = 12f
                reloadTime = 1f

                alwaysUnlocked = true
            }
        }

        sieve = object : GenericCrafter("sieve") {
            init {
                requirements(Category.crafting, with(Items.copper, 35))

                size = 1
                health = 60
                craftTime = 60f
                craftEffect = pulverize

                hasPower = false
                hasLiquids = false
                hasItems = true

                outputItem = ItemStack(Items.scrap, 1)

                consumes.item(Items.sand, 4)

                alwaysUnlocked = true
            }
        }

        tainter = object : GenericCrafter("tainter") {
            init {
                requirements(Category.crafting, with(Items.copper, 75, Items.lead, 50, Items.titanium, 50))

                size = 2
                health = 120
                craftTime = 60f
                liquidCapacity = 60f
                ambientSound = door
                craftEffect = pickup

                hasPower = true
                hasLiquids = true
                hasItems = true

                outputLiquid = LiquidStack(ReLiquids.pollutedWater, 12f)

                consumes.power(0.5f)
                consumes.item(Items.sporePod, 1)
                consumes.liquid(Liquids.water, 0.2f)

                alwaysUnlocked = true
            }
        }

        thorns = object : Thorns("thorns") {
            init {
                requirements(Category.effect, with(Items.scrap, 4))

                hasShadow = false
                health = 100
                update = false
                destructible = true
                solid = false
                targetable = false
            }
        }
    }

    companion object {
        var filter: Block? = null
        var freezer: Block? = null
        var heater: Block? = null
        var largeReWall: Block? = null
        var reWall: Block? = null
        var scraper: Block? = null
        var shredder: Block? = null
        var sieve: Block? = null
        var tainter: Block? = null
        var thorns: Block? = null
    }
}