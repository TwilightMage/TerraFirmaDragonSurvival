package org.drakosha.terrafirmadragonsurvival.mixin;

import by.dragonsurvivalteam.dragonsurvival.common.dragon_types.types.ForestDragonType;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static by.dragonsurvivalteam.dragonsurvival.common.capability.Capabilities.DRAGON_CAPABILITY;

@Mixin(org.drakosha.terrafirmanutrients.Helpers.class)
public class NutrientsHelpersMixin {
    @Inject(method = "canEatRottenFood", at = @At("HEAD"), cancellable = true, remap = false)
    private static void onCanEatRottenFood(Player player, CallbackInfoReturnable<Boolean> cir) {
        player.getCapability(DRAGON_CAPABILITY).ifPresent(handler -> {
            if (handler.getType() instanceof ForestDragonType) {
                cir.setReturnValue(true);
                cir.cancel();
            }
        });
    }
}
