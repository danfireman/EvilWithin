package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.*;

public class BerserkersShout extends AbstractChampCard {

    public final static String ID = makeID("BerserkersShout");

    //stupid intellij stuff skill, self, uncommon

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 3;

    public BerserkersShout() {
        super(ID, 0, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        //tags.add(ChampMod.TECHNIQUE);
        tags.add(ChampMod.OPENER);
        this.tags.add(ChampMod.OPENERBERSERKER);
       // myHpLossCost = magicNumber;
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
       // techique();
        berserkOpen();
        vigor(magicNumber);
        //AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, magicNumber));
      //  if (upgraded) techique();
    }



    public void upp() {
       // tags.add(ChampMod.TECHNIQUE);
        //postInit();
      //  initializeDescription();
        upgradeMagicNumber(3);
    }
}