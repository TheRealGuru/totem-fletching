package com.github.therealguru.totemfletching.action;

import com.github.therealguru.totemfletching.model.Totem;
import com.github.therealguru.totemfletching.model.TotemVarbit;
import net.runelite.api.events.VarbitChanged;

public class DecorationsAction extends TotemAction {
    public DecorationsAction() {
        super(TotemVarbit.DECORATIONS);
    }

    @Override
    public void onVarbitChanged(Totem totem, VarbitChanged varbitChanged) {
        totem.setDecoration(varbitChanged.getValue());
    }
}
