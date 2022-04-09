package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SwordSigil extends AbstractChampCard {

    public final static String ID = makeID("SwordSigil");

    public SwordSigil() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            techique();
        }
    }

    public void upp() {
        upgradeMagicNumber(2);
       // upgradeCool(2);
    }
}