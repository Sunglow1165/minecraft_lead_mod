package com.github.sunglow.lead.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 燃烧僵尸 实体
 * 燃烧僵尸的攻击会使目标燃烧
 * 当燃烧僵尸生命值只剩10点生命值或更低时，燃烧僵尸会进入“狂暴”状态，移动速度和攻击力会得到一定程度的提升
 * 燃烧僵尸碰到水时会变成黑曜石状态，燃烧僵尸在雨中时会持续受到1点（）伤害
 * 它还有一种更强的变种疯狂燃烧僵尸
 */
public class BurnedZombie extends Zombie {
    //属性修改器new AttributeModifier(uuid对象,属性修饰符名称,修改量,加/乘修改量);uuid为这个属性修改器的id,在服务器中标识一个具体的属性修改器
    //用于在"狂暴"状态下,修改属性
    private static final AttributeModifier CRAZY_MODIFIER = new AttributeModifier(UUID.fromString("a0ffa7a6-1210-466a-a9a1-31909417a99e"), "Crazy attribute boost", 0.5F, AttributeModifier.Operation.MULTIPLY_BASE);
    //用于在"黑曜石"状态下,修改属性
    private static final AttributeModifier OBSIDIAN_MODIFIER = new AttributeModifier(UUID.fromString("cf2ce4af-4807-4896-aaad-1c077a87e9bf"), "Obsidian attribute boost", 1.0F, AttributeModifier.Operation.MULTIPLY_BASE);
    //具体表现为在服务器中,一只僵尸A用属性修改器改变了,需要同步一下另一个客户端,将同一只僵尸也改变,为这只僵尸创建一个id标识一下,并序列化和反序列化进行传输,同步
    //用于同步客户端与服务器上面的两个状态,同步属性值
    private static final EntityDataAccessor<Boolean> DATA_IS_OBSIDIAN = SynchedEntityData.defineId(BurnedZombie.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_CRAZY = SynchedEntityData.defineId(BurnedZombie.class, EntityDataSerializers.BOOLEAN);
    //配置掉落物,需要用到mixin
//    public static final ResourceLocation OBSIDIAN_LOOT = LootBuilder.of("burned").build("obsidian");

    public BurnedZombie(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
        this.xpReward = 6;
        this.setPathfindingMalus(BlockPathTypes.LEAVES, 8.0F);
    }

    //添加行为目标,继承了zombie,目标就是玩家和村民等
//    @Override
//    protected void addBehaviourGoals() {
//        super.addBehaviourGoals();
//    }

    //用于生成更强的变种
    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes().add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D).add(Attributes.MAX_HEALTH, 22.0D).add(Attributes.MOVEMENT_SPEED, 0.2D).add(Attributes.ATTACK_DAMAGE, 4.0D).add(Attributes.ARMOR, 4.0D);
    }

    //用于定义同步数据，也就是在客户端和服务端之间同步的数据。
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        //代表实体是否为黑曜石（obsidian）僵尸，类型为布尔值
        this.getEntityData().define(DATA_IS_OBSIDIAN, false);
        //代表实体是否为疯狂（crazy）僵尸，类型为布尔值。
        this.getEntityData().define(DATA_IS_CRAZY, false);
    }

    //关于是否可以转化为溺尸,false不能
    @Override
    protected boolean convertsInWater() {
        return false;
    }

    //关于这三个方法的①
    //实体的高级行为逻辑
    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        //满足条件后转换为“狂暴”状态
        if (this.getHealth() <= 10.0D && !this.isCrazy()) this.setCrazy(true);
    }

    //实体的基础行为逻辑
    @Override
    public void aiStep() {
        super.aiStep();
        //播放对应的粒子效果
        if (this.random.nextInt(10) == 0) {
            this.level.addParticle(this.isObsidian() ? ParticleTypes.FALLING_OBSIDIAN_TEAR : ParticleTypes.FALLING_LAVA, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
        }
        if (this.isCrazy()) {
            if (this.random.nextInt(10) == 0) {
                this.level.addParticle(this.isObsidian() ? (this.random.nextBoolean() ? ParticleTypes.SMOKE : ParticleTypes.LARGE_SMOKE) : ParticleTypes.LAVA, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    //实体生命周期中需要进行的逻辑
    @Override
    public void tick() {
        //满足条件后转换为“黑曜石”状态
        if (!this.level.isClientSide && this.isAlive() && !this.isNoAi()) {
            if (this.isInWaterOrBubble() && !this.isObsidian()) this.setObsidian(true);
            if (this.isInLava() && this.isObsidian()) this.setObsidian(false);
        }

        super.tick();
    }

    //处理造成伤害的逻辑
    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        boolean hurt = super.doHurtTarget(entity);
        //判断是否满足，此生物的手上没有任何物品，目标是生物实体，不是黑曜石状态
        if (hurt && this.getMainHandItem().isEmpty() && entity instanceof LivingEntity living && !this.isObsidian()) {
            //使目标进入着火状态,持续时间为3秒乘以当前难度系数的整数部分
            float modifier = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            living.setSecondsOnFire(3 * (int) modifier);
        }

        return hurt;
    }

    //是否受到伤害(伤害类型)
    @Override
    public boolean hurt(DamageSource source, float amount) {
        return (!(source.getDirectEntity() instanceof AbstractArrow) || !this.isObsidian()) && super.hurt(source, amount);
    }

    //是否对水敏感
    @Override
    public boolean isSensitiveToWater() {
        return !this.isObsidian() && !this.isInWaterOrBubble();
    }

    //处理生物移动的逻辑
    @Override
    public void travel(@NotNull Vec3 vec3) {
        this.setSpeed(this.getMoveSpeed());
        super.travel(vec3);
    }

    //通过不同的状态来返回不同的移动速度
    public float getMoveSpeed() {
        return (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) * (this.isObsidian() ? 0.5F : this.isCrazy() ? 1.5F : 1.0F);
    }

    //计算受到的摔落伤害
    @Override
    protected int calculateFallDamage(float distance, float amount) {
        return super.calculateFallDamage(distance, amount) - (this.isObsidian() ? 10 : 0);
    }

    //是否生成在什么方块上
    public static boolean checkBurnedSpawnRules(EntityType<BurnedZombie> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return !level.getBlockState(pos.below()).is(Blocks.NETHER_WART_BLOCK);
    }

    //用于将实体的状态保存到NBT中，以便在游戏中存储和传输实体数据
    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("IsObsidian", this.isObsidian());
        tag.putBoolean("IsCrazy", this.isCrazy());
    }

    //用于读取实体额外的保存数据，以便将实体保存到磁盘上或从磁盘中加载实体时使用，比如重启游戏
    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setObsidian(tag.getBoolean("IsObsidian"));
        this.setCrazy(tag.getBoolean("IsCrazy"));
    }

    //环境音效
    @Override
    protected SoundEvent getAmbientSound() {
        return super.getAmbientSound();
    }

    //受伤音效
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return super.getHurtSound(damageSource);
    }

    //死亡音效
    @Override
    protected SoundEvent getDeathSound() {
        return super.getDeathSound();
    }

    //判断是否为“狂暴”状态
    public boolean isCrazy() {
        return this.getEntityData().get(DATA_IS_CRAZY);
    }

    //设置为“狂暴”状态
    public void setCrazy(boolean crazy) {
        this.getEntityData().set(DATA_IS_CRAZY, crazy);
        if (!this.level.isClientSide) {
            List<AttributeInstance> instances = new ArrayList<>();
            instances.add(this.getAttribute(Attributes.ATTACK_DAMAGE));
            instances.add(this.getAttribute(Attributes.KNOCKBACK_RESISTANCE));
            //②
            for (AttributeInstance instance : instances) {
                instance.removeModifier(CRAZY_MODIFIER);
                if (crazy) instance.addTransientModifier(CRAZY_MODIFIER);
            }
        }
    }

    //是否为“黑曜石”状态
    public boolean isObsidian() {
        return this.getEntityData().get(DATA_IS_OBSIDIAN);
    }

    //设置为“黑曜石”状态
    public void setObsidian(boolean obsidian) {
        this.getEntityData().set(DATA_IS_OBSIDIAN, obsidian);
        if (!this.level.isClientSide) {
            List<AttributeInstance> instances = new ArrayList<>();
            instances.add(this.getAttribute(Attributes.ATTACK_DAMAGE));
            instances.add(this.getAttribute(Attributes.KNOCKBACK_RESISTANCE));
            //①
            for (AttributeInstance instance : instances) {
                instance.removeModifier(OBSIDIAN_MODIFIER);
                if (obsidian) instance.addTransientModifier(OBSIDIAN_MODIFIER);
            }
        }
        //转换为“黑曜石”状态的音效
        if (obsidian) this.level.levelEvent(1501, this.blockPosition(), 0);
    }

    //如果这个僵尸实例的 isObsidian() 方法返回 true，则返回一个自定义的掉落表（OBSIDIAN_LOOT），否则返回原版的默认掉落表。
//    @Override
//    protected ResourceLocation getDefaultLootTable() {
//        return this.isObsidian() ? OBSIDIAN_LOOT : super.getDefaultLootTable();
//    }

    /**
     * ①
     * 这三大方法tick,aiStep,customServerAiStep都是在实体每个刻都会调用的基础方法
     * tick:适合写实体生命周期中需要进行的逻辑;
     * aiStep:适合写实体的基础行为逻辑，例如实体的移动、攻击等;
     * customServerAiStep:适合写实体的高级行为逻辑，例如实体的决策、策略等。这个方法需要手动开启，因为这个方法的实现可以根据具体实体的需求进行编写，而不是一个通用的实现。
     * 1.如果你在编写自定义实体类，建议继承 MobEntity 或其子类，因为它们已经实现了一些常用的逻辑。
     * 2.在编写实体的逻辑时，应该注意将实体的状态保存在实体数据中，而不是在实体类中定义变量。这是因为实体可能会被序列化、传输或加载到其他维度中，如果将状态保存在实体类中定义变量，这些状态可能会在传输或加载时丢失。
     * 3.在实现 AI 行为时，应该尽可能地使用现有的 API，避免直接操作底层方法或变量。这是因为 Minecraft 的实现可能会不断更新，而直接操作底层方法或变量可能会导致代码不兼容，需要进行大量的修改。
     *
     * ②
     * 在这里移除是为了确保已经存在的修饰器不会和新的修饰器冲突。
     * 通过移除所有现有的修饰器，我们可以确保在设置新修饰器之前，实体不会同时受到多个修饰器的效果。
     * 然后，我们添加一个暂时性的修饰器，它只在设置狂暴状态时存在。
     * 这意味着修饰器将只在一段时间内生效，之后它们将被自动移除。
     * 这种方法可以确保实体不会永久地保持狂暴状态。
     */

}
