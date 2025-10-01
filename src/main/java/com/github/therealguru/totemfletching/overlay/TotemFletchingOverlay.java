package com.github.therealguru.totemfletching.overlay;

import com.github.therealguru.totemfletching.TotemFletchingConfig;
import com.github.therealguru.totemfletching.TotemFletchingPlugin;
import com.github.therealguru.totemfletching.model.Totem;
import com.github.therealguru.totemfletching.model.TotemHighlightMode;
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
                .filter(totem -> totem.isRenderable(client))
                .forEach((totem) -> renderTotem(graphics2D, totem));
        return null;
    }

    void renderTotem(Graphics2D graphics2D, Totem totem) {
        renderTotemHighlight(graphics2D, totem);
        renderPointsOverlay(graphics2D, totem);

        if (config.renderTotemText()) {
            Optional<String> totemText = getTotemText(totem);
            if (totemText.isPresent()) {
                String text = totemText.get();
                Point canvasPoint = getTotemCanvasPoint(graphics2D, totem, text);
                if (canvasPoint != null) {
                    OverlayUtil.renderTextLocation(
                            graphics2D, canvasPoint, text, config.totemTextColor());
                }
            }
        }
    }

    private void renderTotemHighlight(Graphics2D graphics2D, Totem totem) {

        TotemHighlightMode mode = config.renderTotemHighlight();
        if (mode == TotemHighlightMode.NONE) return;

        if (totem.hasTotemStarted()) {
            Shape shape = totem.getTotemGameObject().getClickbox();
            if (shape != null) {
                final Color highlight = getTotemColor(totem);

                if (mode == TotemHighlightMode.FILL) {
                    final int alpha = config.totemFillAlpha();
                    final Color fill =
                            new Color(
                                    highlight.getRed(),
                                    highlight.getGreen(),
                                    highlight.getBlue(),
                                    alpha);

                    graphics2D.setRenderingHint(
                            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    final Composite oldComposite = graphics2D.getComposite();
                    final Color oldColor = graphics2D.getColor();

                    graphics2D.setComposite(AlphaComposite.SrcOver);
                    graphics2D.setColor(fill);
                    graphics2D.fill(shape);

                    graphics2D.setColor(oldColor);
                    graphics2D.setComposite(oldComposite);
                }

                OverlayUtil.renderPolygon(graphics2D, shape, getTotemColor(totem));
            }
        } else {
            OverlayUtil.renderTileOverlay(
                    graphics2D, totem.getTotemGameObject(), null, config.totemIncompleteColor());
        }
    }

    Optional<String> getTotemText(Totem totem) {
        if (!totem.isCarved()) {
            return Optional.of(totem.getTotemId() + " | " + getAnimalText(totem));
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

        renderPointsTile(graphics2D, totem);
        renderPointsText(graphics2D, totem);
    }

    void renderPointsText(Graphics2D graphics2D, Totem totem) {
        final GameObject gameObject = totem.getPointsGameObject();
        final LocalPoint localPoint =
                LocalPoint.fromWorld(client.getTopLevelWorldView(), gameObject.getWorldLocation());
        if (localPoint == null) return;

        String text = getPointsText(totem);
        Point canvasPoint =
                gameObject.getCanvasTextLocation(graphics2D, text, config.pointsOffset());
        if (canvasPoint == null) return;

        OverlayUtil.renderTextLocation(graphics2D, canvasPoint, text, config.pointsTextColor());
    }

    void renderPointsTile(Graphics2D graphics2D, Totem totem) {
        if (!totem.isPointCapped()) return;

        OverlayUtil.renderTileOverlay(
                graphics2D, totem.getPointsGameObject(), null, config.pointsCappedColor());
    }

    private String getPointsText(Totem totem) {
        return totem.isPointCapped() ? "MAXIMUM" : Integer.toString(totem.getPoints());
    }

    private Point getTotemCanvasPoint(Graphics2D graphics2D, Totem totem, String text) {
        if (totem.hasTotemStarted()) {
            return totem.getTotemGameObject()
                    .getCanvasTextLocation(graphics2D, text, config.builtOffset());
        } else {
            return totem.getTotemGameObject()
                    .getCanvasTextLocation(graphics2D, text, config.unbuiltOffset());
        }
    }
}
