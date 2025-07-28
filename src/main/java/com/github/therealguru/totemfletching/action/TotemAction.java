package com.github.therealguru.totemfletching.action;

import com.github.therealguru.totemfletching.model.Totem;
import com.github.therealguru.totemfletching.model.TotemVarbit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.api.events.VarbitChanged;

@RequiredArgsConstructor
public abstract class TotemAction {

    @Getter private final TotemVarbit varbit;

    public abstract void onVarbitChanged(Totem totem, VarbitChanged varbitChanged);
}
