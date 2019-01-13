package cn.nukkit.apiserver;

import cn.nukkit.Server;

public class Api {

private Server server;

public Api(Server server) {
this.server = server;
}

//server实例化完成之后，start之前
public void init() {
String ip = server.getConfig("api.ip", "127.0.0.1");
String port = server.getConfig("api.port", "19132");
server.getLogger().info(port);
}

//server reload之前
public void reload() {

}

//server forceShutdown之前
public void shutdown() {

}

}