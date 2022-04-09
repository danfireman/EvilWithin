package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import downfall.actions.AbstractXAction;
import theHexaghost.powers.BurnPower;

public class EmbersAction extends AbstractXAction {

    private final int bonusAmt;

    private final int damage;
    private final AbstractPlayer p;
    private final AbstractMonster m;
    private final DamageInfo.DamageType damageTypeForTurn;
    private final int burn;
    private final int weak;

    public EmbersAction(int bonusAmt, AbstractPlayer p, AbstractMonster m, int damage, DamageInfo.DamageType damageTypeForTurn, int burn, int weak) {
        this.bonusAmt = bonusAmt;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.damage = damage;
        this.p = p;
        this.m = m;
        this.damageTypeForTurn = damageTypeForTurn;
        this.burn = burn;
        this.weak = weak;
    }

    @Override
    public void initialize(int totalAmount) {
        super.initialize(totalAmount);
        this.amount += bonusAmt;
    }

    public void update() {
        for (int i = 0; i < amount; i++) {
            addToTop(new ApplyPowerAction(m, p, new BurnPower(m, burn), burn));
            addToTop(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AttackEffect.FIRE));

            if (weak > 0) {
                addToTop(new ApplyPowerAction(m, p, new WeakPower(m, weak, false), weak));
            }
        }
        this.isDone = true;
    }
}