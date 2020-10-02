package xyz.pixelatedw.mineminenomi.abilities.kira;

import java.util.List;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.SAnimateHandPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import xyz.pixelatedw.mineminenomi.abilities.haki.BusoshokuHakiImbuingAbility;
import xyz.pixelatedw.mineminenomi.abilities.toriphoenix.PhoenixFlyPointAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.CrewHelper;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.entities.zoan.PhoenixFlyZoanInfo;
import xyz.pixelatedw.mineminenomi.init.ModI18n;
import xyz.pixelatedw.wypi.APIConfig.AbilityCategory;
import xyz.pixelatedw.wypi.WyHelper;
import xyz.pixelatedw.wypi.abilities.Ability;
import xyz.pixelatedw.wypi.data.ability.AbilityDataCapability;
import xyz.pixelatedw.wypi.data.ability.IAbilityData;

public class BrilliantPunkAbility extends Ability
{
	public static final BrilliantPunkAbility INSTANCE = new BrilliantPunkAbility();

	public BrilliantPunkAbility()
	{
		super("Brilliant Punk", AbilityCategory.DEVIL_FRUIT);
		this.setMaxCooldown(10);
		this.setDescription("The user rams into the target with their diamond body");

		this.onUseEvent = this::onUseEvent;
		this.duringCooldownEvent = this::duringCooldown;
	}

	private boolean onUseEvent(PlayerEntity player)
	{
		IAbilityData abilityProps = AbilityDataCapability.get(player);
		DiamondBodyAbility ability = abilityProps.getEquippedAbility(DiamondBodyAbility.INSTANCE);
		boolean DiamondActive = ability != null && ability.isContinuous();
		if (!DiamondActive)
		{
			WyHelper.sendMsgToPlayer(player, new TranslationTextComponent(ModI18n.ABILITY_MESSAGE_NOT_ZOAN_FORM_SINGLE, this.getName(), DiamondBodyAbility.INSTANCE.getName()).getFormattedText());
			return false;
		}
		else
		{
			Vec3d speed = WyHelper.propulsion(player, 3, 3);
			player.setMotion(speed.x, 0.2, speed.z);
			player.velocityChanged = true;
		
			return true;
		}
	}
	
	private void duringCooldown(PlayerEntity player, int cooldownTimer)
	{
		if (this.canDealDamage())
		{
			List<LivingEntity> list = WyHelper.getEntitiesNear(player.getPosition(), player.world, 1.6, CrewHelper.NOT_IN_CREW_PREDICATE, LivingEntity.class);
			list.remove(player);

			list.stream().forEach(entity ->
			{
				entity.attackEntityFrom(DamageSource.causePlayerDamage(player), 15);
			});
		}
	}
	
	// Cooldown = 8*20 = 160 
	// Damage Frame = 6*20 = 120
	// 160-120=40 ticks or 2 seconds of damage frames
	public boolean canDealDamage()
	{
		if(this.cooldown > 6 * 20) 
			return true;
		return false;
	}
}