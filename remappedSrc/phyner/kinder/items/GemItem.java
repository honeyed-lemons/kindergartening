package phyner.kinder.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import phyner.kinder.KinderMod;
import phyner.kinder.entities.AbstractGemEntity;
import phyner.kinder.util.GemColors;
import phyner.kinder.util.PaletteType;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class GemItem extends Item {
    private final EntityType<?> type;
    private final GemColors color;

    public GemItem (EntityType<? extends AbstractGemEntity> type, GemColors color, Settings settings){
        super (settings);
        this.type = type;
        this.color = color;
    }

    @Override public ActionResult useOnBlock (ItemUsageContext context){
        if (context.getSide ().equals (Direction.UP)) {
            this.spawnGem (context.getStack (), context.getWorld (), context.getBlockPos (), context);
        }
        return ActionResult.CONSUME;
    }

    @Override public boolean canBeNested (){
        return false;
    }

    public void spawnGem (ItemStack itemStack, World world, BlockPos pos, ItemUsageContext context){
        NbtCompound nbt = itemStack.getSubNbt ("gem");
        if (!world.isClient) {
            if (nbt != null) {
                Optional<Entity> entity = EntityType.getEntityFromNbt (nbt, world);
                if (entity.isPresent ()) {
                    AbstractGemEntity gem = (AbstractGemEntity) entity.get ();
                    gem.setPos (pos.getX () + 0.5, pos.getY () + 1.0, pos.getZ () + 0.5F);
                    gem.fallDistance = 0;
                    gem.speed = 0;
                    gem.setOnFire (false);
                    gem.setFireTicks (0);
                    gem.setHealth (gem.getMaxHealth ());
                    gem.clearStatusEffects ();
                    gem.setVelocity (0, 0, 0);
                    KinderMod.LOGGER.info ("Spawning Gem, Name is " + gem.getName ().getString ());
                    world.spawnEntity (gem);
                    gem.lookAtEntity (context.getPlayer (), 90, 90);
                    itemStack.setCount (0);
                }
            } else {
                AbstractGemEntity gem = (AbstractGemEntity) Objects.requireNonNull (type.spawn (Objects.requireNonNull (world.getServer ()).getWorld (world.getRegistryKey ()), pos.up (), SpawnReason.MOB_SUMMONED));
                gem.setGemVariantOnInitialSpawn = false;
                gem.setGemColorVariant (color.getId ());
                gem.generateColors ();
                gem.setPerfection (3);
                KinderMod.LOGGER.info ("Spawning Gem, Name is " + type.getName ().getString ());
                if (!Objects.requireNonNull (context.getPlayer ()).isCreative ()) {
                    itemStack.setCount (0);
                }
            }
        }
    }

    @Override
    public void appendTooltip (ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context){
        NbtCompound nbt = stack.getSubNbt ("gem");
        if (nbt != null) {
            Optional<Entity> entity = EntityType.getEntityFromNbt (nbt, world);
            entity.ifPresent (value -> tooltip.add (Text.of (String.valueOf (value.getName ().getString ()))));
        }
    }
}
