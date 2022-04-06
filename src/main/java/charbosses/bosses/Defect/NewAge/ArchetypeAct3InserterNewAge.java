package charbosses.bosses.Defect.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.bosses.Defect.CharBossDefect;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.blue.*;
import charbosses.cards.colorless.EnBlind;
import charbosses.cards.curses.EnClumsy;
import charbosses.orbs.AbstractEnemyOrb;
import charbosses.powers.bossmechanicpowers.DefectCuriosityLightningPower;
import charbosses.powers.bossmechanicpowers.DefectCuriosityPower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import java.util.ArrayList;

public class ArchetypeAct3InserterNewAge extends ArchetypeBaseDefect {

    private CharBossDefect cB;
    private boolean A19 = AbstractDungeon.ascensionLevel >= 19;

    public ArchetypeAct3InserterNewAge() {
        super("DF_ARCHETYPE_ORBS", "Inserter");

        maxHPModifier += 350;
        actNum = 3;
        bossMechanicName = DefectCuriosityLightningPower.NAME;
        bossMechanicDesc = DefectCuriosityLightningPower.DESCRIPTIONS[0] + 1 + DefectCuriosityLightningPower.DESCRIPTIONS[1];
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DefectCuriosityLightningPower(p)));

    }

    public void initialize() {

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_DataDisk());
        addRelic(new CBR_IceCream());
        addRelic(new CBR_StrikeDummy());
        addRelic(new CBR_Inserter());

    }

    public static void increasePretendFocus(int amount) {
        for (AbstractOrb o : AbstractCharBoss.boss.orbs) {
            if (o instanceof AbstractEnemyOrb) {
                ((AbstractEnemyOrb) o).pretendFocus += amount;
                AbstractEnemyOrb.masterPretendFocus += amount;
                o.applyFocus();
                //((AbstractEnemyOrb) o).applyLockOn();
            }
        }
    }

    public static void resetPretendFocus() {
        for (AbstractOrb o : AbstractCharBoss.boss.orbs) {
            if (o instanceof AbstractEnemyOrb) {
                ((AbstractEnemyOrb) o).pretendFocus = 0;
                AbstractEnemyOrb.masterPretendFocus = 0;
                o.applyFocus();
                //((AbstractEnemyOrb) o).applyLockOn();
            }
        }
    }

    int loops = 0;

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        if (cB == null){
            cB = (CharBossDefect) AbstractCharBoss.boss;
        }
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;

        if (!looped) {
            switch (turn) {
                case 0:
                    // No Orbs
                    addToList(cardsList, new EnFusion(), true);
                    addToList(cardsList, new EnBallLightning());
                    addToList(cardsList, new EnClumsy());
                    // Plasma Lightning
                    turn++;
                    break;
                case 1:
                    //Turn 2 - 3E
                    // Plasma Lightning
                    addToList(cardsList, new EnColdSnap());
                    // Plasma Lightning Frost
                    addToList(cardsList, new EnBarrage(3), extraUpgrades);
                    addToList(cardsList, new EnDefendBlue());
                    turn++;
                    break;
                case 2:
                    //Turn 3 3E
                    // Plasma Lightning Frost
                    addToList(cardsList, new EnDoubleEnergy(3), true);
                    // Lightning Frost
                    addToList(cardsList, new EnRebound());
                    addToList(cardsList, new EnMeteorStrike());
                    // Frost Plasma Plasma Plasma
                    turn++;
                    break;
                case 3:
                    //Turn 4 5E
                    EnDualcast dualcast = new EnDualcast();
                    dualcast.energyGeneratedIfPlayed = 4; // Safe to assume there will always be enough E
                    addToList(cardsList, dualcast);
                    cB.orbsAsEn().get(0).evokeOverride = true;
                    cB.orbsAsEn().get(0).evokeMult = 2;
                    // Frost Plasma Plasma Plasma 8E
                    addToList(cardsList, new EnMeteorStrike());
                    addToList(cardsList, new EnZap());
                    // Plasma Plasma Plasma Plasma Lightning 6E
                    turn++;
                    break;
                case 4:
                    //Turn 5 12E
                    addToList(cardsList, new EnDefragment(), extraUpgrades);
                    increasePretendFocus(extraUpgrades?2:1);
                    addToList(cardsList, new EnThunderStrike(EnThunderStrike.getLightningCount()), false); // TODO: Make auto-update
                    // Plasma Plasma Plasma Plasma Lightning 8E
                    addToList(cardsList, new EnReinforcedBody(this.cB.energyPanel.getCurrentEnergy() - 4)); // TODO: Dynamic calculation
                    // Plasma Plasma Plasma Plasma Lightning 0E
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    // first round: Plasma Plasma Plasma Plasma Lightning 10E
                    addToList(cardsList, new EnRebound());
                    addToList(cardsList, new EnBarrage(this.cB.filledOrbCount()), extraUpgrades);
                    addToList(cardsList, new EnDefendBlue());
                    turn++;
                    break;
                case 1:
                    // first round: Plasma Plasma Plasma Plasma Lightning 7E
                    addToList(cardsList, new EnColdSnap());
                    // first round: Plasma Plasma Plasma Plasma Plasma Frost 6E
                    addToList(cardsList, new EnBarrage(Math.min(this.cB.maxOrbs, this.cB.filledOrbCount() + 1)), extraUpgrades);
                    addToList(cardsList, new EnReinforcedBody(this.cB.energyPanel.getCurrentEnergy() - 2)); // TODO: Dynamic calculation
                    // first round: Plasma Plasma Plasma Plasma Frost Plasma 8E
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnDualcast());
                    cB.orbsAsEn().get(0).evokeOverride = true;
                    cB.orbsAsEn().get(0).evokeMult = 2;
                    addToList(cardsList, new EnFusion(), true);
                    addToList(cardsList, new EnZap());
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnMeteorStrike());
                    addToList(cardsList, new EnThunderStrike(EnThunderStrike.getLightningCount()), false); // TODO: Make auto-update
                    addToList(cardsList, new EnBallLightning());
                    turn = 0;
                    looped = true;
                    loops++;
                    break;
            }
        }

        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_Calipers());
    }
}