package xyz.pixelatedw.mineminenomi.abilities.kira;

import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import xyz.pixelatedw.wypi.APIConfig.AbilityCategory;
import xyz.pixelatedw.wypi.abilities.ContinuousAbility;

public class DiamondBodyAbility extends ContinuousAbility
{
	public static final DiamondBodyAbility INSTANCE = new DiamondBodyAbility();
	
	private static final AttributeModifier AMODIFIER = new AttributeModifier(UUID.fromString("0847f786-0a5a-4e83-9ea6-f924c259a798"), "Diamond Body Modifier", 20, AttributeModifier.Operation.ADDITION).setSaved(false);
	private static final AttributeModifier ATMODIFIER = new AttributeModifier(UUID.fromString("0847f786-0a5a-4e83-9ea6-f924c259a798"), "Diamond Body Modifier", 8, AttributeModifier.Operation.ADDITION).setSaved(false);
	private static final AttributeModifier STMODIFIER = new AttributeModifier(UUID.fromString("0847f786-0a5a-4e83-9ea6-f924c259a798"), "Diamond Body Modifier", 6, AttributeModifier.Operation.ADDITION).setSaved(false);

	public DiamondBodyAbility()
	{
		super("Diamond Body", AbilityCategory.DEVIL_FRUIT);
		this.setThreshold(60);
		this.setDescription("Allows the user's body to become diamond, gaining high strength and defence .");

		this.onStartContinuityEvent = this::onStartContinuityEvent;
		this.duringContinuityEvent = this::duringContinuity;
		this.onEndContinuityEvent = this::onEndContinuityEvent;
	}

	private boolean onStartContinuityEvent(PlayerEntity player) {
		return true;
		
		
	}

	private void duringContinuity(PlayerEntity player, int passiveTimer)
	{
		IAttributeInstance attri = player.getAttribute(SharedMonsterAttributes.ARMOR);
		if(!attri.hasModifier(AMODIFIER))
			attri.applyModifier(AMODIFIER);
		IAttributeInstance attrib = player.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS);
		if(!attrib.hasModifier(ATMODIFIER))
			attrib.applyModifier(ATMODIFIER);
		IAttributeInstance attribu = player.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		if(!attribu.hasModifier(STMODIFIER))
			attribu.applyModifier(STMODIFIER);
	}

	private boolean onEndContinuityEvent(PlayerEntity player)
	{
		IAttributeInstance attri = player.getAttribute(SharedMonsterAttributes.ARMOR);
		if(attri.hasModifier(AMODIFIER))
			attri.removeModifier(AMODIFIER);
		IAttributeInstance attrib = player.getAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS);
		if(attrib.hasModifier(ATMODIFIER))
			attrib.removeModifier(ATMODIFIER);
		IAttributeInstance attribu = player.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		if(attribu.hasModifier(STMODIFIER))
			attribu.removeModifier(STMODIFIER);
		
		int cooldown = (int) Math.round(this.continueTime / 35.0);
		this.setMaxCooldown(cooldown);
		return true;
	}
}