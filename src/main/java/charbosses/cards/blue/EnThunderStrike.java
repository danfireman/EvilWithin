package charbosses.cards.blue;

import charbosses.actions.unique.EnemyBarrageAction;
import charbosses.actions.unique.EnemyThunderStrikeAction;
import charbosses.cards.AbstractBossCard;
import charbosses.orbs.EnemyFrost;
import charbosses.orbs.EnemyLightning;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import java.util.ArrayList;

public class EnThunderStrike extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Thunder Strike";
    private static final CardStrings cardStrings;

    public EnThunderStrike() {
        this(1);
    }

    public EnThunderStrike(int hitCount) {
        super(ID, cardStrings.NAME, "blue/attack/thunder_strike", 3, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.RARE, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 7;
        this.isMultiDamage = true;
        this.magicNumber = hitCount;
        intentMultiAmt = this.magicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int lightningCount = getLightningCount();
        this.addToBot(new EnemyThunderStrikeAction(p, new DamageInfo(m, this.damage, DamageInfo.DamageType.NORMAL), lightningCount));
    }

    public static int getLightningCount() {
        int lightningCount = 0;

        for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisCombat) {
            if (o instanceof EnemyLightning) {
                ++lightningCount;
            }
        }
        return lightningCount;
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return autoPriority() * this.owner.orbs.size();
    }

    public AbstractCard makeCopy() {
        return new EnThunderStrike();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Barrage");
    }
}
