package cn.nukkit.apiserver.ws;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;

import java.net.URI;

public class WsClient {

    private static final EventLoopGroup group = new NioEventLoopGroup();
    private Channel ch;

    public WsClient(String ip, int port) {
        try {
            URI uri = new URI("ws://" + ip + ":" + String.valueOf(port));
            Bootstrap bootstrap = new Bootstrap();
            final WsClientHandler handler = new WsClientHandler(WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V13, null, false, HttpHeaders.EMPTY_HEADERS, 1280000));

            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("http-codec", new HttpClientCodec());
                            pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
                            pipeline.addLast("ws-handler", handler);
                        }
                    });

            ch = bootstrap.connect(uri.getHost(), uri.getPort()).sync().channel();
            handler.handshakeFuture().sync();
            ch.writeAndFlush(new TextWebSocketFrame("111"));
            ByteBuf byteBuf = Unpooled.directBuffer(16);
            byteBuf.writeBytes(new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x01});
            ch.writeAndFlush(new BinaryWebSocketFrame(byteBuf));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
