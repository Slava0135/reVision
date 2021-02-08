package revision.content

import mindustry.content.Fx.steam
import mindustry.content.Items
import mindustry.content.Liquids
import mindustry.ctype.ContentList
import mindustry.gen.Sounds.release
import mindustry.type.Category
import mindustry.type.ItemStack
import mindustry.type.ItemStack.with
import mindustry.type.LiquidStack
import mindustry.world.Block
import mindustry.world.blocks.production.GenericCrafter
import revision.world.blocks.production.Freezer

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