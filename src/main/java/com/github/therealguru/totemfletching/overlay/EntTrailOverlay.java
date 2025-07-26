package com.github.therealguru.totemfletching.overlay;

import com.github.therealguru.totemfletching.TotemFletchingConfig;
import com.github.therealguru.totemfletching.TotemFletchingPlugin;
import com.github.therealguru.totemfletching.service.EntTrailService;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.GameObject;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;

@Slf4j
@Singleton
public class EntTrailOverlay extends Overlay {
    private final EntTrailService service;
    private final TotemFletchingConfig config;

    @Inject
    public EntTrailOverlay(TotemFletchingPlugin plugin, TotemFletchingConfig config, EntTrailService service) {
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
