package com.theagent.serverliststatuschecker;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import org.slf4j.Logger;

@Plugin(
        id = "serverliststatuschecker",
        name = "ServerlistStatusChecker",
        version = "1.0-SNAPSHOT",
        authors = {"_The_Agent_"}
)
public class ServerlistStatusChecker {

    @Inject
    private Logger logger;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        logger.info("ServerlistStatusChecker has been initialized");
    }
}
