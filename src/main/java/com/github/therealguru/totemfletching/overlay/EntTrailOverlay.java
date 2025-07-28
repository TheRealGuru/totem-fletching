package com.github.therealguru.totemfletching.overlay;

import com.github.therealguru.totemfletching.TotemFletchingConfig;
import com.github.therealguru.totemfletching.TotemFletchingPlugin;
import com.github.therealguru.totemfletching.service.EntTrailService;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

import java.awt.*;

public class EntTrailOverlay extends Overlay {

    private final EntTrailService service;
    private final TotemFletchingConfig config;

    public EntTrailOverlay(TotemFletchingPlugin plugin, TotemFletchingConfig config, EntTrailService service, Client client) {
        super(plugin);
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        this.service = service;
        this.config = config;
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if(!config.renderEntTrails()) return null;

        for(GameObject object : service.getInactiveEntTrails()) {
            if(object == null) continue;

            renderEntTrail(object, graphics);
        }
        return null;
    }

    void renderEntTrail(final GameObject gameObject, Graphics2D graphics2D) {
        OverlayUtil.renderTileOverlay(graphics2D, gameObject, null, Color.RED);
    }
}
