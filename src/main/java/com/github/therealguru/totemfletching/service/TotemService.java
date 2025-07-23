package com.github.therealguru.totemfletching.service;

import com.github.therealguru.totemfletching.model.Totem;
import com.github.therealguru.totemfletching.model.TotemTier;
import com.github.therealguru.totemfletching.model.TotemVarbit;
import com.github.therealguru.totemfletching.action.*;
import net.runelite.api.GameObject;
import net.runelite.api.events.VarbitChanged;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TotemService {

    private static final List<Totem> TOTEMS = List.of(
            new Totem(1, 57016, 57020, 17611),
            new Totem(2, 57022, 57026, 17629),
            new Totem(3, 57028, 57032, 17647),
            new Totem(4, 57034, 57038, 17665),
            new Totem(5, 57040, 57044, 17683),
            new Totem(6, 57046, 57050, 17701),
            new Totem(7, 57052, 57056, 17719)
    );

    private static final Map<TotemVarbit, TotemAction> TOTEM_ACTIONS = new HashMap<>();
    static {
        TOTEM_ACTIONS.put(TotemVarbit.ANIMAL_1, new AnimalAction(1));
        TOTEM_ACTIONS.put(TotemVarbit.ANIMAL_2, new AnimalAction(2));
        TOTEM_ACTIONS.put(TotemVarbit.ANIMAL_3, new AnimalAction(3));
        TOTEM_ACTIONS.put(TotemVarbit.BASE, new BaseAction());
        TOTEM_ACTIONS.put(TotemVarbit.BASE_CARVED, new BaseCarvedAction());
        TOTEM_ACTIONS.put(TotemVarbit.DECAY, new DecayAction());
        TOTEM_ACTIONS.put(TotemVarbit.DECORATIONS, new DecorationsAction());
        TOTEM_ACTIONS.put(TotemVarbit.POINTS, new PointsAction());
        TOTEM_ACTIONS.put(TotemVarbit.LOW, new TotemTierAction(TotemTier.LOW));
        TOTEM_ACTIONS.put(TotemVarbit.MID, new TotemTierAction(TotemTier.MID));
        TOTEM_ACTIONS.put(TotemVarbit.TOP, new TotemTierAction(TotemTier.TOP));
    }

    public void onVarbitChanged(final VarbitChanged varbitChanged) {
        Totem totem = TOTEMS.stream().filter(t -> t.isVarbitRelated(varbitChanged.getVarbitId())).findFirst().orElse(null);
        if (totem == null) return;

        TotemVarbit totemVarbit = TotemVarbit.getVarbit(totem, varbitChanged.getVarbitId());
        TotemAction action = TOTEM_ACTIONS.get(totemVarbit);
        if(action != null) {
            action.onVarbitChanged(totem, varbitChanged);
        }
    }

    public void addGameObject(final GameObject gameObject) {
        for(Totem totem : TOTEMS) {
            if(totem.getTotemGameObjectId() == gameObject.getId()) {
                totem.setTotemGameObject(gameObject);
            } else if(totem.getPointsGameObjectId() == gameObject.getId()) {
                totem.setPointsGameObject(gameObject);
            }
        }
    }

    public void removeGameObject(final GameObject gameObject) {
        for(Totem totem : TOTEMS) {
            if(totem.getTotemGameObjectId() == gameObject.getId()) {
                totem.setTotemGameObject(null);
            } else if(totem.getPointsGameObjectId() == gameObject.getId()) {
                totem.setPointsGameObject(null);
            }
        }
    }

    public List<Totem> getTotems() {
        return TOTEMS;
    }
}
