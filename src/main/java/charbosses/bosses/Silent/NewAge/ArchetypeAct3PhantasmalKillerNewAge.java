package charbosses.bosses.Silent.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Silent.ArchetypeBaseSilent;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.curses.EnClumsy;
import charbosses.cards.curses.EnRegret;
import charbosses.cards.green.*;
import charbosses.powers.bossmechanicpowers.SilentPoisonPower;
import charbosses.powers.general.PoisonProtectionPower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct3PhantasmalKillerNewAge extends ArchetypeBaseSilent {

    public ArchetypeAct3PhantasmalKillerNewAge() {
        super("SI_PHANTASMAL_ARCHETYPE", "Phantasmal Killer");

        maxHPModifier += 300;
        actNum = 3;
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractDungeon.player;
        secondLoop = false;
    }

    public void initialize() {

        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_ArtOfWar());
        addRelic(new CBR_CoffeeDripper());
        addRelic(new CBR_CaptainsWheel());
        addRelic(new CBR_Whetstone());
        // addRelic(new CBR_DreamCatcher());
        // addRelic(new CBR_Cleric()); // Cleric to remove +1 Strike
        // addRelic(new CBR_UpgradeShrine()); // To upgrade Infinite Blades
        // addRelic(new CBR_WeMeetAgain());

    }

    EnGlassKnife knife1;
    EnGlassKnife knife2;
    EnGlassKnife knife3;
    EnGlassKnife knife4;
    boolean secondLoop = false;

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (!looped) {
            switch (turn) {
                case 0:
                    //Turn 1
                    addToList(cardsList, new EnBackstab());
                    addToList(cardsList, new EnFlyingKnee(), extraUpgrades);
                    addToList(cardsList, new EnAfterImage(), true);
                    turn++;
                    break;
                case 1:
                    //Turn 2
                    addToList(cardsList, new EnNeutralize());
                    addToList(cardsList, new EnPiercingWail());
                    addToList(cardsList, new EnSkewer(3), true);
                    turn++;
                    break;
                case 2:
                    //Turn 3
                    addToList(cardsList, new EnBladeDance());
                    addToList(cardsList, new EnFinisher(3), extraUpgrades);
                    addToList(cardsList, new EnOutmaneuver(), true);
                    turn++;
                    break;
                case 3:
                    //Turn 4
                    addToList(cardsList, new EnNightmare());
                    knife1 = new EnGlassKnife();
                    addToList(cardsList, knife1, false);
                    addToList(cardsList, new EnMalaise()); // Played for 2
                    turn++;
                    break;
                case 4:
                    //Turn 5
                    knife2 = new EnGlassKnife();
                    knife3 = new EnGlassKnife();
                    knife4 = new EnGlassKnife();
                    addToList(cardsList, knife2, false);
                    addToList(cardsList, knife3, false);
                    addToList(cardsList, knife4, false);
                    addToList(cardsList, new EnRiddleWithHoles(), extraUpgrades); // Not played
                    addToList(cardsList, new EnClumsy()); // Not played
                    addToList(cardsList, new EnPhantasmalKiller()); // Not played
                    turn = 0;
                    looped = true;
                    break;

            }
        } else {

            switch (turn) {
                case 0:
                    addToList(cardsList, new EnNeutralize());
                    addToList(cardsList, knife1);
                    addToList(cardsList, new EnSkewer(secondLoop? 5 : 2), true);
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, knife2);
                    addToList(cardsList, new EnBladeDance());
                    addToList(cardsList, new EnFinisher(4), extraUpgrades);
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, knife3);
                    addToList(cardsList, new EnFlyingKnee(), extraUpgrades);
                    addToList(cardsList, new EnPhantasmalKiller());
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, knife4);
                    addToList(cardsList, new EnRiddleWithHoles(), extraUpgrades);
                    addToList(cardsList, new EnOutmaneuver(), true);
                    secondLoop = true;
                    turn = 0;
                    break;
            }
        }
        return cardsList;
    }


    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_Shuriken());
    }
}