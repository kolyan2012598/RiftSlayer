package vortex.client.vortexclient.command.commands;

import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import vortex.client.vortexclient.command.Command;
import vortex.client.vortexclient.module.modules.render.BlockESP;

public class BlockESPCommand extends Command {
    public BlockESPCommand() {
        super("blockesp", "Adds or removes blocks from the BlockESP list.");
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            send("§cUsage: .blockesp <add|remove> <block_id>");
            return;
        }

        Block block = Registry.BLOCK.get(new Identifier(args[1]));
        if (block == null) {
            send("§cBlock not found.");
            return;
        }

        if (args[0].equalsIgnoreCase("add")) {
            BlockESP.blocks.add(block);
            send("§aAdded " + args[1] + " to BlockESP.");
        } else if (args[0].equalsIgnoreCase("remove")) {
            BlockESP.blocks.remove(block);
            send("§aRemoved " + args[1] + " from BlockESP.");
        }
    }
}
