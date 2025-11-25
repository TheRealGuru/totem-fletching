package com.github.therealguru.totemfletching.overlay;

import com.github.therealguru.totemfletching.TotemFletchingConfig;
import com.github.therealguru.totemfletching.model.Totem;
import com.github.therealguru.totemfletching.service.TotemService;
import java.awt.*;
import java.util.Map;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

@Slf4j
public class CarvingActionOverlay extends Overlay {

    private static final int TOTEM_CARVING_TEXT_WIDGET = InterfaceID.Skillmulti.TITLE;
    private static final int FIRST_CARVING_WIDGET = InterfaceID.Skillmulti.A;
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

        if (!isCarvingWidget()) return null;

        totemService.getClosestTotem().ifPresent(totem -> renderCarvingChoices(graphics2D, totem));

        return null;
    }

    private void renderCarvingChoices(Graphics2D graphics2D, Totem totem) {
        Map<Integer, Boolean> carvedState = totemService.getCarvedAnimalsStatus(totem);
        for (int i = 0; i < ANIMAL_COUNT; i++) {
            Widget carvingWidget = client.getWidget(FIRST_CARVING_WIDGET + i);
            if (carvingWidget != null && !carvingWidget.isHidden()) {
                // If the choice doesn't exist then we default to true (this just means the animal
                // is an incorrect choice).
                boolean isCarvedOrIncorrectChoice = carvedState.getOrDefault(i + 1, true);
                renderOverlay(graphics2D, carvingWidget, !isCarvedOrIncorrectChoice);
            }
        }
    }

    private void renderOverlay(Graphics2D graphics, Widget carvingWidget, boolean correctChoice) {
        Rectangle bounds = carvingWidget.getBounds();
        if (bounds != null) {
            if (correctChoice && config.highlightCorrectCarvingChoice()) {
                graphics.setColor(config.carvingHighlightColor());
                graphics.setStroke(new BasicStroke(config.carvingHighlightBorderWidth()));
                graphics.draw(bounds);
            } else if (!correctChoice && config.maskIncorrectCarvingChoice()) {
                graphics.setColor(config.carvingMaskColor());
                graphics.fill(bounds);
            }
        }
    }

    private boolean isCarvingWidget() {
        Widget titleWidget = client.getWidget(TOTEM_CARVING_TEXT_WIDGET);
        return titleWidget != null
                && titleWidget.getText() != null
                && titleWidget.getText().equalsIgnoreCase(ACTION_TEXT);
    }
}
