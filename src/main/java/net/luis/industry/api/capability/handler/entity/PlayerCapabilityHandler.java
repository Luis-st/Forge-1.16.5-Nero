package net.luis.industry.api.capability.handler.entity;

import java.util.ArrayList;
import java.util.List;

import net.luis.industry.api.capability.interfaces.entity.IPlayerCapability;
import net.luis.industry.api.item.OrbItem;
import net.luis.industry.api.item.RuneItem;
import net.luis.industry.core.NetworkHandler;
import net.luis.industry.core.messages.SyncPlayerCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class PlayerCapabilityHandler implements IPlayerCapability {
	
	private int blood = 0;
	
	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("blood", this.blood);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		this.blood = nbt.getInt("blood");
	}

	@Override
	public List<OrbItem> getOrbs(PlayerEntity player) {
		List<OrbItem> orbItems = new ArrayList<OrbItem>();
		IItemHandler inventory = player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElseThrow(NullPointerException::new);
		for (int i = 0; i < inventory.getSlots(); i++) {
			ItemStack itemStack = inventory.getStackInSlot(i);
			if (itemStack.getItem() instanceof OrbItem) {
				orbItems.add((OrbItem) itemStack.getItem());
			}
		}
		return orbItems;
	}
	
	@Override
	public boolean hasOrbs(PlayerEntity player) {
		return !this.getOrbs(player).isEmpty();
	}
	
	public boolean hasOrbs(List<OrbItem> orbItems) {
		return !orbItems.isEmpty() && orbItems != null;
	}

	@Override
	public int getBlood() {
		return this.blood;
	}

	@Override
	public int getMaxBlood(PlayerEntity player) {
		if (!this.hasOrbs(player)) {
			return 0;
		}
		List<OrbItem> orbItems = this.getOrbs(player);
		int maxBlood = 0;
		for (OrbItem orbItem : orbItems) {
			maxBlood += orbItem.getBloodCapability();
		}
		return maxBlood;
	}
	
	@Override
	public int getBloodForMax(PlayerEntity player) {
		int maxBlood = this.getMaxBlood(player);
		return maxBlood - getBlood();
	}
	
	@Override
	public void addBlood(PlayerEntity player, int blood) {
		int addedBlood = Math.min(this.getMaxBlood(player), this.blood + blood);
		this.blood += addedBlood;
		this.detectAndSendChanges();
	}

	@Override
	public boolean canBloodAdd(PlayerEntity player, int blood) {
		int maxBlood = this.getMaxBlood(player);
		return maxBlood >= this.getBlood() + blood;
	}

	@Override
	public int reduceBlood(int blood) {
		int reducedBlood = Math.min(this.getBlood(), blood);
		this.blood -= reducedBlood;
		this.detectAndSendChanges();
		return blood - reducedBlood;
	}
	
	@Override
	public boolean hasBlood() {
		return this.blood > 0;
	}
	
	@Override
	public boolean hasBlood(int blood) {
		return this.blood >= blood;
	}
	
	@Override
	public boolean hasMaxBlood(List<OrbItem> orbItems) {
		if (!this.hasOrbs(orbItems)) {
			return false;
		}
		int bloodCapability = 0;
		for (OrbItem orbItem : orbItems) {
			bloodCapability += orbItem.getBloodCapability();
		}
		return this.getBlood() >= bloodCapability;
	}

	
	@Override
	public boolean shouldDamage(RuneItem runeItem) {
		return runeItem.getRuneType().getBloodCost() > 0 && this.hasBlood();
	}

	@Override
	public void detectAndSendChanges() {
		NetworkManager networkManager = Minecraft.getInstance().getConnection().getConnection();
		NetworkHandler.simpleChannel.sendTo(new SyncPlayerCapability(), networkManager, NetworkDirection.PLAY_TO_CLIENT);
	}

}
