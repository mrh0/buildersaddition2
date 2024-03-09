package github.mrh0.buildersaddition2.blocks.carpenters_table;

import com.google.common.collect.Lists;
import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.recipe.carpenter.CarpenterRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

import java.util.List;

public class CarpenterTableMenu extends AbstractContainerMenu {
    public static final int INPUT_SLOT = 0;
    public static final int RESULT_SLOT = 4;
    private static final int INV_SLOT_START = 5;
    private static final int INV_SLOT_END = 32;
    private static final int USE_ROW_SLOT_START = 32;
    private static final int USE_ROW_SLOT_END = 41;
    private final DataSlot selectedRecipeIndex = DataSlot.standalone();
    private final Level level;
    private List<RecipeHolder<CarpenterRecipe>> recipes = Lists.newArrayList();
    private List<ItemStack> inputCache = NonNullList.withSize(4, ItemStack.EMPTY);
    long lastSoundTime;
    final List<Slot> inputSlots;
    final Slot resultSlot;
    Runnable slotUpdateListener = () -> {
    };
    public final SimpleContainer inputContainer = new SimpleContainer(4) {
        @Override
        public void setChanged() {
            super.setChanged();
            CarpenterTableMenu.this.slotsChanged(this);
            CarpenterTableMenu.this.slotUpdateListener.run();
        }
    };
    final ResultContainer resultContainer = new ResultContainer();

