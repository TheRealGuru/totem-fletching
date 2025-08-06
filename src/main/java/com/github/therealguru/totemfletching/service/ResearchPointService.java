package com.github.therealguru.totemfletching.service;

import com.github.therealguru.totemfletching.TotemFletchingConfig;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.gameval.VarPlayerID;

public class ResearchPointService {

    private final Client client;
    private final TotemFletchingConfig config;
    private Integer researchPoints;

    @Inject
    public ResearchPointService(TotemFletchingConfig config, Client client) {
        this.client = client;
        this.config = config;
    }

    public void onVarbitChanged() {
        if (!config.renderPanel()) return;
        researchPoints = client.getVarpValue(VarPlayerID.ENT_TOTEMS_RESEARCH_POINTS);
    }

    public int getResearchPoints() {
        if (researchPoints == null) {
            researchPoints = client.getVarpValue(VarPlayerID.ENT_TOTEMS_RESEARCH_POINTS);
        }
        return researchPoints;
    }
}
