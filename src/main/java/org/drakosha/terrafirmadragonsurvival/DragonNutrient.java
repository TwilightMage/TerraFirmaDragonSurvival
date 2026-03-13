package org.drakosha.terrafirmadragonsurvival;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextColor;
import org.drakosha.terrafirmanutrients.Nutrient;

import java.awt.*;

public class DragonNutrient extends Nutrient {

    public DragonNutrient(TextColor color, float defaultNutritionValue) {
        super(color, defaultNutritionValue);
    }

    public DragonNutrient(Color color, float defaultNutritionValue) {
        super(color, defaultNutritionValue);
    }
}
