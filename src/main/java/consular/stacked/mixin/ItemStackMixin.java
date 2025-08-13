package consular.stacked.mixin;

import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.component.ComponentHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import consular.stacked.ConfigManager;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ComponentHolder, FabricItemStack {

	@Inject(at = @At("TAIL"), method = "getMaxCount")
	private int init(CallbackInfoReturnable<?> info) {
		ItemStack stack = (ItemStack)(Object)this;
		if(stack.getMaxDamage() == 0 && !stack.hasEnchantments() && !stack.isOf(Items.ENCHANTED_BOOK) && stack.isIn(ItemTags.BUNDLES)){
			return ConfigManager.getMaxStackSize();
		}else{
			return 1;
		}
	}
}