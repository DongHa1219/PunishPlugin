package org.soonsal.punishplugin.command.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.soonsal.punishplugin.version.VersionHandler;
import org.soonsal.punishplugin.command.CommandPermission;
import org.soonsal.punishplugin.command.admin.list.*;
import org.soonsal.punishplugin.command.util.SoonSaLCommand;
import org.soonsal.punishplugin.command.util.SubCommand;
import org.soonsal.punishplugin.config.list.Message;
import org.soonsal.punishplugin.util.Info;

import java.util.ArrayList;
import java.util.List;

//
// /경고관리 콘피그리로드 : 콘피그를 리로드합니다. : warning.configReload
// /경고관리 처벌관리 : 목표 경고 도달시 적용할 처벌을 설정합니다. : warning.setting
// /경고관리 처벌삭제 <횟수> : 해당 경고 도달시 적용되는 처벌을 삭제합니다. : warning.setting
// /경고관리 지급 <플레이어> <횟수> <사유> : 해당 플레이어에게 경고를 지급합니다. : warning.increase
// /경고관리 차감 <플레이어> <횟수> <사유> : 해당 플레이어에게 경고를 차감합니다. : warning.decrease
// /경고관리 확인 <플레이어> : 해당 플레이어의 경고 히스토리를 확인합니다. : warning.confirm
// /경고관리 초기화 <플레이어> : 해당 플레이어의 받은 경고들을 초기화합니다. (받은 처벌도 초기화) : warning.reset
// /경고관리 처벌해제 <플레이어> <경고횟수> : 해당 경고때 받은 처벌을 해제합니다. : warning.pardon
//
public class WarningAdminCommand extends SoonSaLCommand implements TabCompleter {
    private final List<SubCommand> subCommands = new ArrayList<>();

    public WarningAdminCommand() {
        subCommands.add(new ConfigReloadCommand());
        subCommands.add(new WarningIncreaseCommand());
        subCommands.add(new WarningDecreaseCommand());
        subCommands.add(new PunishSettingCommand());
        subCommands.add(new PunishSettingRemoveCommand());
        subCommands.add(new PunishHistoryConfirmCommand());
        subCommands.add(new WarningResetCommand());
        subCommands.add(new PunishPardonCommand());
    }

    @Override
    public void runCommand(Player player, String label, String[] args) {
        if (!CommandPermission.hasAnyPermission(player)) {
            Info.sendMessage(player, Message.NO_PERMISSION.getMessage());
            return;
        }
        if (args.length == 0) {
            helpMessage(player);
            return;
        }

        for (SubCommand subCommand : subCommands) {
            if (subCommand.getName().equalsIgnoreCase(args[0])) {
                if (subCommand.getPermission() == null || player.hasPermission(subCommand.getPermission())) {
                    subCommand.perform(player, args);
                } else {
                    Info.sendMessage(player, Message.NO_PERMISSION.getMessage());
                }

                return;
            }
        }

        Info.sendMessage(player, "알 수 없는 명령어입니다.");
    }

    @Override
    public String getLabel() {
        return "경고관리";
    }

    @Override
    public boolean isAdminCommand() {
        return true;
    }

    public void helpMessage(Player player) {
        Info.sendMessage(player, "&a&l명령어 목록");
        for (SubCommand subCommand : subCommands) {
            Info.sendMessage(player, subCommand.getSyntax() + ": " + subCommand.getDescription());
        }
        Info.sendMessage(player, "&8v" + VersionHandler.RUNTIME_VERSION.getValue() + " by SoonSaL_");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        int length = strings.length;

        if (length == 1) {
            for (SubCommand subCommand : subCommands) {
                if (subCommand.getPermission() != null && !player.hasPermission(subCommand.getPermission())) {
                    continue;
                }

                return subCommands
                        .stream()
                        .map(SubCommand::getName)
                        .toList();
            }
        } else {
            String label = strings[0];

            for (SubCommand subCommand : subCommands) {
                if (subCommand.getName().equalsIgnoreCase(label)) {
                    String[] args = subCommand.getSubCommands();

                    if (args.length <= length) {
                        return null;
                    }

                    String index = args[length];

                    if (index.equals("<플레이어>")) {
                        return null;
                    }

                    return List.of(index);
                }
            }
        }

        return null;
    }
}
