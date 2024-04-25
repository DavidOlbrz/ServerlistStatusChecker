package com.theagent.serverliststatuschecker;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.util.concurrent.ExecutionException;

@Plugin(
        id = "serverliststatuschecker",
        name = "ServerlistStatusChecker",
        version = "1.0-SNAPSHOT",
        authors = {"_The_Agent_"}
)
public class ServerlistStatusChecker {

    private final ProxyServer server;
    private final Logger logger;

    private ServerPinger pinger;

    @Inject
    public ServerlistStatusChecker(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        pinger = new ServerPinger(server, logger);

        logger.info("ServerlistStatusChecker has been initialized");
    }

    @Subscribe
    public void onServerPing(ProxyPingEvent event) {
        logger.debug("Server was pinged!"); // TODO Remove before first production build
        try {
            pinger.pingServers();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
