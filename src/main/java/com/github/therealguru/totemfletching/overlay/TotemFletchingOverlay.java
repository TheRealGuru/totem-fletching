package com.github.therealguru.totemfletching.overlay;

import com.github.therealguru.totemfletching.TotemFletchingPlugin;
import com.github.therealguru.totemfletching.model.Totem;
import com.github.therealguru.totemfletching.service.TotemService;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Singleton
public class TotemFletchingOverlay extends Overlay {
    private final TotemService totemService;
    private final Client client;

    @Inject
    public TotemFletchingOverlay(TotemFletchingPlugin plugin, TotemService totemService, Client client) {
        super(plugin);
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        this.totemService = totemService;
        this.client = client;
    }

    @Override
    public Dimension render(Graphics2D graphics2D) {
        totemService.getTotems().stream().filter(Totem::isRenderable).forEach((totem) -> renderTotem(graphics2D, totem));
        return null;
    }

    void renderTotem(Graphics2D graphics2D, Totem totem) {
        renderTotemHighlight(graphics2D, totem);
        renderPoints(graphics2D, totem);

        Optional<String> totemText = getTotemText(totem);
        if (totemText.isPresent()) {
            String text = totemText.get();
            Point canvasPoint = totem.getTotemGameObject().getCanvasTextLocation(graphics2D, text, 16);
            if (canvasPoint != null) {
                OverlayUtil.renderTextLocation(graphics2D, canvasPoint, text, Color.GREEN);
            }
        }
    }

    private void renderTotemHighlight(Graphics2D graphics2D, Totem totem) {
        if (totem.hasTotemStarted()) {
            Shape shape = totem.getTotemGameObject().getClickbox();
            if (shape != null) {
                OverlayUtil.renderPolygon(graphics2D, shape, getTotemColor(totem));
            }
        } else {
            OverlayUtil.renderTileOverlay(graphics2D, totem.getTotemGameObject(), null, Color.RED);
        }
    }

    Optional<String> getTotemText(Totem totem) {
        if ((!totem.hasTotemStarted() || totem.isBuildingTotem()) && !totem.isCarved()) {
            return Optional.of(getAnimalText(totem));
        } else if (!totem.isDecorated()) {
            return Optional.of(totem.getDecoration() + " / 4");
        } else {
            return Optional.empty();
        }
    }

    private String getAnimalText(final Totem totem) {
        Map<Integer, Boolean> animalData = totemService.getAnimalsProgress(totem);
        StringBuilder text = new StringBuilder();
        for (Map.Entry<Integer, Boolean> entry : animalData.entrySet()) {
            if (entry.getValue()) continue;

            text.append(entry.getKey()).append(" ");
        }
        return text.toString().trim();
    }

    public Color getTotemColor(Totem totem) {
        return totem.isCarved() && totem.isDecorated() ? Color.GREEN : Color.RED;
    }

    void renderPoints(Graphics2D graphics2D, Totem totem) {
        GameObject gameObject = totem.getPointsGameObject();

        final LocalPoint localPoint = LocalPoint.fromWorld(client.getTopLevelWorldView(), gameObject.getWorldLocation());
        if (localPoint == null) return;

        final String text = totem.getPoints() + "";

        Point canvasPoint = totem.getPointsGameObject().getCanvasTextLocation(graphics2D, text, 16);
        if (canvasPoint == null) return;

        OverlayUtil.renderTextLocation(graphics2D, canvasPoint, text, Color.GREEN);
    }
}
