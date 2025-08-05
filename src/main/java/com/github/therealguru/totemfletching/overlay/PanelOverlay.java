package com.github.therealguru.totemfletching.overlay;

import com.github.therealguru.totemfletching.TotemFletchingConfig;
import com.github.therealguru.totemfletching.TotemFletchingPlugin;
import com.github.therealguru.totemfletching.model.Totem;
import com.github.therealguru.totemfletching.model.TotemRegions;
import com.github.therealguru.totemfletching.service.ResearchPointService;
import com.github.therealguru.totemfletching.service.TotemService;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;

import javax.inject.Inject;
import java.awt.*;

public class PanelOverlay extends OverlayPanel {
    private final Client client;
    private final TotemFletchingConfig config;
    private final ResearchPointService pointService;
    private final TotemService totemService;

    @Inject
    public PanelOverlay(
            TotemFletchingPlugin plugin,
            TotemFletchingConfig config,
            TotemService totemService,
            ResearchPointService pointService,
            Client client) {
        super(plugin);
        this.client = client;
        this.config = config;
        this.pointService = pointService;
        this.totemService = totemService;
        setPosition(OverlayPosition.TOP_LEFT);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if (!config.renderPanel()) return null;

        boolean regionId = TotemRegions.isValid(client.getLocalPlayer().getWorldLocation().getRegionID());

        if (!regionId) return null;

        panelComponent.getChildren().add(LineComponent.builder()
            .left("Research points:")
            .right(String.valueOf(pointService.getResearchPoints()))
            .build());

        panelComponent.getChildren().add(LineComponent.builder()
            .left("Unclaimed points:")
            .right(String.valueOf(totemService.getTotems().stream().mapToInt(Totem::getPoints).sum() / 100))
            .build());

        return super.render(graphics);
    }
}
