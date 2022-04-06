package charbosses.bosses.Ironclad.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.colorless.EnSwiftStrike;
import charbosses.cards.curses.EnClumsy;
import charbosses.cards.curses.EnHaunted;
import charbosses.cards.red.*;
import charbosses.powers.bossmechanicpowers.IroncladMushroomPower;
import charbosses.powers.cardpowers.EnemySpotWeaknessPower;
import charbosses.relics.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct2SpotWeaknessNewAge extends ArchetypeBaseIronclad {

    private CBR_ArtOfWar theArtOfWar;

    public ArchetypeAct2SpotWeaknessNewAge() {
        super("IC_SPOT_WEAKNESS_ARCHETYPE", "SpotWeakness");

        maxHPModifier += 160;
        actNum = 2;
    }

    AbstractCreature p;
    boolean firstTime = true;

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        p = AbstractCharBoss.boss;
    }


    public void initialize() {

        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());


       // addRelic(new CBR_ThreadAndNeedle());
        theArtOfWar = new CBR_ArtOfWar();
        addRelic(theArtOfWar);

        addRelic(new CBR_Orichalcum());
        addRelic(new CBR_Vajra());
      //  addRelic(new CBR_RedMask());  // gremlin mask
       // addRelic(new CBR_HappyFlower());  // gremlin mask

        /////   CARDS   /////
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;//Turn 1


    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;


        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnTwinStrike(), extraUpgrades);
                    addToList(cardsList, new EnGhostlyArmor());
                    addToList(cardsList, new EnStrikeRed(), true); // Not used
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnSpotWeakness());
                    addToList(cardsList, new EnRampage());
                    addToList(cardsList, new EnFlameBarrier()); // Not used
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EnemySpotWeaknessPower(p, 3)));
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnBash());
                    addToList(cardsList, new EnDefendRed()); // Not used
                    addToList(cardsList, new EnHaunted());
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnSwiftStrike(), extraUpgrades);
                    addToList(cardsList, new EnHeavyBlade());
                    addToList(cardsList, new EnClumsy());
                    turn++;
                    break;
                case 4:
                    addToList(cardsList, new EnSpotWeakness());
                    addToList(cardsList, new EnInflame(), true);
                    addToList(cardsList, new EnSwordBoomerang()); // Not used
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EnemySpotWeaknessPower(p, 3)));
                    theArtOfWar.beginPulse();
                    turn=0;
                    looped=true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    if (firstTime) {
                        addToList(cardsList, new EnSpotWeakness());
                        addToList(cardsList, new EnHeavyBlade());
                        addToList(cardsList, new EnSwiftStrike(), extraUpgrades);
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EnemySpotWeaknessPower(p, 3)));
                    }
                    else {
                        addToList(cardsList, new EnHeavyBlade());
                        addToList(cardsList, new EnSwiftStrike(), extraUpgrades);
                        addToList(cardsList, new EnSpotWeakness());
                    }
                    firstTime = false;
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnSpotWeakness());
                    addToList(cardsList, new EnGhostlyArmor(), true);
                    addToList(cardsList, new EnDefendRed()); // Not used
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EnemySpotWeaknessPower(p, 3)));
                    theArtOfWar.beginPulse();
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnBash());
                    addToList(cardsList, new EnTwinStrike(), extraUpgrades);
                    addToList(cardsList, new EnFlameBarrier()); // Not used
                    turn++;
                    break;
                case 3:
                    addToList(cardsList, new EnRampage());
                    addToList(cardsList, new EnSwordBoomerang());
                    addToList(cardsList, new EnStrikeRed(), true); // Not used
                    turn=0;
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_ChampionsBelt());
    }
}