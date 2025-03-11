package org.soonsal.punishplugin.command.user;

import org.bukkit.entity.Player;
import org.soonsal.punishplugin.command.util.SoonSaLCommand;
import org.soonsal.punishplugin.gui.list.WarningListGUI;
import org.soonsal.punishplugin.user.UserManager;

public class WarningUserCommand extends SoonSaLCommand {
    @Override
    public void runCommand(Player player, String label, String[] args) {
        WarningListGUI gui = new WarningListGUI(UserManager.get(player));
        gui.open(player);
    }

    @Override
    public String getLabel() {
        return "경고";
    }

    @Override
    public boolean isAdminCommand() {
        return false;
    }
}
