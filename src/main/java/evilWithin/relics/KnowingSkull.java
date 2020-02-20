package evilWithin.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import evilWithin.EvilWithinMod;
import evilWithin.cards.KnowingSkullWish;

public class KnowingSkull extends CustomRelic {

    public static final String ID = EvilWithinMod.makeID("KnowingSkull");
    private static final Texture IMG = new Texture(EvilWithinMod.assetPath("images/relics/WingStatue.png"));
    private static final Texture OUTLINE = new Texture(EvilWithinMod.assetPath("images/relics/WingStatue.png"));

    public KnowingSkull() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStartPreDraw() {
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));// 24
        this.addToBot(new MakeTempCardInHandAction(new KnowingSkullWish()));// 25
    }
}
