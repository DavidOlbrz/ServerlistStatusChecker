package com.theagent.serverliststatuschecker;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Plugin(
        id = "serverliststatuschecker",
        name = "ServerlistStatusChecker",
        version = "1.0",
        authors = {"_The_Agent_"}
)
public class ServerlistStatusChecker {

    private final ProxyServer server;
    private final Logger logger;

    private ServerPinger pinger;
    private HashMap<String, Boolean> serverStatus;

    @Inject
    public ServerlistStatusChecker(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    /**
     * Triggers, when the proxy finished initialization
     *
     * @param event ProxyInitializeEvent
     */
    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        pinger = new ServerPinger(server, logger);

        logger.info("ServerlistStatusChecker has been initialized");

        // adds the automatic server ping as a scheduled task
        server.getScheduler()
                .buildTask(this, () -> {
                    try {
                        serverStatus = pinger.pingServers();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
                .repeat(1L, TimeUnit.MINUTES)
                .schedule();
    }


    /**
     * Triggers, when a player reloads the server list
     *
     * @param event ProxyPingEvent
     */
    @Subscribe()
    public void onServerPing(ProxyPingEvent event) {
        StatusDisplay.updateServerListEntry(event, serverStatus);
    }

}
