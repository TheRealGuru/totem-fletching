package com.github.therealguru.totemfletching.overlay;

import com.github.therealguru.totemfletching.TotemFletchingConfig;
import com.github.therealguru.totemfletching.model.Totem;
import com.github.therealguru.totemfletching.service.TotemService;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;
import java.util.Map;

@Slf4j
@Singleton
public class CarvingActionOverlay extends Overlay {
    private static final int TOTEM_CARVING_WIDGET = 270;
    private static final int TOTEM_CARVING_TEXT_WIDGET = 5;
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
        if(!config.renderCarvingOverlay()) return null;

        Totem totem = totemService.getClosestTotem();
        if (totem == null) return null;

        Widget root = client.getWidget(TOTEM_CARVING_WIDGET, 0);
        if (root == null || root.isHidden()) {
            return null;
        }

        if(!isCarvingWidget()) return null;

        Map<Integer, Boolean> carved = totemService.getAnimalsProgress(totem);

        for (Map.Entry<Integer, Boolean> state : carved.entrySet()) {
            if (state.getValue()) continue;

            Widget childWidget = client.getWidget(TOTEM_CARVING_WIDGET, 13 + state.getKey());
            if (childWidget != null && !childWidget.isHidden()) {
                renderOverlay(graphics2D, childWidget);
            }
            Widget textWidget = client.getWidget(TOTEM_CARVING_WIDGET, InterfaceID.Skillmulti.TEXT);
            if(textWidget != null) {
                log.debug("Text widget is present with text: {}", textWidget.getText());
            }

            Widget tooltipWidget = client.getWidget(TOTEM_CARVING_WIDGET, InterfaceID.Skillmulti.TOOLTIP);
            if(tooltipWidget != null) {
                log.debug("Tooltip widget is present with text: {}", tooltipWidget.getText());
            }
        }
        return null;
    }

    private void renderOverlay(Graphics2D graphics, Widget childWidget) {
        Rectangle bounds = childWidget.getBounds();
        if (bounds != null) {
            graphics.setColor(Color.GREEN);
            graphics.setStroke(new BasicStroke(2));
            graphics.draw(bounds);
        }
    }

    private boolean isCarvingWidget() {
        Widget childWidget = client.getWidget(TOTEM_CARVING_WIDGET, TOTEM_CARVING_TEXT_WIDGET);
        return childWidget != null && childWidget.getText() != null && childWidget.getText().equalsIgnoreCase(ACTION_TEXT);
    }
}
