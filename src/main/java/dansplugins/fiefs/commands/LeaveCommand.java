package dansplugins.fiefs.commands;

import dansplugins.factionsystem.externalapi.MF_Faction;
import dansplugins.fiefs.MedievalFactionsIntegrator;
import dansplugins.fiefs.data.PersistentData;
import dansplugins.fiefs.objects.Fief;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveCommand {

    public boolean execute(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return false;
        }

        Player player = (Player) sender;

        MF_Faction faction = MedievalFactionsIntegrator.getInstance().getAPI().getFaction(player);
        if (faction == null) {
            player.sendMessage(ChatColor.RED + "You must be in a faction to use this command.");
            return false;
        }

        Fief fief = PersistentData.getInstance().getFief(player);
        if (fief == null) {
            player.sendMessage(ChatColor.RED + "You must be in a fief to use this command.");
            return false;
        }

        if (fief.getOwnerUUID().equals(player.getUniqueId())) {
            PersistentData.getInstance().removeFief(fief);
            player.sendMessage(ChatColor.GREEN + "Left. Your fief was disbanded since you were the owner.");
            return true;
        }

        fief.removeMember(player.getUniqueId());
        player.sendMessage(ChatColor.GREEN + "Left.");

        // TODO: inform fief members that the player has left the fief

        return true;
    }

}
