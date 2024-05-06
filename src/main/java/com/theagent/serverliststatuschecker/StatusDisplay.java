package com.theagent.serverliststatuschecker;

import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.proxy.server.ServerPing;

import java.util.HashMap;
import java.util.UUID;

public class StatusDisplay {

    private static final String ONLINE = "§a[x] ";
    private static final String OFFLINE = "§c[-] ";

    /**
     * Updates the server list entry for the player
     *
     * @param event        ProxyPingEvent
     * @param serverStatus map of all servers and their online statuses
     */
    public static void updateServerListEntry(ProxyPingEvent event, HashMap<String, Boolean> serverStatus) {
        ServerPing.Builder ping = event.getPing().asBuilder(); // get the currently displayed ping information

        int serverCount = serverStatus.size(); // get the amount of configured servers
        Object[] serverNames = serverStatus.keySet().toArray(); // save all map keys into an array

        // create new SamplePlayer array, which will hold the server list and their statuses
        ServerPing.SamplePlayer[] statusList = new ServerPing.SamplePlayer[serverCount + 1];

        // add a header to the top of the list
        statusList[0] = new ServerPing.SamplePlayer("§f§nStatus:", UUID.randomUUID());

        // iterate through all servers and build the server status list
        for (int i = 0; i < serverCount; i++) {
            statusList[i + 1] = new ServerPing.SamplePlayer((serverStatus.get(serverNames[i].toString()) ? ONLINE : OFFLINE) + serverNames[i].toString(), UUID.randomUUID());
        }

        ping.samplePlayers(statusList); // add the list to the ping builder
        event.setPing(ping.build()); // apply the new ping information
    }

}
