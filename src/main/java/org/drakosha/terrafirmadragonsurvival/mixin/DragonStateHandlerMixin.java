package org.drakosha.terrafirmadragonsurvival.mixin;

import net.minecraft.world.entity.player.Player;
import org.drakosha.terrafirmanutrients.Helpers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(by.dragonsurvivalteam.dragonsurvival.common.capability.DragonStateHandler.class)
public class DragonStateHandlerMixin {
    @Inject(method = "updateModifiers", at = @At("TAIL"), remap = false)
    void onUpdateModifiers(Player player, CallbackInfo ci) {
        Helpers.redefinePlayerNutritionData(player);
    }
}
