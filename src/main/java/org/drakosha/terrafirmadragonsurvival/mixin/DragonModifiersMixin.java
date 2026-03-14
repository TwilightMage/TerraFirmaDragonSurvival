package org.drakosha.terrafirmadragonsurvival.mixin;

import by.dragonsurvivalteam.dragonsurvival.registry.DragonModifiers;
import net.minecraft.world.entity.player.Player;
import org.drakosha.terrafirmanutrients.Helpers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DragonModifiers.class)
public class DragonModifiersMixin {
    @Inject(method = "updateTypeModifiers", at = @At("TAIL"), remap = false)
    private static void onUpdateTypeModifiers(Player player, CallbackInfo ci) {
        Helpers.redefinePlayerNutritionData(player);
    }
}
