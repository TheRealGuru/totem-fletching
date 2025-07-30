package com.github.therealguru.totemfletching.service;

import com.github.therealguru.totemfletching.overlay.CarvingActionOverlay;
import com.github.therealguru.totemfletching.overlay.EntTrailOverlay;
import com.github.therealguru.totemfletching.overlay.TotemFletchingOverlay;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@Singleton
public class OverlayService {
    @Inject private OverlayManager overlayManager;

    @Inject private TotemFletchingOverlay gameOverlay;
    @Inject private CarvingActionOverlay carvingOverlay;
    @Inject private EntTrailOverlay entTrailOverlay;

    public OverlayService() {}

    public void registerOverlays() {
        overlayManager.add(gameOverlay);
        overlayManager.add(carvingOverlay);
        overlayManager.add(entTrailOverlay);
    }

    public void unregisterOverlays() {
        overlayManager.remove(gameOverlay);
        overlayManager.remove(carvingOverlay);
        overlayManager.remove(entTrailOverlay);
    }
}
