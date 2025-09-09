package com.theendercore.outlined

import net.fabricmc.fabric.api.event.player.AttackEntityCallback
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import net.minecraft.world.phys.EntityHitResult
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress("unused")
object Outlined {
    const val MODID = "outlined"

    @JvmField
    val log: Logger = LoggerFactory.getLogger(MODID)

    fun init() {
        AttackEntityCallback.EVENT.register { player, world, hand, entity, hitResult ->
            if (debugUse(player, world, hand, entity, hitResult)) InteractionResult.SUCCESS
            else InteractionResult.PASS
        }
//        UseEntityCallback.EVENT.register { player, world, hand, entity, hitResult ->
//            if ()
//        }
    }

    private fun debugUse(
        player: Player,
        world: Level,
        hand: InteractionHand,
        entity: Entity,
        hitResult: EntityHitResult?,
    ): Boolean {
        val stack = player.mainHandItem
        if (stack.isEmpty || !stack.`is`(Items.STICK)) return false

        if (!world.isClientSide) {
            entity.setGlowingTag(!entity.isCurrentlyGlowing)
//            player.sendSystemMessage(Component.literal("Glowing: ${entity.isCurrentlyGlowing}"))
        }

        return true
    }

    fun id(path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(MODID, path)
}
