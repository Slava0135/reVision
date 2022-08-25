package revision.content

import mindustry.content.Fx
import mindustry.content.Items
import mindustry.content.Liquids
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
import revision.world.blocks.defense.HackTurret
import revision.world.blocks.defense.ReWall
import revision.world.blocks.defense.Thorns
import revision.world.blocks.power.WindTurbine
import revision.world.blocks.production.CoalLiquefier
import revision.world.blocks.production.Freezer
import revision.world.blocks.production.Heater
import revision.world.blocks.production.MultiDrill
import revision.world.blocks.units.Collector

class ReBlocks {
    companion object {
        lateinit var filter: Block
        lateinit var freezer: Block
        lateinit var heater: Block
        lateinit var largeReWall: Block
        lateinit var reWall: Block
        lateinit var scraper: Block
        lateinit var shredder: Block
        lateinit var sieve: Block
        lateinit var tainter: Block
        lateinit var thorns: Block
        lateinit var windTurbine: Block
        lateinit var collector: Block
        lateinit var multiDrill: Block
        lateinit var coalLiquefier: Block
        lateinit var hacker: Block

        fun load() {
            filter = object : GenericCrafter("filter") {}.apply {
                requirements(
                    Category.crafting,
                    with(Items.copper, 125, Items.lead, 100, Items.graphite, 50, Items.titanium, 50)
                )
                size = 3
                craftTime = 60f
                liquidCapacity = 120f
                craftEffect = Fx.steam
                ambientSound = release
                hasPower = true
                hasLiquids = true
                hasItems = true
                outputLiquid = LiquidStack(Liquids.water, 60f)
                outputItem = ItemStack(Items.thorium, 1)
                consumePower(1f)
                consumeItem(Items.coal, 2)
                consumeLiquid(ReLiquids.pollutedWater, 1f)
            }

            freezer = object : Freezer("freezer") {}.apply {
                requirements(
                    Category.crafting,
                    with(Items.copper, 125, Items.lead, 100, Items.graphite, 50, Items.metaglass, 50)
                )
                size = 3
                craftTime = 30f
                liquidCapacity = 120f
                craftEffect = Fx.pickup
                ambientSound = release
                hasPower = true
                hasLiquids = true
                hasItems = true
                outputItem = ItemStack(ReItems.snowball, 3)
                consumePower(0.5f)
                consumeLiquid(Liquids.water, 0.4f)
            }

            heater = object : Heater("heater") {}.apply {
                requirements(Category.crafting, with(Items.copper, 75, Items.lead, 50, Items.metaglass, 50))
                size = 2
                craftTime = 20f
                liquidCapacity = 60f
                craftEffect = Fx.steam
                ambientSound = release
                hasPower = true
                hasLiquids = true
                hasItems = true
                outputLiquid = LiquidStack(Liquids.water, 4f)
                consumePower(0.25f)
                consumeItem(ReItems.snowball, 1)
            }

            largeReWall = object : ReWall("large-rewall") {}.apply {
                requirements(Category.defense, with(Items.silicon, 12, Items.lead, 8, Items.titanium, 4))
                size = 2
                update = true
                health = 2000
            }

            reWall = object : ReWall("rewall") {}.apply {
                requirements(Category.defense, with(Items.silicon, 3, Items.lead, 2, Items.titanium, 1))
                size = 1
                update = true
                health = 500
            }

            scraper = object : Drill("scraper") {}.apply {
                requirements(Category.production, with(Items.scrap, 4))
                size = 1
                tier = 2
                drillTime = 600f
                hasLiquids = false
                liquidBoostIntensity = 1f
                drawMineItem = false
            }

            shredder = object : ItemTurret("shredder") {}.apply {
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
                reload = 2f
            }

            sieve = object : GenericCrafter("sieve") {}.apply {
                requirements(Category.crafting, with(Items.copper, 35))
                size = 1
                craftTime = 60f
                craftEffect = Fx.pulverize
                hasPower = false
                hasLiquids = false
                hasItems = true
                outputItem = ItemStack(Items.scrap, 1)
                consumeItem(Items.sand, 4)
            }

            tainter = object : GenericCrafter("tainter") {}.apply {
                requirements(Category.crafting, with(Items.copper, 75, Items.lead, 50, Items.titanium, 50))
                size = 2
                craftTime = 60f
                liquidCapacity = 60f
                ambientSound = door
                craftEffect = Fx.pickup
                hasPower = true
                hasLiquids = true
                hasItems = true
                outputLiquid = LiquidStack(ReLiquids.pollutedWater, 12f)
                consumePower(0.5f)
                consumeItem(Items.sporePod, 1)
                consumeLiquid(Liquids.water, 0.2f)
            }

            thorns = object : Thorns("thorns") {}.apply {
                requirements(Category.effect, with(Items.scrap, 4))
                hasShadow = false
                health = 100
                update = false
                destructible = true
                solid = false
                targetable = false
            }

            windTurbine = object : WindTurbine("wind-turbine") {}.apply {
                requirements(Category.power, with(Items.copper, 35, Items.lead, 35, Items.graphite, 35))
                size = 2
                powerProduction = 0.5f
            }

            collector = object : Collector("collector") {}.apply {
                requirements(Category.effect, with(Items.titanium, 100))
                hasItems = true
                size = 2
                itemCapacity = 300
            }

            multiDrill = object : MultiDrill("multi-drill") {}.apply {
                requirements(
                    Category.production,
                    with(Items.copper, 130, Items.silicon, 120, Items.graphite, 100, Items.phaseFabric, 75)
                )
                size = 4
                hasPower = true
                consumePower(6f)
                consumeLiquid(Liquids.water, 0.2f).boost()
            }

            coalLiquefier = object : CoalLiquefier("coal-liquefier") {}.apply {
                requirements(
                    Category.crafting,
                    with(Items.copper, 150, Items.graphite, 175, Items.lead, 115, Items.thorium, 115, Items.silicon, 75)
                )
                size = 3
                craftTime = 60f
                liquidCapacity = 240f
                craftEffect = Fx.steam
                ambientSound = release
                outputLiquid = LiquidStack(Liquids.oil, 15f)
                hasPower = true
                hasLiquids = true
                hasItems = true
                consumePower(4f)
                consumeItem(Items.coal, 5)
                consumeLiquid(Liquids.water, 0.25f)
            }

            hacker = object : HackTurret("hacker") {}.apply {
                requirements(Category.turret, with(Items.silicon, 120, Items.titanium, 90, Items.graphite, 30))
                hasPower = true
                size = 2
                range = 240f
                damage = 0.5f
                health = 160 * size * size
                rotateSpeed = 10f
                consumePower(3f)
            }
        }
    }
}