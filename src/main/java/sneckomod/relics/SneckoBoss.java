package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.util.TextureLoader;
import sneckomod.SneckoMod;
import sneckomod.cards.unknowns.UnknownClass;
import sneckomod.util.ColorfulCardReward;

public class SneckoBoss extends CustomRelic implements CustomSavable<AbstractCard.CardColor> {

    public static final String ID = SneckoMod.makeID("SneckoBoss");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("LuckyHorseshoe.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("LuckyHorseshoe.png"));

    public SneckoBoss() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public static AbstractCard.CardColor myColor = null; // shared between SneckoCommon and SneckoBoss
    private boolean chosenInGeneral = true;

    @Override
    public void onEquip() {
        if (myColor != null && AbstractDungeon.player.hasRelic(SneckoCommon.ID)) { // Already got Seal of Approval
            for (AbstractCard c : CardLibrary.getAllCards()) {
                if (c instanceof UnknownClass) {
                    if (myColor == ((UnknownClass) c).myColor) {
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c.makeCopy(), Settings.WIDTH * 0.2F, Settings.HEIGHT / 2F));
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c.makeCopy(), Settings.WIDTH * 0.35F, Settings.HEIGHT / 2F));
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c.makeCopy(), Settings.WIDTH * 0.5F, Settings.HEIGHT / 2F));
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c.makeCopy(), Settings.WIDTH * 0.65F, Settings.HEIGHT / 2F));
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c.makeCopy(), Settings.WIDTH * 0.8F, Settings.HEIGHT / 2F));
                    }
                }
            }
        } else {
            chosenInGeneral = false;
            if (AbstractDungeon.isScreenUp) {
                AbstractDungeon.dynamicBanner.hide();
                AbstractDungeon.overlayMenu.cancelButton.hide();
                AbstractDungeon.previousScreen = AbstractDungeon.screen;
            }

            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
            CardGroup c = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard q : CardLibrary.getAllCards()) {
                if (q instanceof UnknownClass) {
                    if (SneckoMod.validColors.contains(((UnknownClass) q).myColor) || SneckoMod.pureSneckoMode) {
                        c.addToTop(q.makeCopy());
                    }
                }
            }
            if (SneckoMod.pureSneckoMode) {
                c.shuffle();
                CardGroup r = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                for (int i = 0; i < 4; i++) {
                    r.addToTop(c.group.get(i));
                }
                AbstractDungeon.gridSelectScreen.open(r, 1, false, CardCrawlGame.languagePack.getUIString("bronze:MiscStrings").TEXT[8]);
            } else
                AbstractDungeon.gridSelectScreen.open(c, 1, false, CardCrawlGame.languagePack.getUIString("bronze:MiscStrings").TEXT[8]);
        }
    }

    public static void updateCardPools() {
        AbstractDungeon.commonCardPool.group.removeIf(q -> q instanceof UnknownClass && ((UnknownClass) q).myColor != myColor);
    }

    @Override
    public void update() {
        super.update();
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && !chosenInGeneral) {
            chosenInGeneral = true;
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            myColor = ((UnknownClass) c).myColor;
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c.makeCopy(), Settings.WIDTH * 0.2F, Settings.HEIGHT / 2F));
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c.makeCopy(), Settings.WIDTH * 0.35F, Settings.HEIGHT / 2F));
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c.makeCopy(), Settings.WIDTH * 0.5F, Settings.HEIGHT / 2F));
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c.makeCopy(), Settings.WIDTH * 0.65F, Settings.HEIGHT / 2F));
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c.makeCopy(), Settings.WIDTH * 0.8F, Settings.HEIGHT / 2F));
            updateCardPools();
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.description = getUpdatedDescription();
            this.tips.clear();
            this.tips.add(new PowerTip(this.name, this.description));
        }
    }

    public String getUpdatedDescription() {
        if (myColor != null) {
            return DESCRIPTIONS[1] + SneckoMod.getClassFromColor(myColor) + DESCRIPTIONS[2] + SneckoMod.getClassFromColor(myColor) + DESCRIPTIONS[3];
        }
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractCard.CardColor onSave() {
        return myColor;
    }

    @Override
    public void onLoad(AbstractCard.CardColor s) {
        myColor = s;
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public void onVictory() {
        AbstractDungeon.getCurrRoom().rewards.add(new ColorfulCardReward(myColor));
    }
}
