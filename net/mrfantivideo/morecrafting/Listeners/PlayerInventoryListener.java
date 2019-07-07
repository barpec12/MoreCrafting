package net.mrfantivideo.morecrafting.Listeners;

import net.mrfantivideo.morecrafting.Main;
import net.mrfantivideo.morecrafting.Recipes.CustomRecipe;
import net.mrfantivideo.morecrafting.Recipes.RecipesManager;
import net.mrfantivideo.morecrafting.Utils.NBTEditor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class PlayerInventoryListener implements Listener
{
    @EventHandler
    public void OnPlayerInventoryClick(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        InventoryView inv = event.getView();
        ItemStack stack = event.getCurrentItem();

        if (stack == null)
            return;

        if (inv.getTitle().equalsIgnoreCase(Main.GetInstance().GetConfigMessages().GetGUITitle() + Main.GetInstance().GetConfigMessages().GetGUITitleCrafting()))
        {
            event.setCancelled(true);
            return;
        }

        if (inv.getTitle().equalsIgnoreCase(Main.GetInstance().GetConfigMessages().GetGUITitle() + Main.GetInstance().GetConfigMessages().GetGUITitleFurnace()))
        {
            event.setCancelled(true);
            return;
        }

        if (inv.getTitle().equalsIgnoreCase(Main.GetInstance().GetConfigMessages().GetGUITitle() + Main.GetInstance().GetConfigMessages().GetGUITitleSmoker()))
        {
            event.setCancelled(true);
            return;
        }

        if (inv.getTitle().equalsIgnoreCase(Main.GetInstance().GetConfigMessages().GetGUITitle() + Main.GetInstance().GetConfigMessages().GetGUITitleBlasting()))
        {
            event.setCancelled(true);
            return;
        }

        if (inv.getTitle().equalsIgnoreCase(Main.GetInstance().GetConfigMessages().GetGUITitle() + Main.GetInstance().GetConfigMessages().GetGUITitleStonecutting()))
        {
            event.setCancelled(true);
            return;
        }

        if (inv.getTitle().equalsIgnoreCase(Main.GetInstance().GetConfigMessages().GetGUITitle() + Main.GetInstance().GetConfigMessages().GetGUITitleCampfire()))
        {
            event.setCancelled(true);
            return;
        }

        if(!NBTEditor.contains(stack, "recipeName"))
            return;

        if (inv.getTitle().equalsIgnoreCase(Main.GetInstance().GetConfigMessages().GetGUITitle() + Main.GetInstance().GetConfigMessages().GetGUITitleMain()))
        {
            CustomRecipe recipe = RecipesManager.GetInstance().GetRecipeByName(NBTEditor.getString(stack, "recipeName"));
            if (recipe != null)
            {
                Inventory inventory;
                if (recipe.IsFurnaceRecipe())
                {
                    inventory = Bukkit.createInventory(null, InventoryType.FURNACE, Main.GetInstance().GetConfigMessages().GetGUITitle() + Main.GetInstance().GetConfigMessages().GetGUITitleFurnace());
                    inventory.setItem(0, recipe.GetFurnaceRecipe().getInput().clone());
                    inventory.setItem(1, new ItemStack(Material.COAL));
                    inventory.setItem(2, recipe.GetResult().clone());
                } else if (recipe.IsSmokingRecipe())
                {
                    inventory = Bukkit.createInventory(null, InventoryType.FURNACE, Main.GetInstance().GetConfigMessages().GetGUITitle() + Main.GetInstance().GetConfigMessages().GetGUITitleSmoker());
                    inventory.setItem(0, recipe.GetSmokingRecipe().getInput().clone());
                    inventory.setItem(1, new ItemStack(Material.COAL));
                    inventory.setItem(2, recipe.GetResult().clone());
                } else if (recipe.IsBlastingRecipe())
                {
                    inventory = Bukkit.createInventory(null, InventoryType.FURNACE, Main.GetInstance().GetConfigMessages().GetGUITitle() + Main.GetInstance().GetConfigMessages().GetGUITitleBlasting());
                    inventory.setItem(0, recipe.GetBlastingRecipe().getInput().clone());
                    inventory.setItem(1, new ItemStack(Material.COAL));
                    inventory.setItem(2, recipe.GetResult().clone());
                } else if (recipe.IsStonecuttingRecipe())
                {
                    inventory = Bukkit.createInventory(null, InventoryType.STONECUTTER, Main.GetInstance().GetConfigMessages().GetGUITitle() + Main.GetInstance().GetConfigMessages().GetGUITitleStonecutting());
                    inventory.setItem(0, recipe.GetStonecuttingRecipe().getInput().clone());
                    inventory.setItem(1, recipe.GetResult().clone());
                } else if (recipe.IsCampfireRecipe())
                {
                    inventory = Bukkit.createInventory(null, InventoryType.FURNACE, Main.GetInstance().GetConfigMessages().GetGUITitle() + Main.GetInstance().GetConfigMessages().GetGUITitleCampfire());
                    inventory.setItem(0, recipe.GetCampfireRecipe().getInput().clone());
                    inventory.setItem(1, new ItemStack(Material.CAMPFIRE));
                    inventory.setItem(2, recipe.GetResult().clone());
                } else
                {
                    inventory = Bukkit.createInventory(null, InventoryType.WORKBENCH, Main.GetInstance().GetConfigMessages().GetGUITitle() + Main.GetInstance().GetConfigMessages().GetGUITitleCrafting());
                    for (Map.Entry<Character, ItemStack> entry : recipe.GetRecipe().getIngredientMap().entrySet())
                    {
                        if (entry.getValue() == null)
                            continue;
                        inventory.setItem(Integer.valueOf(entry.getKey().toString()), entry.getValue().clone());
                    }
                    inventory.setItem(0, recipe.GetResult().clone());
                }
                player.openInventory(inventory);
                event.setCancelled(true);
            }
        }
    }
}