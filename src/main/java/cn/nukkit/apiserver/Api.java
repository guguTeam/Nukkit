package cn.nukkit.apiserver;

import cn.nukkit.Server;
import cn.nukkit.apiserver.ws.WsClient;

public class Api {

    //假如你改了这个，先commit，再push,好像按错了。 ojbk
    //gugugugu
    private Server server;
    private WsClient ws;

    public Api(Server server) {
        this.server = server;
    }

    //server实例化完成之后，start之前
    public void init() {
        String ip = server.getConfig("api.ip", "127.0.0.1");
        String port = server.getConfig("api.port", "19132");
        this.ws = new WsClient(ip, Integer.valueOf(port));
        server.getLogger().info(ip);
    }

    //server reload之前
    public void reload() {

    }

    //server forceShutdown之前
    public void shutdown() {

    }

}
