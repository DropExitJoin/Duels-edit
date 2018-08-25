package me.realized.duels.command;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import me.realized.duels.DuelsPlugin;
import me.realized.duels.arena.ArenaManager;
import me.realized.duels.betting.BettingManager;
import me.realized.duels.config.Config;
import me.realized.duels.config.Lang;
import me.realized.duels.data.UserManager;
import me.realized.duels.duel.DuelManager;
import me.realized.duels.hook.HookManager;
import me.realized.duels.inventories.InventoryManager;
import me.realized.duels.kit.KitManager;
import me.realized.duels.player.PlayerInfoManager;
import me.realized.duels.queue.QueueManager;
import me.realized.duels.queue.sign.QueueSignManager;
import me.realized.duels.request.RequestManager;
import me.realized.duels.setting.SettingsManager;
import me.realized.duels.spectate.SpectateManager;
import me.realized.duels.util.StringUtil;
import me.realized.duels.util.command.AbstractCommand;
import org.bukkit.command.CommandSender;

public abstract class BaseCommand extends AbstractCommand<DuelsPlugin> {

    protected final DuelsPlugin plugin;
    protected final Config config;
    protected final Lang lang;
    protected final UserManager userManager;
    protected final ArenaManager arenaManager;
    protected final KitManager kitManager;
    protected final QueueManager queueManager;
    protected final QueueSignManager queueSignManager;
    protected final SettingsManager settingManager;
    protected final PlayerInfoManager playerManager;
    protected final SpectateManager spectateManager;
    protected final BettingManager bettingManager;
    protected final InventoryManager inventoryManager;
    protected final DuelManager duelManager;
    protected final RequestManager requestManager;
    protected final HookManager hookManager;

    /**
     * Constructor for a sub command
     */
    protected BaseCommand(final DuelsPlugin plugin, final String name, final String usage, final String description, final String permission, final int length,
        final boolean playerOnly, final String... aliases) {
        super(plugin, name, usage, description, permission, length, playerOnly, aliases);
        this.plugin = plugin;
        this.config = plugin.getConfiguration();
        this.lang = plugin.getLang();
        this.userManager = plugin.getUserManager();
        this.arenaManager = plugin.getArenaManager();
        this.kitManager = plugin.getKitManager();
        this.queueManager = plugin.getQueueManager();
        this.queueSignManager = plugin.getQueueSignManager();
        this.settingManager = plugin.getSettingManager();
        this.playerManager = plugin.getPlayerManager();
        this.spectateManager = plugin.getSpectateManager();
        this.bettingManager = plugin.getBettingManager();
        this.inventoryManager = plugin.getInventoryManager();
        this.duelManager = plugin.getDuelManager();
        this.requestManager = plugin.getRequestManager();
        this.hookManager = plugin.getHookManager();
    }

    /**
     * Constructor for a sub command, inherits parent permission
     */
    protected BaseCommand(final DuelsPlugin plugin, final String name, final String usage, final String description, final int length, final boolean playerOnly,
        final String... aliases) {
        this(plugin, name, usage, description, null, length, playerOnly, aliases);
    }

    /**
     * Constructor for a parent command
     */
    protected BaseCommand(final DuelsPlugin plugin, final String name, final String permission, final boolean playerOnly) {
        this(plugin, name, null, null, permission, -1, playerOnly);
    }

    @Override
    protected void handleMessage(final CommandSender sender, final MessageType type, final String... args) {
        switch (type) {
            case PLAYER_ONLY:
                super.handleMessage(sender, type, args);
                break;
            case NO_PERMISSION:
                lang.sendMessage(sender, "ERROR.no-permission", "permission", args[0]);
                break;
            case SUB_COMMAND_INVALID:
                lang.sendMessage(sender, "ERROR.command.invalid-sub-command", "command", args[0], "argument", args[1]);
                break;
            case SUB_COMMAND_USAGE:
                lang.sendMessage(sender, "COMMAND.sub-command-usage", "command", args[0], "usage", args[1], "description", args[2]);
                break;
        }
    }

    protected <V> List<String> handleTabCompletion(final CommandSender sender, final String arg, final String type, final Collection<V> collection,
        final Function<V, String> function) {
        sender.sendMessage(StringUtil.color("&e&l(!) &r&eSpaces in " + type + " name has been replaced to a dash to support tab completion."));
        return collection.stream()
            .filter(value -> function.apply(value).toLowerCase().startsWith(arg.toLowerCase()))
            .map(value -> function.apply(value).replace(" ", "-"))
            .collect(Collectors.toList());
    }
}
