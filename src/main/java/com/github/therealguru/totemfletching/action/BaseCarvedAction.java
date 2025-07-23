package com.github.therealguru.totemfletching.action;

import com.github.therealguru.totemfletching.model.Totem;
import com.github.therealguru.totemfletching.model.TotemVarbit;
import net.runelite.api.events.VarbitChanged;

public class BaseCarvedAction extends TotemAction {

    public BaseCarvedAction() {
        super(TotemVarbit.BASE_CARVED);
    }

    @Override
    public void onVarbitChanged(Totem totem, VarbitChanged varbitChanged) {
        totem.setCarved(varbitChanged.getValue() == 1);
    }
}
