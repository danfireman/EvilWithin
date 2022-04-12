package charbosses.bosses.Watcher.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.ArchetypeBaseDefect;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnBlind;
import charbosses.cards.colorless.EnGoodInstincts;
import charbosses.cards.curses.EnDoubt;
import charbosses.cards.curses.EnNormality;
import charbosses.cards.curses.EnShame;
import charbosses.cards.curses.EnWrithe;
import charbosses.cards.purple.*;
import charbosses.powers.bossmechanicpowers.WatcherCripplePower;
import charbosses.powers.bossmechanicpowers.WatcherDivinityPower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct3DevaNewAge extends ArchetypeBaseDefect {

    private AbstractBossCard theVeryImportantFlyingSleeves = null;
    private AbstractBossCard theVeryImportantPerseverence = null;

    public ArchetypeAct3DevaNewAge() {
        super("WA_ARCHETYPE_DEVA", "Deva");

        maxHPModifier += 398;
        actNum = 3;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,  new WatcherCripplePower(p, 200), 150));

    }

    public void initialize() {

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_TungstenRod());
        addRelic(new CBR_Akabeko());
        addRelic(new CBR_BronzeScales());
        addRelic(new CBR_EmptyCage());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (!looped) {
            switch (turn) {
                case 0:
                    //Turn 2
                    addToList(cardsList, new EnFlyingSleeves(), false);
                    addToList(cardsList, new EnMeditate());
                    addToList(cardsList, new EnWrithe());
                    turn++;
                    break;
                case 1:
                    //Turn 2
                    EnEmptyBody emptyBody = new EnEmptyBody();
                    emptyBody.newPrio = 0;
                    addToList(cardsList, emptyBody);
                    addToList(cardsList, new EnDevaForm(), true);
                    theVeryImportantFlyingSleeves = new EnFlyingSleeves();
                    theVeryImportantFlyingSleeves.newPrio = 1;
                    addToList(cardsList, theVeryImportantFlyingSleeves, false); //Not played
                    addToList(cardsList, new EnCrushJoints()); //Not played
                    turn++;
                    break;
                case 2:
                    //Turn 3
                    theVeryImportantFlyingSleeves.lockIntentValues = false;
                    addToList(cardsList, new EnRagnarok(), extraUpgrades);
                    addToList(cardsList, new EnHalt(), true);
                    addToList(cardsList, new EnDoubt());    //Not played
                    AbstractCharBoss.boss.powerhouseTurn = true;
                    turn++;
                    break;
                case 3:
                    AbstractCharBoss.boss.powerhouseTurn = false;
                    theVeryImportantFlyingSleeves.lockIntentValues = false;
                    addToList(cardsList, new EnVigilance(), true);
                    EnEruption e = new EnEruption(true);
                    e.energyGeneratedIfPlayed = 2;
                    addToList(cardsList, e, false);
                    theVeryImportantFlyingSleeves.newPrio = 99;
                    EnEmptyFist c = new EnEmptyFist();
                    c.newPrio = 100;
                    addToList(cardsList, c, extraUpgrades);
                    turn++;
                    break;
                case 4:
                    addToList(cardsList, new EnTalkToTheHand(), true);
                    addToList(cardsList, new EnBattleHymn()); // TODO: that wallop
                    addToList(cardsList, new EnFasting(), false);
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    EnEruption e = new EnEruption(true);
                    e.energyGeneratedIfPlayed = 2;
                    e.newPrio = 0;
                    addToList(cardsList, e, false);
                    addToList(cardsList, new EnHalt(), true);
                    addToList(cardsList, new EnCrushJoints());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnEmptyFist(), extraUpgrades);
                    addToList(cardsList, new EnMeditate());
                    addToList(cardsList, new EnDoubt());
                    turn++;
                    break;
                case 2:
                    EnEruption eruption = new EnEruption(true);
                    eruption.newPrio = 0;
                    eruption.energyGeneratedIfPlayed = 2;
                    addToList(cardsList, eruption, false);
                    addToList(cardsList, new EnRagnarok(), extraUpgrades);
                    addToList(cardsList, new EnVigilance(), true);
                    addToList(cardsList, new EnWrithe());
                    AbstractCharBoss.boss.powerhouseTurn = true;
                    turn++;
                    break;
                case 3:
                    AbstractCharBoss.boss.powerhouseTurn = false;
                    addToList(cardsList, new EnFlyingSleeves(), false);
                    addToList(cardsList, new EnWallop());
                    addToList(cardsList, new EnEmptyBody());
                    turn = 0;
                    break;
            }
        }

        if (AbstractCharBoss.boss.powerhouseTurn){
            AbstractCharBoss.boss.getPower(WatcherCripplePower.POWER_ID).onSpecificTrigger();
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_DuvuDoll(2));
    }
}
