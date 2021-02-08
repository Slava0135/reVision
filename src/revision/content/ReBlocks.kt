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
            }
        }

        freezer = object : Freezer("freezer") {
            init {

            }
        }

        heater = object : Heater("heater") {
            init {

            }
        }

        largeReWall = object : ReWall("large-rewall") {
            init {

            }
        }

        reWall = object : ReWall("rewall") {
            init {

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
            }
        }

        shredder = object : ItemTurret("shredder") {
            init {
                requirements(Category.defense, with(Items.scrap, 35))

                ammo(
                    Items.scrap, ReBullets.scrapShredder,
                    Items.coal, ReBullets.coalShredder,
                    Items.silicon, ReBullets.siliconShredder,
                    ReItems.snowball, ReBullets.snowballShredder
                )

                size = 1
                health = 250
                range = 100f
                ammoUseEffect = null
                maxAmmo = 30
                inaccuracy = 30f
                rotateSpeed = 12f
                reloadTime = 1f
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

                outputItem = ItemStack(Items.copper, 1)

                consumes.item(Items.sand, 4)
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
            }
        }

        thorns = object : Thorns("thorns") {

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