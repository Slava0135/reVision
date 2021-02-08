package revision

import mindustry.content.Blocks
import mindustry.mod.Mod
import mindustry.world.blocks.environment.Floor
import mindustry.world.meta.BuildVisibility
import revision.content.ReBlocks
import revision.content.ReBullets
import revision.content.ReItems
import revision.content.ReLiquids

class ReVision : Mod() {
    override fun loadContent() {
        ReLiquids().load()
        ReItems().load()
        ReBullets().load()
        ReBlocks().load()

        (Blocks.darksandTaintedWater as Floor).liquidDrop = ReLiquids.pollutedWater
        (Blocks.taintedWater as Floor).liquidDrop = ReLiquids.pollutedWater
        (Blocks.iceSnow as Floor).itemDrop = ReItems.snowball
        (Blocks.snow as Floor).itemDrop = ReItems.snowball

        Blocks.scrapWall.buildVisibility = BuildVisibility.shown
        Blocks.scrapWallHuge.buildVisibility = BuildVisibility.shown
        Blocks.scrapWallLarge.buildVisibility = BuildVisibility.shown
        Blocks.scrapWallGigantic.buildVisibility = BuildVisibility.shown
    }
}