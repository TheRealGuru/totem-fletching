package com.github.therealguru.totemfletching.action;

import com.github.therealguru.totemfletching.model.Totem;
import com.github.therealguru.totemfletching.model.TotemVarbit;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.VarbitChanged;
import net.runelite.client.Notifier;

@Slf4j
public class BaseAction extends TotemAction {

    private final Notifier notifier;

    public BaseAction(Notifier notifier) {
        super(TotemVarbit.BASE);
        this.notifier = notifier;
    }

    @Override
    public void onVarbitChanged(Totem totem, VarbitChanged varbitChanged) {
        totem.setBase(varbitChanged.getValue());
        if (varbitChanged.getValue() == 0) {
            log.debug("Totem {} has decayed", totem.getTotemId());
            notifier.notify(
                    totem.getNotification(), "Totem " + totem.getTotemId() + " has decayed");
        }
    }
}
