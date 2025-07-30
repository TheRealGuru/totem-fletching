package com.github.therealguru.totemfletching.overlay;

import com.github.therealguru.totemfletching.TotemFletchingConfig;
import com.github.therealguru.totemfletching.TotemFletchingPlugin;
import com.github.therealguru.totemfletching.model.Totem;
import com.github.therealguru.totemfletching.service.TotemService;
import java.awt.*;
import java.util.Map;
import java.util.Optional;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

@Slf4j
public class TotemFletchingOverlay extends Overlay {

    private final TotemService totemService;
    private final Client client;
    private final TotemFletchingConfig config;

    @Inject
    public TotemFletchingOverlay(
            TotemFletchingPlugin plugin,
            TotemFletchingConfig config,
            TotemService totemService,
            Client client) {
        super(plugin);
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        this.totemService = totemService;
        this.client = client;
        this.config = config;
    }

    @Override
    public Dimension render(Graphics2D graphics2D) {
        totemService.getTotems().stream()
                .filter(Totem::isRenderable)
                .forEach((totem) -> renderTotem(graphics2D, totem));
        return null;
    }

    void renderTotem(Graphics2D graphics2D, Totem totem) {
        renderTotemHighlight(graphics2D, totem);
        renderPointsOverlay(graphics2D, totem);

        if (config.renderTotemText()) {
            Optional<String> totemText = getTotemText(totem);
            if (totemText.isPresent()) {
                Font font = new Font(config.overlayFont().toString(), config.useBoldFont() ? Font.BOLD : Font.PLAIN, config.totemFontSize());
                graphics2D.setFont(font);
                String text = totemText.get();
                Point canvasPoint = getCanvasPoint(graphics2D, totem, text);
                if (canvasPoint != null) {
                    OverlayUtil.renderTextLocation(
                            graphics2D, canvasPoint, text, config.totemTextColor());
                }
            }
        }
    }

    private void renderTotemHighlight(Graphics2D graphics2D, Totem totem) {
        if (!config.renderTotemHighlight()) return;

        if (totem.hasTotemStarted()) {
            Shape shape = totem.getTotemGameObject().getClickbox();
            if (shape != null) {
                OverlayUtil.renderPolygon(graphics2D, shape, getTotemColor(totem));
            }
        } else {
            OverlayUtil.renderTileOverlay(
                    graphics2D, totem.getTotemGameObject(), null, config.totemIncompleteColor());
        }
    }

    Optional<String> getTotemText(Totem totem) {
        if (!totem.isCarved()) {
            return Optional.of(getAnimalText(totem));
        } else if (!totem.isDecorated() || config.keepDecoratedText()) {
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
        return totem.isCarved() && totem.isDecorated()
                ? config.totemCompleteColor()
                : config.totemIncompleteColor();
    }

    void renderPointsOverlay(Graphics2D graphics2D, Totem totem) {
        if (!config.renderPoints() || (totem.getPoints() == 0 && !config.showZeroPoints())) return;

        renderPointsText(graphics2D, totem);
        renderPointsTile(graphics2D, totem);
    }

    void renderPointsText(Graphics2D graphics2D, Totem totem) {
        final GameObject gameObject = totem.getPointsGameObject();
        final LocalPoint localPoint =
                LocalPoint.fromWorld(client.getTopLevelWorldView(), gameObject.getWorldLocation());
        if (localPoint == null) return;

        Font font = new Font(config.overlayFont().toString(), config.useBoldFont() ? Font.BOLD : Font.PLAIN, config.pointsFontSize());
        graphics2D.setFont(font);
        String text = getPointsText(totem);
        Point canvasPoint = gameObject.getCanvasTextLocation(graphics2D, text, config.pointsHeight());
        if (canvasPoint == null) return;

        OverlayUtil.renderTextLocation(graphics2D, canvasPoint, text, config.overlayTextColor());
    }

    void renderPointsTile(Graphics2D graphics2D, Totem totem) {
        if (!totem.isPointCapped()) return;

        OverlayUtil.renderTileOverlay(
                graphics2D, totem.getTotemGameObject(), null, config.pointsCappedColor());
    }

    private String getPointsText(Totem totem) {
        return totem.isPointCapped() ? "MAXIMUM" : Integer.toString(totem.getPoints());
    }

    private Point getCanvasPoint(Graphics2D graphics2D, Totem totem, String text) {
        if (totem.hasTotemStarted()) {
            return totem.getTotemGameObject().getCanvasTextLocation(graphics2D, text, config.builtHeight());
        } else {
            return totem.getTotemGameObject().getCanvasTextLocation(graphics2D, text, config.unbuiltHeight());
        }
    }
}
