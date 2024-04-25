package com.theagent.serverliststatuschecker;

import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class ServerPinger {

    Collection<RegisteredServer> allServers;
    Logger logger;

    public ServerPinger(ProxyServer server, Logger logger) {
        allServers = server.getAllServers();
        this.logger = logger;
    }

    /**
     * Pings all configured Servers and saves their online statuses
     *
     * @throws InterruptedException Operation was not completed successfully
     */
    protected void pingServers() throws InterruptedException {
        HashMap<String, Boolean> pingResults = new HashMap<>();

        for (RegisteredServer server : allServers) {
            boolean status = false;
            try {
                status = server.ping().get() != null;
            } catch (ExecutionException e) {
                logger.warn(String.format("Server %s appears to be offline", server.getServerInfo().getName()));
            }
            pingResults.put(server.getServerInfo().getName(), status);
        }

        Set<String> keys = pingResults.keySet();
        for (String key : keys) {
            System.out.println(key + ": " + pingResults.get(key));
        }
    }

}
