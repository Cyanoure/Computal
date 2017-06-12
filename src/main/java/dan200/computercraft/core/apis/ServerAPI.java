package dan200.computercraft.core.apis;

import dan200.computercraft.ComputerCraft;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.core.computer.IComputerEnvironment;
import dan200.computercraft.core.terminal.Terminal;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ServerAPI implements ILuaAPI {
    private Terminal m_terminal;
    private IComputerEnvironment m_environment;

    public ServerAPI(IAPIEnvironment _environment) {
        m_terminal = _environment.getTerminal();
        m_environment = _environment.getComputerEnvironment();
    }

    @Override
    public String[] getNames() {
        return new String[]{
                "server"
        };
    }

    @Override
    public void startup() {

    }

    @Override
    public void advance(double _dt) {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public String[] getMethodNames() {
        return new String[]
                {
                        "getOnlinePlayerUUIDs",
                        "getOnlinePlayerNames",
                        "getCurrentPlayerCount",
                        "getNameFromUUID",
                        "getUptime"
                };
    }

    @Override
    public Object[] callMethod(ILuaContext context, int method, Object[] arguments) throws LuaException, InterruptedException {
        List<EntityPlayerMP> playerList = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers();
        switch (method) {
            case 0:
                String playerUUIDs = "";
                for (int i = 0; i < playerList.size(); i++) {
                    EntityPlayerMP player = playerList.get(i);
                    playerUUIDs += player.getUniqueID().toString();
                    if (i < playerList.size() - 1)
                        playerUUIDs += ",";
                }
                return new Object[]{playerUUIDs};
            case 1:
                String playerNames = "";
                for (int i = 0; i < playerList.size(); i++) {
                    EntityPlayerMP player = playerList.get(i);
                    playerNames += player.getDisplayNameString();
                    if (i < playerList.size() - 1)
                        playerNames += ",";
                }
                return new Object[]{playerNames};
            case 2:
                return new Object[]{playerList.size()};
            case 3:
                if (arguments.length < 1 || !(arguments[0] instanceof String))
                    throw new LuaException("Expected String");
                UUID uuid;
                try {
                    uuid = UUID.fromString((String) arguments[0]);
                } catch (Exception e) {
                    if (e.getMessage().contains("Invalid UUID"))
                        throw new LuaException("Invalid UUID");
                    uuid = null;
                }
                if (uuid == null)
                    throw new LuaException("Unexpected Error");
                EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(uuid);
                if (player == null)
                    throw new LuaException("Player Not Found");
                String name = player.getDisplayNameString();
                return new Object[]{name};
            case 4:
                return new Object[]{ComputerCraft.UPTIME.elapsed(TimeUnit.MILLISECONDS)};
        }
        return new Object[0];
    }
}