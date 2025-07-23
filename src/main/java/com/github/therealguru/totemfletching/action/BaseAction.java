package com.github.therealguru.totemfletching.action;

import com.github.therealguru.totemfletching.model.Totem;
import com.github.therealguru.totemfletching.model.TotemVarbit;
import net.runelite.api.events.VarbitChanged;

public class BaseAction extends TotemAction {

    public BaseAction() {
        super(TotemVarbit.BASE);
    }

    @Override
    public void onVarbitChanged(Totem totem, VarbitChanged varbitChanged) {
        totem.setBase(varbitChanged.getValue());
    }
}
