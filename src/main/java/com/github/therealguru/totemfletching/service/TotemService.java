package com.github.therealguru.totemfletching.service;

import com.github.therealguru.totemfletching.action.*;
import com.github.therealguru.totemfletching.model.Totem;
import com.github.therealguru.totemfletching.model.TotemTier;
import com.github.therealguru.totemfletching.model.TotemVarbit;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import net.runelite.api.GameObject;
import net.runelite.api.Player;
import net.runelite.api.events.VarbitChanged;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class TotemService {
    private static final ImmutableList<Totem> TOTEMS = ImmutableList.of(
        new Totem(1, 57016, 57020, 17611),
        new Totem(2, 57022, 57026, 17629),
        new Totem(3, 57028, 57032, 17647),
        new Totem(4, 57034, 57038, 17665),
        new Totem(5, 57040, 57044, 17683),
        new Totem(6, 57046, 57050, 17701),
        new Totem(7, 57052, 57056, 17719),
        new Totem(8, 57058, 57062, 17737)
    );

    private static final ImmutableMap<TotemVarbit, TotemAction> TOTEM_ACTIONS = ImmutableMap.<TotemVarbit, TotemAction>builder()
        .put(TotemVarbit.ANIMAL_1, new AnimalAction(1))
        .put(TotemVarbit.ANIMAL_2, new AnimalAction(2))
        .put(TotemVarbit.ANIMAL_3, new AnimalAction(3))
        .put(TotemVarbit.BASE, new BaseAction())
        .put(TotemVarbit.BASE_CARVED, new BaseCarvedAction())
        .put(TotemVarbit.DECAY, new DecayAction())
        .put(TotemVarbit.DECORATIONS, new DecorationsAction())
        .put(TotemVarbit.POINTS, new PointsAction())
        .put(TotemVarbit.LOW, new TotemTierAction(TotemTier.LOW))
        .put(TotemVarbit.MID, new TotemTierAction(TotemTier.MID))
        .put(TotemVarbit.TOP, new TotemTierAction(TotemTier.TOP))
        .build();

    @Getter
    @Nullable
    private Totem closestTotem = null;

    @Inject
    public TotemService() {}

    public void onVarbitChanged(final VarbitChanged varbitChanged) {
        Totem totem = TOTEMS.stream().filter(t -> t.isVarbitRelated(varbitChanged.getVarbitId())).findFirst().orElse(null);
        if (totem == null) return;

        TotemVarbit totemVarbit = TotemVarbit.getVarbit(totem, varbitChanged.getVarbitId());
        TotemAction action = TOTEM_ACTIONS.get(totemVarbit);
        if (action != null) {
            action.onVarbitChanged(totem, varbitChanged);
        }
    }

    public void addGameObject(final GameObject gameObject) {
        for (Totem totem : TOTEMS) {
            if (totem.getTotemGameObjectId() == gameObject.getId()) {
                totem.setTotemGameObject(gameObject);
            } else if (totem.getPointsGameObjectId() == gameObject.getId()) {
                totem.setPointsGameObject(gameObject);
            }
        }
    }

    public void removeGameObject(final GameObject gameObject) {
        for (Totem totem : TOTEMS) {
            if (totem.getTotemGameObjectId() == gameObject.getId()) {
                totem.setTotemGameObject(null);
            } else if (totem.getPointsGameObjectId() == gameObject.getId()) {
                totem.setPointsGameObject(null);
            }
        }
    }

    public void clearGameObjects() {
        getTotems().forEach(totem -> {
            totem.setTotemGameObject(null);
            totem.setPointsGameObject(null);
        });
    }

    public Map<Integer, Boolean> getAnimalsProgress(final Totem totem) {
        Map<Integer, Boolean> result = new HashMap<>();

        for (int animal : totem.getAnimals()) {
            int progressKey = animal + 9;
            boolean exists = totem.getProgress().containsValue(progressKey);
            result.put(animal, exists);
        }

        return result;
    }

    public List<Totem> getTotems() {
        return TOTEMS;
    }

    public void updateClosestTotem(Player player) {
        closestTotem = getTotems().stream()
                .filter(totem -> totem.getTotemGameObject() != null)
                .filter(totem -> totem.getTotemGameObject().getWorldLocation().distanceTo(player.getWorldLocation()) <= 10)
                .findFirst()
                .orElse(null);
    }
}