    public CarpenterTableMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, Component.empty());
    }

    public CarpenterTableMenu(int id, Inventory inv, Component component) {
        super(Index.CARPENTER_TABLE_MENU.get(), id);
        this.level = inv.player.level();
        this.inputSlots = List.of(
                this.addSlot(new Slot(this.inputContainer, 0, 11, 24)),
                this.addSlot(new Slot(this.inputContainer, 1, 29, 24)),
                this.addSlot(new Slot(this.inputContainer, 2, 11, 42)),
                this.addSlot(new Slot(this.inputContainer, 3, 29, 42))
        );

        this.resultSlot = this.addSlot(new Slot(this.resultContainer, 1, 143, 33) {
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            public void onTake(Player player, ItemStack stack) {
                if(selectedRecipeIndex.get() < 0) {
                    CarpenterTableMenu.this.setupResultSlot();
                    return;
                }
                stack.onCraftedBy(player.level(), player, stack.getCount());
                CarpenterTableMenu.this.resultContainer.awardUsedRecipes(player, this.getRelevantItems());

                CarpenterTableMenu.this.recipes.get(selectedRecipeIndex.get()).value().getIngredients().forEach(ingredient -> {
                    for (Slot slot : CarpenterTableMenu.this.inputSlots) {
                        if(ingredient.test(slot.getItem())) {
                            ItemStack itemstack = slot.remove(1);
                            if (!itemstack.isEmpty()) {
                                CarpenterTableMenu.this.setupResultSlot();
                                break;
                            }
                        }
                    }
                });

                long l = level.getGameTime();
                if (CarpenterTableMenu.this.lastSoundTime != l) {
                    level.playSound((Player)null, player.blockPosition(), SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                    CarpenterTableMenu.this.lastSoundTime = l;
                }
                super.onTake(player, stack);
            }

            private List<ItemStack> getRelevantItems() {
                return CarpenterTableMenu.this.inputSlots.stream().map((Slot::getItem)).toList();
            }
        });

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inv, k, 8 + k * 18, 142));
        }

        this.addDataSlot(this.selectedRecipeIndex);
    }

    public int getSelectedRecipeIndex() {
        return this.selectedRecipeIndex.get();
    }

    public List<RecipeHolder<CarpenterRecipe>> getRecipes() {
        return this.recipes;
    }

    public int getNumRecipes() {
        return this.recipes.size();
    }

    public boolean hasInputItem() {
        return this.inputSlots.stream().anyMatch(Slot::hasItem) && !this.recipes.isEmpty();
    }

    public boolean stillValid(Player player) {
        return true; // TODO: Propper implementation ( stillValid(this.access, player, Blocks.STONECUTTER); )
    }

    public boolean clickMenuButton(Player player, int index) {
        if (this.isValidRecipeIndex(index)) {
            this.selectedRecipeIndex.set(index);
            this.setupResultSlot();
        }
        return true;
    }

    private boolean isValidRecipeIndex(int index) {
        return index >= 0 && index < this.recipes.size();
    }

    public void slotsChanged(SimpleContainer container) {
        boolean changed = false;
        for (int i = 0; i < this.inputSlots.size(); i++) {
            ItemStack itemstack = this.inputSlots.get(i).getItem();
            if (!itemstack.is(this.inputCache.get(i).getItem())) {
                this.inputCache.set(i, itemstack.copy());
                changed = true;

            }
        }
        if(changed) this.setupRecipeList(container);
    }

    private void setupRecipeList(SimpleContainer container) {
        this.recipes.clear();
        this.selectedRecipeIndex.set(-1);
        this.resultSlot.set(ItemStack.EMPTY);
        this.recipes = this.level.getRecipeManager().getRecipesFor(CarpenterRecipe.Type.INSTANCE, container, this.level);
    }

    void setupResultSlot() {
        if (!this.recipes.isEmpty() && this.isValidRecipeIndex(this.selectedRecipeIndex.get())) {
            RecipeHolder<CarpenterRecipe> recipe = this.recipes.get(this.selectedRecipeIndex.get());
            ItemStack itemstack = recipe.value().assemble(this.inputContainer, this.level.registryAccess());
            this.resultContainer.setRecipeUsed(recipe);
            this.resultSlot.set(itemstack.copy());
        } else {
            this.resultSlot.set(ItemStack.EMPTY);
        }
        this.broadcastChanges();
    }

    public MenuType<?> getType() {
        return Index.CARPENTER_TABLE_MENU.get();
    }

    public void registerUpdateListener(Runnable runnable) {
        this.slotUpdateListener = runnable;
    }

    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.resultContainer && super.canTakeItemForPickAll(stack, slot);
    }

    public ItemStack quickMoveStack(Player player, int slotFrom) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotFrom);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstackFrom = slot.getItem();
            Item item = itemstackFrom.getItem();
            itemstack = itemstackFrom.copy();
            if (slotFrom == RESULT_SLOT) {
                item.onCraftedBy(itemstackFrom, player.level(), player);
                if (!this.moveItemStackTo(itemstackFrom, RESULT_SLOT+1, USE_ROW_SLOT_END, false)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstackFrom, itemstack);
            } else if (slotFrom >= INPUT_SLOT && slotFrom < RESULT_SLOT) {
                if (!this.moveItemStackTo(itemstackFrom, INV_SLOT_START, USE_ROW_SLOT_END, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (slotFrom >= INV_SLOT_START && slotFrom < INV_SLOT_END) {
                if (this.moveItemStackTo(itemstackFrom, INPUT_SLOT, RESULT_SLOT, false) || this.moveItemStackTo(itemstackFrom, INV_SLOT_END, USE_ROW_SLOT_END, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (slotFrom >= INV_SLOT_END && slotFrom < USE_ROW_SLOT_END) {
                if (this.moveItemStackTo(itemstackFrom, INPUT_SLOT, RESULT_SLOT, false) || this.moveItemStackTo(itemstackFrom, INV_SLOT_START, INV_SLOT_END, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (itemstackFrom.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            }

            slot.setChanged();
            if (itemstackFrom.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }


            slot.onTake(player, itemstackFrom);
            this.broadcastChanges();
        }

        return itemstack;
    }

    public void removed(Player player) {
        super.removed(player);
        this.resultContainer.removeItemNoUpdate(1);
        this.clearContainer(player, this.inputContainer);
    }
}