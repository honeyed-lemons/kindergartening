package phyner.kinder.entities.gems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import phyner.kinder.entities.AbstractVaryingGemEntity;
import phyner.kinder.entities.GemDefaultAnimations;
import phyner.kinder.init.KinderItems;
import phyner.kinder.items.PearlCustomizerItem;
import phyner.kinder.util.GemPlacements;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animation.AnimatableManager;

public class PearlEntity extends AbstractVaryingGemEntity {
    public PearlEntity (EntityType<? extends TameableEntity> entityType, World world){
        super (entityType, world);
    }

    public static DefaultAttributeContainer.@NotNull Builder createGemAttributes (){
        return createDefaultGemAttributes ().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.625);
    }
    @Override public int maxHealth (){
        return 20;
    }

    @Override public int attackDamage (){
        return 1;
    }
    @Override public void interactGem (PlayerEntity player){
        ItemStack stack = player.getStackInHand (Hand.MAIN_HAND);
        if (stack == ItemStack.EMPTY)
        {
            player.openHandledScreen (new GemScreenHandlerFactory ());
        }
    }
    @Override public boolean isSolider (){
        return false;
    }

    @Override public int hairVariantCount (){
        return 5;
    }

    @Override public void registerControllers (AnimatableManager.ControllerRegistrar controllerRegistrar){
        controllerRegistrar.add (GemDefaultAnimations.genericGemWalkLegsController (this));
        controllerRegistrar.add (GemDefaultAnimations.genericGemPearlArmsController (this));
        controllerRegistrar.add (DefaultAnimations.genericAttackAnimation (this, GemDefaultAnimations.ARMS_USE));
    }

    @Override public int outfitVariantCount (){
        return 4;
    }

    public int insigniaVariantCount (){
        return 4;
    }

    @Override public int generateInsigniaVariant (){
        if (insigniaVariantCount () != 0) {
            return this.random.nextBetween (1, outfitVariantCount ());
        } else return 0;
    }

    @Override public boolean hasOutfitPlacementVariant (){
        return false;
    }

    @Override public int defaultOutfitColor (){
        return 0;
    }

    @Override public int defaultInsigniaColor (){
        return 0;
    }

    @Override public GemPlacements[] getPlacements (){
        return new GemPlacements[]{
                GemPlacements.CHEST,
                GemPlacements.NOSE,
                GemPlacements.BACK,
                GemPlacements.BELLY,
                GemPlacements.FOREHEAD,
                GemPlacements.RIGHT_EYE,
                GemPlacements.LEFT_EYE,
                GemPlacements.RIGHT_SHOULDER,
                GemPlacements.LEFT_SHOULDER,
                GemPlacements.LEFT_CHEEK,
                GemPlacements.RIGHT_CHEEK,
                GemPlacements.RIGHT_HAND,
                GemPlacements.LEFT_HAND,
                GemPlacements.RIGHT_KNEE,
                GemPlacements.LEFT_KNEE,
                GemPlacements.BACK_OF_HEAD,
                GemPlacements.TOP_OF_HEAD};
    }

    @Override public ItemStack gemItem (){
        return switch (getGemColorVariant ()) {
            case 1 -> KinderItems.PEARL_GEM_1.getDefaultStack ();
            case 2 -> KinderItems.PEARL_GEM_2.getDefaultStack ();
            case 3 -> KinderItems.PEARL_GEM_3.getDefaultStack ();
            case 4 -> KinderItems.PEARL_GEM_4.getDefaultStack ();
            case 5 -> KinderItems.PEARL_GEM_5.getDefaultStack ();
            case 6 -> KinderItems.PEARL_GEM_6.getDefaultStack ();
            case 7 -> KinderItems.PEARL_GEM_7.getDefaultStack ();
            case 8 -> KinderItems.PEARL_GEM_8.getDefaultStack ();
            case 9 -> KinderItems.PEARL_GEM_9.getDefaultStack ();
            case 10 -> KinderItems.PEARL_GEM_10.getDefaultStack ();
            case 11 -> KinderItems.PEARL_GEM_11.getDefaultStack ();
            case 12 -> KinderItems.PEARL_GEM_12.getDefaultStack ();
            case 13 -> KinderItems.PEARL_GEM_13.getDefaultStack ();
            case 14 -> KinderItems.PEARL_GEM_14.getDefaultStack ();
            case 15 -> KinderItems.PEARL_GEM_15.getDefaultStack ();
            default -> KinderItems.PEARL_GEM_0.getDefaultStack ();
        };
    }

    @Override public @NotNull SoundEvent gemInstrument (){
        return SoundEvents.BLOCK_NOTE_BLOCK_HARP.value ();
    }

    @Override public boolean UsesUniqueNames (){
        return true;
    }

    @Override public void onInventoryChanged (Inventory sender){
    }
}
