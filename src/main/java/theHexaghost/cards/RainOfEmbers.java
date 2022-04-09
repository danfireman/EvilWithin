package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theHexaghost.actions.EmbersAction;
import downfall.actions.PerformXAction;

public class RainOfEmbers extends AbstractHexaCard {

    public final static String ID = makeID("RainOfEmbers");

    public RainOfEmbers() {
        super(ID, -1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 6;
        baseBurn = burn = 6;
        baseMagicNumber = magicNumber = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (energyOnUse < EnergyPanel.totalCount) {
            energyOnUse = EnergyPanel.totalCount;
        }

        EmbersAction r = new EmbersAction(0, p, m, damage, damageTypeForTurn, burn, magicNumber);
        atb(new PerformXAction(r, p, energyOnUse, freeToPlayOnce));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
          //  upgradeDamage(1);
          //  upgradeBurn(1);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}