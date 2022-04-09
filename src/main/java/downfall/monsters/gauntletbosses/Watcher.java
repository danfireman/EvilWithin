package downfall.monsters.gauntletbosses;

import charbosses.cards.purple.EnWish;
import charbosses.cards.purple.EnWishPlated;
import charbosses.core.EnemyEnergyManager;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.WallopAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Defend_Blue;
import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.cards.purple.Defend_Watcher;
import com.megacrit.cardcrawl.cards.purple.Strike_Purple;
import com.megacrit.cardcrawl.cards.purple.Wallop;
import com.megacrit.cardcrawl.cards.purple.Wish;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbPurple;
import downfall.downfallMod;
import downfall.powers.DrawReductionPowerPlus;
import downfall.powers.gauntletpowers.MonsterVigor;
import downfall.powers.gauntletpowers.OnDeathEveryoneBuffer;
import downfall.powers.gauntletpowers.OnDeathEveryoneVigor;

public class Watcher extends GauntletBoss {

    public static final String ID = downfallMod.makeID("GauntletWatcher");
    public static final String NAME = CardCrawlGame.languagePack.getCharacterString("Watcher").NAMES[0];
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 225.0F;
    private static final float HB_H = 250.0F;

    int turnNum = 0;

    public Watcher(float x, float y) {
        super(NAME, ID, 72 * 2, 0.0F, -5.0F, 240.0F, 270.0F, null, x, y);
        this.loadAnimation("images/characters/watcher/idle/skeleton.atlas", "images/characters/watcher/idle/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.flipHorizontal = true;
        this.stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.7F);

        type = EnemyType.ELITE;

        this.damage.add(new DamageInfo(this, 6));
        this.damage.add(new DamageInfo(this, 6));
        this.damage.add(new DamageInfo(this, 9));
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new OnDeathEveryoneVigor(this, 8), 8));
    }

    public void takeTurn() {

        int dex = 0;
        switch (this.nextMove) {
            case 1:
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                if (hasPower(MonsterVigor.POWER_ID)) {
                    addToBot(new RemoveSpecificPowerAction(this, this, MonsterVigor.POWER_ID));
                }
                break;
            case 2:
                if (this.hasPower(DexterityPower.POWER_ID)) {
                    dex = getPower(DexterityPower.POWER_ID).amount;
                }
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                addToBot(new GainBlockAction(this, 5 + dex));
                if (hasPower(MonsterVigor.POWER_ID)) {
                    addToBot(new RemoveSpecificPowerAction(this, this, MonsterVigor.POWER_ID));
                }
                break;
            case 3:
                if (this.hasPower(DexterityPower.POWER_ID)) {
                    dex = getPower(DexterityPower.POWER_ID).amount;
                }
                addToBot(new GainBlockAction(this, 10 + (dex * 2)));
                break;
            case 4:
                addToBot(new WallopAction(AbstractDungeon.player, this.damage.get(2)));
                if (hasPower(MonsterVigor.POWER_ID)) {
                    addToBot(new RemoveSpecificPowerAction(this, this, MonsterVigor.POWER_ID));
                }
                break;
            case 5:
                addToBot(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 8), 8));
                break;
        }


        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private void bossMove() {
        int rnd = AbstractDungeon.cardRandomRng.random(0, 3);
        switch (rnd) {
            case 0:
                isAttacking = true;
                setMove(moveName(Strike_Purple.ID, Strike_Purple.ID), (byte) 1, Intent.ATTACK, this.damage.get(0).base, 2, true);
                break;
            case 1:
                isAttacking = true;
                setMove(moveName(Strike_Purple.ID, Defend_Watcher.ID), (byte) 2, Intent.ATTACK_DEFEND, this.damage.get(1).base);
                break;
            case 2:
                isAttacking = false;
                setMove(moveName(Defend_Watcher.ID, Defend_Watcher.ID), (byte) 3, Intent.DEFEND);
                break;
            case 3:
                isAttacking = true;
                setMove(moveName(Wallop.ID), (byte) 4, Intent.ATTACK_DEFEND, this.damage.get(2).base);
                break;
        }
    }


    protected void getMove(int num) {
        turnNum++;
        if (turnNum == 5) {
            isAttacking = false;
            setMove(moveName(Wish.ID) + "+", (byte) 5, Intent.BUFF);
        } else {
            if (isThird && turnNum > 1 && ally1 != null && ally2 != null) {
                if (ally1.isAttacking && ally2.isAttacking) {
                    setMove(moveName(Defend_Watcher.ID, Defend_Watcher.ID), (byte) 3, Intent.DEFEND);
                } else {
                    bossMove();
                }

            } else {
                bossMove();
            }
        }


    }
}
