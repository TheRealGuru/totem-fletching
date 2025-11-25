package com.github.therealguru.totemfletching.service;

import com.github.therealguru.totemfletching.overlay.CarvingActionOverlay;
import com.github.therealguru.totemfletching.overlay.EntTrailOverlay;
import com.github.therealguru.totemfletching.overlay.PanelOverlay;
import com.github.therealguru.totemfletching.overlay.TotemFletchingOverlay;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class OverlayService {
    private final OverlayManager overlayManager;
    private final TotemFletchingOverlay gameOverlay;
    private final CarvingActionOverlay carvingOverlay;
    private final EntTrailOverlay entTrailOverlay;
    private final PanelOverlay panelOverlay;

    public void registerOverlays() {
        overlayManager.add(gameOverlay);
        overlayManager.add(carvingOverlay);
        overlayManager.add(entTrailOverlay);
        overlayManager.add(panelOverlay);
    }

    public void unregisterOverlays() {
        overlayManager.remove(gameOverlay);
        overlayManager.remove(carvingOverlay);
        overlayManager.remove(entTrailOverlay);
        overlayManager.remove(panelOverlay);
    }
}
