package l2s.gameserver.skills.skillclasses;

import java.util.List;

import l2s.gameserver.Config;
import l2s.gameserver.model.Creature;
import l2s.gameserver.model.Player;
import l2s.gameserver.model.Skill;
import l2s.gameserver.templates.StatsSet;


public class DeathPenalty extends Skill
{
	public DeathPenalty(StatsSet set)
	{
		super(set);
	}

	@Override
	public boolean checkCondition(final Creature activeChar, final Creature target, boolean forceUse, boolean dontMove, boolean first)
	{
		// Chaotic characters can't use scrolls of recovery
		if(activeChar.getKarma() > 0 && !Config.ALT_DEATH_PENALTY_C5_CHAOTIC_RECOVERY)
		{
			activeChar.sendActionFailed();
			return false;
		}

		return super.checkCondition(activeChar, target, forceUse, dontMove, first);
	}

	@Override
	public void useSkill(Creature activeChar, List<Creature> targets)
	{
		for(Creature target : targets)
			if(target != null)
			{
				if(!target.isPlayer())
					continue;
				((Player) target).getDeathPenalty().reduceLevel();
			}
	}
}