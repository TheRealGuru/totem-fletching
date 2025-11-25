package com.github.therealguru.totemfletching.service;

import com.github.therealguru.totemfletching.action.*;
import com.github.therealguru.totemfletching.model.Totem;
import com.github.therealguru.totemfletching.model.TotemTier;
import com.github.therealguru.totemfletching.model.TotemVarbit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.GameObject;
import net.runelite.api.Player;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.ObjectID;
import net.runelite.api.gameval.VarbitID;

@Slf4j
@Singleton
public class TotemService {

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

    private final List<Totem> totems =
            List.of(
                    new Totem(
                            1,
                            ObjectID.ENT_TOTEMS_SITE_1_BASE,
                            ObjectID.ENT_TOTEMS_SITE_1_OFFERINGS,
                            VarbitID.ENT_TOTEMS_SITE_1_BASE),
                    new Totem(
                            2,
                            ObjectID.ENT_TOTEMS_SITE_2_BASE,
                            ObjectID.ENT_TOTEMS_SITE_2_OFFERINGS,
                            VarbitID.ENT_TOTEMS_SITE_2_BASE),
                    new Totem(
                            3,
                            ObjectID.ENT_TOTEMS_SITE_3_BASE,
                            ObjectID.ENT_TOTEMS_SITE_3_OFFERINGS,
                            VarbitID.ENT_TOTEMS_SITE_3_BASE),
                    new Totem(
                            4,
                            ObjectID.ENT_TOTEMS_SITE_4_BASE,
                            ObjectID.ENT_TOTEMS_SITE_4_OFFERINGS,
                            VarbitID.ENT_TOTEMS_SITE_4_BASE),
                    new Totem(
                            5,
                            ObjectID.ENT_TOTEMS_SITE_5_BASE,
                            ObjectID.ENT_TOTEMS_SITE_5_OFFERINGS,
                            VarbitID.ENT_TOTEMS_SITE_5_BASE),
                    new Totem(
                            6,
                            ObjectID.ENT_TOTEMS_SITE_6_BASE,
                            ObjectID.ENT_TOTEMS_SITE_6_OFFERINGS,
                            VarbitID.ENT_TOTEMS_SITE_6_BASE),
                    new Totem(
                            7,
                            ObjectID.ENT_TOTEMS_SITE_7_BASE,
                            ObjectID.ENT_TOTEMS_SITE_7_OFFERINGS,
                            VarbitID.ENT_TOTEMS_SITE_7_BASE),
                    new Totem(
                            8,
                            ObjectID.ENT_TOTEMS_SITE_8_BASE,
                            ObjectID.ENT_TOTEMS_SITE_8_OFFERINGS,
                            VarbitID.ENT_TOTEMS_SITE_8_BASE));

    private Totem closestTotem = null;

    public void onVarbitChanged(final VarbitChanged varbitChanged) {
        findTotemByVarbit(varbitChanged.getVarbitId())
                .ifPresent(
                        totem -> {
                            TotemVarbit totemVarbit =
                                    TotemVarbit.getVarbit(totem, varbitChanged.getVarbitId());
                            TotemAction action = TOTEM_ACTIONS.get(totemVarbit);
                            if (action != null) {
                                action.onVarbitChanged(totem, varbitChanged);
                            }
                        });
    }

    private Optional<Totem> findTotemByVarbit(int varbitId) {
        return totems.stream().filter(t -> t.isVarbitRelated(varbitId)).findFirst();
    }

    public Optional<Totem> getClosestTotem() {
        return Optional.ofNullable(closestTotem);
    }

    public void addGameObject(final GameObject gameObject) {
        for (Totem totem : totems) {
            if (totem.getTotemGameObjectId() == gameObject.getId()) {
                totem.setTotemGameObject(gameObject);
            } else if (totem.getPointsGameObjectId() == gameObject.getId()) {
                totem.setPointsGameObject(gameObject);
            }
        }
    }

    public void removeGameObject(final GameObject gameObject) {
        for (Totem totem : totems) {
            if (totem.getTotemGameObjectId() == gameObject.getId()) {
                totem.setTotemGameObject(null);
            } else if (totem.getPointsGameObjectId() == gameObject.getId()) {
                totem.setPointsGameObject(null);
            }
        }
    }

    public void clearGameObjects() {
        getTotems()
                .forEach(
                        totem -> {
                            totem.setTotemGameObject(null);
                            totem.setPointsGameObject(null);
                        });
    }

    /**
     * Returns the carved status for each animal on the given totem.
     * The map contains animal IDs as keys and whether they have been carved as values.
     * A value of {@code true} means the animal has already been carved on this totem.
     * A value of {@code false} means the animal has not been carved yet.
     *
     * @param totem the totem to check
     * @return a map of animal ID to carved status
     */
    public Map<Integer, Boolean> getCarvedAnimalsStatus(final Totem totem) {
        Map<Integer, Boolean> result = new HashMap<>();

        for (int animal : totem.getAnimals()) {
            int progressKey = animal + 9;
            boolean exists = totem.getProgress().containsValue(progressKey);
            result.put(animal, exists);
        }

        return result;
    }

    public List<Totem> getTotems() {
        return totems;
    }

    public void updateClosestTotem(Player player) {
        closestTotem =
                findClosestTotem(player)
                        .orElse(null);
    }

    private Optional<Totem> findClosestTotem(Player player) {
        return getTotems().stream()
                .filter(totem -> totem.getTotemGameObject() != null)
                .filter(
                        totem ->
                                totem.getTotemGameObject()
                                                .getWorldLocation()
                                                .distanceTo(player.getWorldLocation())
                                        <= 10)
                .findFirst();
    }

    public int getTotalPoints() {
        return getTotems().stream().mapToInt(Totem::getPoints).sum();
    }
}
