package org.drakosha.terrafirmadragonsurvival;

import by.dragonsurvivalteam.dragonsurvival.common.dragon_types.types.CaveDragonType;
import by.dragonsurvivalteam.dragonsurvival.common.dragon_types.types.ForestDragonType;
import by.dragonsurvivalteam.dragonsurvival.common.dragon_types.types.SeaDragonType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import org.drakosha.terrafirmanutrients.*;
import net.minecraft.ChatFormatting;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;

import static by.dragonsurvivalteam.dragonsurvival.common.capability.Capabilities.DRAGON_CAPABILITY;
import static org.drakosha.terrafirmadragonsurvival.TerraFirmaDragonSurvival.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Nutrients {
    public static final DeferredRegister<Nutrient> NUTRIENTS = DeferredRegister.create(NutrientRegistry.REGISTRY_KEY, TerraFirmaDragonSurvival.MODID);

    public static final ResourceLocation NUTRIENT_SET_CAVE_DRAGON = ResourceLocation.fromNamespaceAndPath(MODID, "cave_dragon");
    public static final ResourceLocation NUTRIENT_SET_FOREST_DRAGON = ResourceLocation.fromNamespaceAndPath(MODID, "forest_dragon");
    public static final ResourceLocation NUTRIENT_SET_SEA_DRAGON = ResourceLocation.fromNamespaceAndPath(MODID, "sea_dragon");

    public static final RegistryObject<Nutrient> METALS = NUTRIENTS.register("metals", () -> new DragonNutrient(new Color(177, 161, 161), 0.5f));
    public static final RegistryObject<Nutrient> FLUX = NUTRIENTS.register("flux", () -> new DragonNutrient(new Color(205, 186, 96), 0.5f));
    public static final RegistryObject<Nutrient> SILICON = NUTRIENTS.register("silicon", () -> new DragonNutrient(new Color(218, 218, 218), 0.5f));
    public static final RegistryObject<Nutrient> CARBON = NUTRIENTS.register("carbon", () -> new DragonNutrient(new Color(43, 43, 43), 0.5f));
    public static final RegistryObject<Nutrient> IRRADIANTS = NUTRIENTS.register("irradiants", () -> new DragonNutrient(new Color(179, 0, 0), 0.0f));
    public static final RegistryObject<Nutrient> LAVA = NUTRIENTS.register("lava", () -> new DragonNutrient(new Color(255, 93, 0), 0.5f));

    public static final RegistryObject<Nutrient> GLUCOSE = NUTRIENTS.register("glucose", () -> new DragonNutrient(new Color(232, 232, 232), 0.5f));
    public static final RegistryObject<Nutrient> TOXINS = NUTRIENTS.register("toxins", () -> new DragonNutrient(new Color(41, 204, 0), 0.5f));
    public static final RegistryObject<Nutrient> PHOSPHATES = NUTRIENTS.register("phosphates", () -> new DragonNutrient(new Color(118, 170, 170), 0.5f));
    public static final RegistryObject<Nutrient> TANNINS = NUTRIENTS.register("tannins", () -> new DragonNutrient(new Color(128, 71, 44), 0.0f));

    public static final RegistryObject<Nutrient> IODINE = NUTRIENTS.register("iodine", () -> new DragonNutrient(new Color(123, 32, 179), 0.5f));
    public static final RegistryObject<Nutrient> CHITIN = NUTRIENTS.register("chitin", () -> new DragonNutrient(new Color(198, 176, 185), 0.5f));
    public static final RegistryObject<Nutrient> SALTS = NUTRIENTS.register("salts", () -> new DragonNutrient(new Color(214, 214, 214), 0.5f));
    public static final RegistryObject<Nutrient> OILS = NUTRIENTS.register("oils", () -> new DragonNutrient(new Color(227, 225, 111), 0.0f));

    @SubscribeEvent
    public static void onDefineNutrients(DefineNutrientsEvent event) {
        event.player.getCapability(DRAGON_CAPABILITY).ifPresent(handler -> {
            if (handler.getType() instanceof CaveDragonType type) {
                event.patch()
                        .withName(NUTRIENT_SET_CAVE_DRAGON)
                        .withPriority(1000)
                        .removeByNamespace(TerraFirmaNutrients.MODID)
                        .add(METALS.get())       // e.g. metal dust
                        .add(FLUX.get())         // e.g. flux
                        .add(SILICON.get())      // e.g. lava or silicon
                        .add(CARBON.get())       // e.g. coal
                        .add(IRRADIANTS.get())   // e.g. redstone
                        .build();
                event.thirstNutrientType = LAVA.get();
            } else if (handler.getType() instanceof ForestDragonType type) {
                event.patch()
                        .withName(NUTRIENT_SET_FOREST_DRAGON)
                        .withPriority(1000)
                        .removeByNamespace(TerraFirmaNutrients.MODID)
                        .add(GLUCOSE.get())    // they can also synthesize it under the sun
                        .add(TOXINS.get())     // e.g. spider eye or rotten flesh
                        .add(org.drakosha.terrafirmanutrients.Nutrients.PROTEIN.get()) // meat
                        .add(PHOSPHATES.get()) // e.g. bonemeal?
                        .add(TANNINS.get())
                        .build();
            } else if (handler.getType() instanceof SeaDragonType type) {
                event.patch()
                        .withName(NUTRIENT_SET_SEA_DRAGON)
                        .withPriority(1000)
                        .removeByNamespace(TerraFirmaNutrients.MODID)
                        .add(IODINE.get())   // sea products should have lot of it
                        .add(CHITIN.get())   // seaweed
                        .add(SALTS.get())    // salt water
                        .add(org.drakosha.terrafirmanutrients.Nutrients.PROTEIN.get()) // meat
                        .add(OILS.get())
                        .build();
            }
        });
    }

    @SubscribeEvent
    public static void onAteBadNutrient(AteBadNutrientEvent event) {
        if (event.nutrient == FLUX.get()) {
            // Set on fire, 1 second for each 1.0 amount
            event.player.setSecondsOnFire((int)Math.ceil(event.amount));
        } else if (event.nutrient == IODINE.get()) {
            // Poison, 5 seconds for each 1.0 amount, 1 level for each 5.0 amount
            event.player.addEffect(new MobEffectInstance(MobEffects.POISON, (int)Math.ceil(event.amount * 5), (int)Math.ceil(event.amount / 5.0f)));
        }
    }
}
