package theHexaghost.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHexaghost.HexaMod;
import theHexaghost.util.OnRetractSubscriber;
import theHexaghost.util.TextureLoader;

public class PastPower extends AbstractPower implements CloneablePowerInterface, OnRetractSubscriber {

    public static final String POWER_ID = HexaMod.makeID("PastPower");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Past84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Past32.png");
    public boolean activated = false;

    public PastPower(final int amount) {
        this.name = "Devil's Dance";
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        activated = false;
    }

    @Override
    public void onRetract() {
        if (!activated) {
            this.flash();
            addToBot(new GainEnergyAction(amount));
            addToBot(new DrawCardAction(amount));
            activated = true;
        }
    }

    @Override
    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("The first time you #yRetract each turn, gain ");

        for (int i = 0; i < this.amount; ++i) {
            sb.append("[E] ");
        }
        if (amount == 1)
            sb.append(" and draw #b" + amount + " card.");
        else
            sb.append(" and draw #b" + amount + " cards.");
        this.description = sb.toString();
    }

    @Override
    public AbstractPower makeCopy() {
        return new PastPower(amount);
    }
}