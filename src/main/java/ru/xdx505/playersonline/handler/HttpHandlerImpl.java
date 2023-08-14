package ru.xdx505.playersonline.handler;

import com.google.gson.Gson;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.client.ObjectMapper;
import com.mojang.authlib.properties.Property;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import net.minecraft.block.PlayerSkullBlock;
import net.minecraft.data.client.TexturedModel;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class HttpHandlerImpl implements HttpHandler {
    private final Gson gson = new Gson();
    private final int SERVER_PORT = 5555;
    private final String MONITOR_PATH = "/monitor";

    private MinecraftServer minecraftServer;

    public HttpHandlerImpl(MinecraftServer minecraftServer) {
        try {
            this.minecraftServer = minecraftServer;
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(SERVER_PORT), 0);
            httpServer.createContext(MONITOR_PATH, this);
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        byte[] response = new byte[0];
        if (minecraftServer != null) {
            String[] onlinePlayerNames = minecraftServer.getPlayerNames();
            response = gson.toJson(onlinePlayerNames).getBytes(StandardCharsets.UTF_8);
        }

        exchange.sendResponseHeaders(200, response.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response);
        }
    }
}
