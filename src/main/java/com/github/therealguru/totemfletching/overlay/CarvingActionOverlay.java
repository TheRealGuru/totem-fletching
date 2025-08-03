package com.github.therealguru.totemfletching.overlay;

import com.github.therealguru.totemfletching.TotemFletchingConfig;
import com.github.therealguru.totemfletching.model.Totem;
import com.github.therealguru.totemfletching.service.TotemService;
import java.awt.*;
import java.util.Map;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class CarvingActionOverlay extends Overlay {

    private static final int TOTEM_CARVING_WIDGET = 270;
    private static final int TOTEM_CARVING_TEXT_WIDGET = 5;
    private static final int FIRST_ANIMAL_WIDGET = 13;
    private static final int ANIMAL_COUNT = 5;
    private static final String ACTION_TEXT = "What animal would you like to carve?";

    private final Client client;
    private final TotemService totemService;
    private final TotemFletchingConfig config;

    @Inject
    public CarvingActionOverlay(TotemService service, TotemFletchingConfig config, Client client) {
        super();
        this.client = client;
        this.totemService = service;
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        setPriority(.6F);
    }

    @Override
    public Dimension render(Graphics2D graphics2D) {
        if (!config.highlightCorrectCarvingChoice() && !config.maskIncorrectCarvingChoice())
            return null;

        Totem totem = totemService.getClosestTotem();
        if (totem == null) return null;

        Widget root = client.getWidget(TOTEM_CARVING_WIDGET, 0);
        if (root == null || root.isHidden()) {
            return null;
        }

        if (!isCarvingWidget()) return null;

        Map<Integer, Boolean> carvedState = totemService.getAnimalsProgress(totem);
        for (int i = 1; i < ANIMAL_COUNT + 1; i++) {
            Widget childWidget = client.getWidget(TOTEM_CARVING_WIDGET, i + FIRST_ANIMAL_WIDGET);
            if (childWidget != null && !childWidget.isHidden()) {
                // If the choice doesn't exist then we default to true (this just means the animal
                // is an incorrect choice).
                boolean isCarvedOrIncorrectChoice = carvedState.getOrDefault(i, true);
                renderOverlay(graphics2D, childWidget, !isCarvedOrIncorrectChoice);
            }
        }

        return null;
    }

    private void renderOverlay(Graphics2D graphics, Widget childWidget, boolean correctChoice) {
        Rectangle bounds = childWidget.getBounds();
        if (bounds != null) {
            if (correctChoice && config.highlightCorrectCarvingChoice()) {
                graphics.setColor(config.carvingInterfaceColor());
                graphics.setStroke(new BasicStroke(config.carvingInterfaceBorderWith()));
                graphics.draw(bounds);
            } else if (!correctChoice && config.maskIncorrectCarvingChoice()) {
                graphics.setColor(config.carvingMaskColor());
                graphics.fill(bounds);
            }
        }
    }

    private boolean isCarvingWidget() {
        Widget childWidget = client.getWidget(TOTEM_CARVING_WIDGET, TOTEM_CARVING_TEXT_WIDGET);
        return childWidget != null
                && childWidget.getText() != null
                && childWidget.getText().equalsIgnoreCase(ACTION_TEXT);
    }
}
