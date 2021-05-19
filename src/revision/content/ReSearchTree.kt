package revision.content

import arc.struct.Seq
import mindustry.content.Blocks.*
import mindustry.content.Items.*
import mindustry.content.TechTree
import mindustry.content.TechTree.TechNode
import mindustry.ctype.ContentList
import mindustry.ctype.UnlockableContent
import mindustry.game.Objectives.Objective
import mindustry.game.Objectives.Produce
import mindustry.type.ItemStack


class ReSearchTree : ContentList {

    override fun load() {

        margeNode(blastDrill) {
            node(ReBlocks.multiDrill)
        }

        margeNode(waterExtractor) {
            node(ReBlocks.freezer)
            node(ReBlocks.heater)
        }

        margeNode(cultivator) {
            node(ReBlocks.tainter)
            node(ReBlocks.filter)
        }

        margeNode(graphite) {
            node(ReBlocks.windTurbine)
        }

        margeNode(oilExtractor) {
            node(ReBlocks.coalLiquefier)
        }

        margeNode(mendProjector) {
            node(ReBlocks.reWall) {
                node(ReBlocks.largeReWall)
            }
        }

        margeNode(container) {
            node(ReBlocks.collector)
        }

        margeNode(mechanicalDrill) {
            node(ReBlocks.scraper)
            node(ReBlocks.sieve)
        }

        margeNode(duo) {
            node(ReBlocks.shredder)
            node(ReBlocks.thorns)
        }
    }

    companion object {

        var context: TechNode? = null

        private fun margeNode(parent: UnlockableContent, children: Runnable) {
            val parnode = TechTree.all.find { t: TechNode -> t.content === parent }
            context = parnode
            children.run()
        }

        private fun node(
            content: UnlockableContent,
            requirements: Array<ItemStack>,
            objectives: Seq<Objective>?,
            children: Runnable
        ) {
            val node = TechNode(context, content, requirements)
            if (objectives != null) node.objectives = objectives
            val prev = context
            context = node
            children.run()
            context = prev
        }

        private fun node(content: UnlockableContent, requirements: Array<ItemStack>, children: Runnable) {
            node(content, requirements, null, children)
        }

        private fun node(content: UnlockableContent, objectives: Seq<Objective>, children: Runnable) {
            node(content, content.researchRequirements(), objectives, children)
        }

        private fun node(content: UnlockableContent, children: Runnable) {
            node(content, content.researchRequirements(), children)
        }

        private fun node(block: UnlockableContent) {
            node(block) {}
        }

        private fun nodeProduce(
            content: UnlockableContent,
            objectives: Seq<Objective> = Seq.with(),
            children: Runnable = Runnable {}
        ) {
            node(content, content.researchRequirements(), objectives.and(Produce(content)), children)
        }

        private fun nodeProduce(content: UnlockableContent, children: Runnable) {
            nodeProduce(content, Seq.with(), children)
        }
    }
}