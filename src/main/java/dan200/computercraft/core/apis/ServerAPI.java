package dan200.computercraft.core.apis;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.core.computer.IComputerEnvironment;
import dan200.computercraft.core.terminal.Terminal;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.List;

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
                        "getNameFromUUID"
                };
    }

    @Override
    public Object[] callMethod(ILuaContext context, int method, Object[] arguments) throws LuaException, InterruptedException {
        List<EntityPlayerMP> playerList = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers();
        switch (method) {
            case 0:
                String[] players = new String[playerList.size()];
                for (int i = 0; i < playerList.size(); i++) {
                    EntityPlayerMP player = playerList.get(i);
                    players[i] = player.getUniqueID().toString();
                }
                return players;
            case 1:
                String[] playerNames = new String[playerList.size()];
                for (int i = 0; i < playerList.size(); i++) {
                    EntityPlayerMP player = playerList.get(i);
                    playerNames[i] = player.getDisplayNameString();
                }
                return playerNames;
            case 2:
                return null;
        }
        return new Object[0];
    }
}