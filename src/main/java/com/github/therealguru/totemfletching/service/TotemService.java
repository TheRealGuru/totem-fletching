package com.github.therealguru.totemfletching.service;

import com.github.therealguru.totemfletching.TotemFletchingConfig;
import com.github.therealguru.totemfletching.action.*;
import com.github.therealguru.totemfletching.model.Totem;
import com.github.therealguru.totemfletching.model.TotemTier;
import com.github.therealguru.totemfletching.model.TotemVarbit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.GameObject;
import net.runelite.api.Player;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.ObjectID;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.Notifier;

@Slf4j
@Singleton
public class TotemService {

    private final Map<TotemVarbit, TotemAction> totemActions;
    @Getter private final List<Totem> totems;
    @Getter private Optional<Totem> closestTotem = Optional.empty();

    @Inject
    public TotemService(TotemFletchingConfig config, Notifier notifier) {
        this.totemActions = initializeTotemActions(notifier);
        this.totems = initializeTotems(config);
    }

    private Map<TotemVarbit, TotemAction> initializeTotemActions(Notifier notifier) {
        Map<TotemVarbit, TotemAction> actions = new HashMap<>();
        actions.put(TotemVarbit.ANIMAL_1, new AnimalAction(1));
        actions.put(TotemVarbit.ANIMAL_2, new AnimalAction(2));
        actions.put(TotemVarbit.ANIMAL_3, new AnimalAction(3));
        actions.put(TotemVarbit.BASE, new BaseAction(notifier));
        actions.put(TotemVarbit.BASE_CARVED, new BaseCarvedAction());
        actions.put(TotemVarbit.DECAY, new DecayAction());
        actions.put(TotemVarbit.DECORATIONS, new DecorationsAction());
        actions.put(TotemVarbit.POINTS, new PointsAction());
        actions.put(TotemVarbit.LOW, new TotemTierAction(TotemTier.LOW));
        actions.put(TotemVarbit.MID, new TotemTierAction(TotemTier.MID));
        actions.put(TotemVarbit.TOP, new TotemTierAction(TotemTier.TOP));
        return actions;
    }

    private List<Totem> initializeTotems(TotemFletchingConfig config) {
        return List.of(
                new Totem(
                        1,
                        ObjectID.ENT_TOTEMS_SITE_1_BASE,
                        ObjectID.ENT_TOTEMS_SITE_1_OFFERINGS,
                        VarbitID.ENT_TOTEMS_SITE_1_BASE,
                        config.notificationDecayTotem1()),
                new Totem(
                        2,
                        ObjectID.ENT_TOTEMS_SITE_2_BASE,
                        ObjectID.ENT_TOTEMS_SITE_2_OFFERINGS,
                        VarbitID.ENT_TOTEMS_SITE_2_BASE,
                        config.notificationDecayTotem2()),
                new Totem(
                        3,
                        ObjectID.ENT_TOTEMS_SITE_3_BASE,
                        ObjectID.ENT_TOTEMS_SITE_3_OFFERINGS,
                        VarbitID.ENT_TOTEMS_SITE_3_BASE,
                        config.notificationDecayTotem3()),
                new Totem(
                        4,
                        ObjectID.ENT_TOTEMS_SITE_4_BASE,
                        ObjectID.ENT_TOTEMS_SITE_4_OFFERINGS,
                        VarbitID.ENT_TOTEMS_SITE_4_BASE,
                        config.notificationDecayTotem4()),
                new Totem(
                        5,
                        ObjectID.ENT_TOTEMS_SITE_5_BASE,
                        ObjectID.ENT_TOTEMS_SITE_5_OFFERINGS,
                        VarbitID.ENT_TOTEMS_SITE_5_BASE,
                        config.notificationDecayTotem5()),
                new Totem(
                        6,
                        ObjectID.ENT_TOTEMS_SITE_6_BASE,
                        ObjectID.ENT_TOTEMS_SITE_6_OFFERINGS,
                        VarbitID.ENT_TOTEMS_SITE_6_BASE,
                        config.notificationDecayTotem6()),
                new Totem(
                        7,
                        ObjectID.ENT_TOTEMS_SITE_7_BASE,
                        ObjectID.ENT_TOTEMS_SITE_7_OFFERINGS,
                        VarbitID.ENT_TOTEMS_SITE_7_BASE,
                        config.notificationDecayTotem7()),
                new Totem(
                        8,
                        ObjectID.ENT_TOTEMS_SITE_8_BASE,
                        ObjectID.ENT_TOTEMS_SITE_8_OFFERINGS,
                        VarbitID.ENT_TOTEMS_SITE_8_BASE,
                        config.notificationDecayTotem8()));
    }

    public void onConfigChange(TotemFletchingConfig config) {
        this.totems.get(0).setNotification(config.notificationDecayTotem1());
        this.totems.get(1).setNotification(config.notificationDecayTotem2());
        this.totems.get(2).setNotification(config.notificationDecayTotem3());
        this.totems.get(3).setNotification(config.notificationDecayTotem4());
        this.totems.get(4).setNotification(config.notificationDecayTotem5());
        this.totems.get(5).setNotification(config.notificationDecayTotem6());
        this.totems.get(6).setNotification(config.notificationDecayTotem7());
        this.totems.get(7).setNotification(config.notificationDecayTotem8());
    }

    public void onVarbitChanged(final VarbitChanged varbitChanged) {
        findTotemByVarbit(varbitChanged.getVarbitId())
                .ifPresent(
                        totem -> {
                            TotemVarbit totemVarbit =
                                    TotemVarbit.getVarbit(totem, varbitChanged.getVarbitId());
                            TotemAction action = totemActions.get(totemVarbit);
                            if (action != null) {
                                action.onVarbitChanged(totem, varbitChanged);
                            }
                        });
    }

    private Optional<Totem> findTotemByVarbit(int varbitId) {
        return totems.stream().filter(t -> t.isVarbitRelated(varbitId)).findFirst();
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
     * Returns the carved status for each animal on the given totem. The map contains animal IDs as
     * keys and whether they have been carved as values. A value of {@code true} means the animal
     * has already been carved on this totem. A value of {@code false} means the animal has not been
     * carved yet.
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

    public void updateClosestTotem(Player player) {
        closestTotem = findClosestTotem(player);
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
