package revision

import mindustry.content.Blocks
import mindustry.mod.Mod
import mindustry.world.blocks.defense.turrets.LiquidTurret
import mindustry.world.blocks.environment.Floor
import mindustry.world.meta.BuildVisibility
import revision.content.*

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
        Blocks.scrapWall.alwaysUnlocked = true
        Blocks.scrapWallHuge.buildVisibility = BuildVisibility.shown
        Blocks.scrapWallHuge.alwaysUnlocked = true
        Blocks.scrapWallLarge.buildVisibility = BuildVisibility.shown
        Blocks.scrapWallLarge.alwaysUnlocked = true
        Blocks.scrapWallGigantic.buildVisibility = BuildVisibility.shown
        Blocks.scrapWallGigantic.alwaysUnlocked = true

        (Blocks.tsunami as LiquidTurret).ammoTypes.put(ReLiquids.pollutedWater, ReBullets.pollutedTsunami)
        (Blocks.wave as LiquidTurret).ammoTypes.put(ReLiquids.pollutedWater, ReBullets.pollutedTsunami)
    }
}