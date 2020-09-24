package champ.stances;

import champ.ChampChar;
import champ.actions.FatigueHpLossAction;
import champ.cards.AbstractChampCard;
import champ.powers.ResolvePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BerserkerStance extends AbstractChampStance {

    public static final String STANCE_ID = "champ:BerserkerStance";
    private static long sfxId = -1L;

    public BerserkerStance() {
        this.ID = STANCE_ID;// 21
        this.name = ChampChar.characterStrings.TEXT[3];
        this.updateDescription();// 23
    }// 24

    @Override
    public void updateDescription() {
        this.description = ChampChar.characterStrings.TEXT[8] + ": " + ChampChar.characterStrings.TEXT[10] + " NL " + ChampChar.characterStrings.TEXT[9] + ": " + ChampChar.characterStrings.TEXT[11];
    }

    @Override
    public void technique() {
        int x = Math.min(4, AbstractDungeon.player.currentHealth - 1);
        AbstractDungeon.actionManager.addToBottom(new FatigueHpLossAction(AbstractDungeon.player, AbstractDungeon.player, x));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ResolvePower(x), x));
        //AbstractDungeon.actionManager.addToBottom(new GainEnergAction(1));
    }

    @Override
    public void finisher() {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (AbstractDungeon.player.hasPower(ResolvePower.POWER_ID)) {
                    int x = AbstractDungeon.player.getPower(ResolvePower.POWER_ID).amount;
                    if (x > 0) {
                        addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, x));
                        addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, AbstractDungeon.player.getPower(ResolvePower.POWER_ID)));
                    }
                }
            }
        });
    }
}
