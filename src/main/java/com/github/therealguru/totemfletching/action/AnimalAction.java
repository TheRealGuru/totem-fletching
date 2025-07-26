package com.github.therealguru.totemfletching.action;

import com.github.therealguru.totemfletching.model.Totem;
import com.github.therealguru.totemfletching.model.TotemVarbit;
import net.runelite.api.events.VarbitChanged;

public class AnimalAction extends TotemAction {
    private final int animal;

    public AnimalAction(int animal) {
        super(TotemVarbit.valueOf("ANIMAL_" + animal));
        this.animal = animal;
    }

    @Override
    public void onVarbitChanged(Totem totem, VarbitChanged varbitChanged) {
        totem.getAnimals()[animal - 1] = varbitChanged.getValue();
    }
}
