package ru.xdx505.playersonline;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.xdx505.playersonline.handler.HttpHandlerImpl;

public class PlayersOnlineMod implements ModInitializer {
    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(HttpHandlerImpl::new);
    }
}