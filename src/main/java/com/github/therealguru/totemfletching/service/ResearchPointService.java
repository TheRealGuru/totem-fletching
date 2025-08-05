package com.github.therealguru.totemfletching.service;

import com.github.therealguru.totemfletching.TotemFletchingConfig;
import com.github.therealguru.totemfletching.model.Constants;
import net.runelite.api.Client;

import javax.inject.Inject;

public class ResearchPointService {

    private final Client client;
    private final TotemFletchingConfig config;
    private Integer RESEARCH_POINTS;

    @Inject
    public ResearchPointService(TotemFletchingConfig config, Client client) {
        this.client = client;
        this.config = config;
    }

    public void onVarbitChanged() {
        if (!config.renderPanel()) return;
        RESEARCH_POINTS = client.getVarpValue(Constants.Varps.RESEARCH_POINTS);
    }

    public int getResearchPoints() {
        return RESEARCH_POINTS != null ? RESEARCH_POINTS : client.getVarpValue(Constants.Varps.RESEARCH_POINTS);
    }
}
