package net.luis.industry.api.inventory;

import java.util.List;

import net.luis.industry.api.recipe.IModRecipe;
import net.luis.industry.api.util.ItemStackList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public interface IRecipeInventory {
	
	ItemStackList getInput();
	
	ItemStackList getOutput();
	
	int sizeInput();
	
	int sizeOutput();
	
	boolean hasEmptySlots(ItemStackList inventory);
	
	boolean isSlotEmpty(ItemStackList inventory, int slot);
	
	List<InventorySlot> hasItemsForRecipe(ItemStackList inventory, IModRecipe recipe);
	
	InventorySlot getSlotWithStack(ItemStackList inventory, ItemStack itemStack);
	
	ItemStack insert(int slot, ItemStack itemStack, ItemStackList inventory);
	
	ItemStack insertAll(int slot, List<ItemStack> itemStacks, ItemStackList inventory);
	
	ItemStack extract(int slot, ItemStack itemStack, ItemStackList inventory, boolean extractNext);
	
	List<ItemStack> extractAll(List<ItemStack> itemStacks, ItemStackList inventory);
	
	void extractRecipe(IModRecipe recipe, ItemStackList inventory);
	
	void clearInput();
	
	void clearOutput();
	
	ItemStackList clearAndGetInput();
	
	ItemStackList clearAndGetOutput();
	
	CompoundNBT serializeNBT(CompoundNBT nbt);
	
	void deserializeNBT(CompoundNBT nbt);
	
}
