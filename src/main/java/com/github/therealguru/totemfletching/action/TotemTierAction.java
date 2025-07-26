package com.github.therealguru.totemfletching.action;

import com.github.therealguru.totemfletching.model.Totem;
import com.github.therealguru.totemfletching.model.TotemTier;
import com.github.therealguru.totemfletching.model.TotemVarbit;
import net.runelite.api.events.VarbitChanged;

public class TotemTierAction extends TotemAction {
    private final TotemTier totemTier;

    public TotemTierAction(TotemTier tier) {
        super(TotemVarbit.valueOf(tier.name()));
        this.totemTier = tier;
    }

    @Override
    public void onVarbitChanged(Totem totem, VarbitChanged varbitChanged) {
        totem.getProgress().put(totemTier, varbitChanged.getValue());
    }
}
