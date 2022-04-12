package charbosses.bosses.Watcher.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.curses.EnDoubt;
import charbosses.cards.curses.EnWrithe;
import charbosses.cards.purple.*;
import charbosses.powers.bossmechanicpowers.WatcherCripplePower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct3MasterRealityNewAge extends ArchetypeBaseDefect {

    private AbstractBossCard smite1 = null;

    public ArchetypeAct3MasterRealityNewAge() {
        super("WA_ARCHETYPE_DEVA", "MasterReality");

        maxHPModifier += 300;
        actNum = 3;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
//        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,  new WatcherCripplePower(p, 200), 150));

    }

    public void initialize() {

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_OrangePellets());
        addRelic(new CBR_Orichalcum());
        addRelic(new CBR_HandDrill());
        addRelic(new CBR_Sozu());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (!looped) {
            switch (turn) {
                case 0:
                    //Turn 2 - 5 damage
                    addToList(cardsList, new EnBattleHymn());
                    addToList(cardsList, new EnConsecrate());
                    addToList(cardsList, new EnVigilance(), true);
                    turn++;
                    break;
                case 1:
                    //Turn 2 - 22 damage
                    addToList(cardsList, new EnEstablishment());
                    addToList(cardsList, new EnCarveReality(false), extraUpgrades);
                    addToList(cardsList, new EnProstrate());
                    turn++;
                    break;
                case 2:
                    //Turn 3 - 41
                    EnDevotion d = new EnDevotion();
                    d.newPrio = 1;
                    addToList(cardsList, d);
                    EnEmptyFist e = new EnEmptyFist();
                    e.newPrio = 2;
                    e.energyGeneratedIfPlayed = 2;
                    addToList(cardsList, e, extraUpgrades);
                    EnRagnarok r = new EnRagnarok();
                    r.newPrio = 3;
                    addToList(cardsList, r);
                    turn++;
                    break;
                case 3:
                    // Turn 4 - 28
                    addToList(cardsList, new EnStrikePurple(), true);
                    addToList(cardsList, new EnFollowUp());
                    addToList(cardsList, new EnDeceiveReality());
                    turn++;
                    break;
                case 4:
                    // Turn 5 - 22
                    addToList(cardsList, new EnMasterReality());
                    addToList(cardsList, new EnLessonLearned(), extraUpgrades);
                    addToList(cardsList, new EnTranquility());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    EnEmptyFist e = new EnEmptyFist();
                    e.energyGeneratedIfPlayed = 2;
                    addToList(cardsList, e, extraUpgrades);
                    addToList(cardsList, new EnStrikePurple(), true);
                    addToList(cardsList, new EnDeceiveReality());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnProstrate());
                    addToList(cardsList, new EnConsecrate());
                    addToList(cardsList, new EnVigilance(), true);
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnFollowUp());
                    addToList(cardsList, new EnCarveReality(true), extraUpgrades);
                    addToList(cardsList, new EnRagnarok());
                    turn = 0;
                    break;
            }
        }

//        if (AbstractCharBoss.boss.powerhouseTurn){
//            AbstractCharBoss.boss.getPower(WatcherCripplePower.POWER_ID).onSpecificTrigger();
//        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_Duality());
    }
}
