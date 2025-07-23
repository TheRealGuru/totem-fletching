package com.github.therealguru.totemfletching.overlay;

import com.github.therealguru.totemfletching.model.Totem;
import com.github.therealguru.totemfletching.service.TotemService;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Optional;

public class TotemFletchingOverlay extends Overlay {

    private TotemService totemService;
    private Client client;

    public TotemFletchingOverlay(@Nullable Plugin plugin, TotemService totemService, Client client) {
        super(plugin);
        this.totemService = totemService;
        this.client = client;
    }

    @Override
    public Dimension render(Graphics2D graphics2D) {
        totemService.getTotems().stream().filter(Totem::isRenderable).forEach((totem) -> renderTotem(graphics2D, totem));
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        return null;
    }

    void renderTotem(Graphics2D graphics2D, Totem totem) {
        renderPoints(graphics2D, totem);

        Shape shape = getTotemHighlight(totem);
        if(shape != null) {
            OverlayUtil.renderPolygon(graphics2D, shape, getTotemColor(totem));
        }

        Optional<String> totemText = getTotemText(totem);
        if(totemText.isPresent()) {
            String text = totemText.get();
            Point canvasPoint = totem.getTotemGameObject().getCanvasTextLocation(graphics2D, text, 16);
            if(canvasPoint != null) {
                OverlayUtil.renderTextLocation(graphics2D, canvasPoint, text, Color.GREEN);
            }
        }
    }

    private Shape getTotemHighlight(Totem totem) {
        if(!totem.hasTotemStarted() || totem.getTotemGameObject().getConvexHull() == null) {
            return totem.getTotemGameObject().getCanvasTilePoly();
        } else {
            return totem.getTotemGameObject().getConvexHull();
        }
    }

    Optional<String> getTotemText(Totem totem) {
        if((!totem.hasTotemStarted() || totem.isBuildingTotem()) && !totem.isCarved()) {
            return Optional.of(totem.getAnimals()[0] + " " + totem.getAnimals()[1] + " " + totem.getAnimals()[2]);
        } else if(!totem.isDecorated()) {
            return Optional.of(totem.getDecoration() + " / 4");
        } else {
            return Optional.empty();
        }
    }

    public Color getTotemColor(Totem totem) {
        return totem.isCarved() && totem.isDecorated() ? Color.GREEN : Color.RED;
    }

    void renderPoints(Graphics2D graphics2D, Totem totem) {
        GameObject gameObject = totem.getPointsGameObject();

        final LocalPoint localPoint = LocalPoint.fromWorld(client.getTopLevelWorldView(), gameObject.getWorldLocation());
        if(localPoint == null) return;

        final String text = totem.getPoints() + "";

        Point canvasPoint = totem.getPointsGameObject().getCanvasTextLocation(graphics2D, text, 16);
        if(canvasPoint == null) return;

        OverlayUtil.renderTextLocation(graphics2D, canvasPoint, text, Color.GREEN);
    }
}
