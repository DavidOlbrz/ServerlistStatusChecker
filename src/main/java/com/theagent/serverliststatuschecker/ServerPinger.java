package com.theagent.serverliststatuschecker;

import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.HashMap;
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
     * @return map of all servers and their online statuses
     * @throws InterruptedException Operation was not completed successfully
     */
    protected HashMap<String, Boolean> pingServers() throws InterruptedException {
        HashMap<String, Boolean> pingResults = new HashMap<>(); // the server statuses will be saved here

        // iterate through all configured servers and get their online status
        for (RegisteredServer server : allServers) {
            boolean status = false;
            try {
                status = server.ping().get() != null;
            } catch (ExecutionException e) {
                //logger.warn(String.format("Server %s appears to be offline", server.getServerInfo().getName()));
            }
            pingResults.put(server.getServerInfo().getName(), status); // add status of server
        }

        return pingResults;
    }

}
