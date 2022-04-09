package automaton.cards;

import automaton.AutomatonChar;
import automaton.cardmods.FullReleaseCardMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FullRelease extends AbstractBronzeCard {

    public final static String ID = makeID("FullRelease");

    //stupid intellij stuff attack, enemy, rare


    public FullRelease() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        thisEncodes();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void onCompileFirst(AbstractCard function, boolean forGameplay) {
        CardModifierManager.addModifier(function, new FullReleaseCardMod());
    }

    @Override
    public String getBonusChar() {
        return EXTENDED_DESCRIPTION[2];
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}