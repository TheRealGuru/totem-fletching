package com.github.therealguru.totemfletching.overlay;

import com.github.therealguru.totemfletching.TotemFletchingConfig;
import com.github.therealguru.totemfletching.model.Totem;
import com.github.therealguru.totemfletching.service.TotemService;
import java.awt.*;
import java.util.Map;
import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

public class CarvingActionOverlay extends Overlay {

    private static final int TOTEM_CARVING_WIDGET = 270;

    private final Client client;
    private final TotemService totemService;
    private final TotemFletchingConfig config;

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
        if (!config.renderChatboxOptions()) return null;

        Totem totem = totemService.getClosestTotem();
        if (totem == null) return null;

        Widget root = client.getWidget(TOTEM_CARVING_WIDGET, 0);
        if (root == null || root.isHidden()) {
            return null;
        }

        Map<Integer, Boolean> carved = totemService.getAnimalsProgress(totem);
        for (Map.Entry<Integer, Boolean> state : carved.entrySet()) {
            if (state.getValue()) continue;

            Widget childWidget = client.getWidget(TOTEM_CARVING_WIDGET, 13 + state.getKey());
            if (childWidget != null && !childWidget.isHidden()) {
                renderOverlay(graphics2D, childWidget);
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
}
