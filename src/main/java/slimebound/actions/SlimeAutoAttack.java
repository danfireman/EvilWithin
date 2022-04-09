package slimebound.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.orbs.BronzeSlime;
import slimebound.orbs.ShieldSlime;
import slimebound.orbs.SpawnedSlime;
import guardian.powers.LoseThornsPower;
import slimebound.powers.PotencyPower;
import slimebound.powers.SlimedPower;
import slimebound.vfx.SearEffect;
import slimebound.vfx.SlimeIntentEffect;
import theHexaghost.powers.BurnPower;


public class SlimeAutoAttack extends AbstractGameAction {

    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    private AbstractCreature owner;
    private int damage;
    private int debuffamount;
    private AttackEffect AE;
    private SpawnedSlime slime;
    private boolean beamVFX;
    private boolean CultistBuff;
    private boolean appliesPoison;
    private boolean appliesSlimed;
    private boolean appliesWeak;
    private boolean appliesBurn;
    private boolean appliesVulnerable;
    private boolean hitsAll;
    private boolean searVFX;
    private boolean selfThorns;


    public SlimeAutoAttack(AbstractCreature player, Integer damage, AttackEffect AE, SpawnedSlime slime, boolean appliesPoison, boolean appliesSlimed, boolean appliesWeak, Integer debuffamount, boolean beamVFX, int block, boolean CultistBuff) {

        this.owner = player;
        this.actionType = ActionType.POWER;
        this.attackEffect = AttackEffect.POISON;
        this.duration = 0.01F;
        this.debuffamount = debuffamount;
        this.AE = AE;
        this.damage = damage;
        this.slime = slime;
        this.beamVFX = beamVFX;
        this.CultistBuff = CultistBuff;
        this.appliesPoison = appliesPoison;
        this.appliesSlimed = appliesSlimed;
        this.appliesWeak = appliesWeak;
        this.hitsAll = false;
        this.appliesVulnerable = false;
        this.appliesBurn = false;

    }

    public SlimeAutoAttack(AbstractCreature player, Integer damage, AttackEffect AE, SpawnedSlime slime, boolean appliesPoison, boolean appliesSlimed, boolean appliesWeak, Integer debuffamount, boolean beamVFX, int block, boolean CultistBuff, boolean hitsall) {

        this.owner = player;
        this.actionType = ActionType.POWER;
        this.attackEffect = AttackEffect.POISON;
        this.duration = 0.01F;
        this.debuffamount = debuffamount;
        this.AE = AE;
        this.damage = damage;
        this.slime = slime;
        this.beamVFX = beamVFX;
        this.CultistBuff = CultistBuff;
        this.appliesPoison = appliesPoison;
        this.appliesSlimed = appliesSlimed;
        this.appliesWeak = appliesWeak;
        this.hitsAll = hitsall;
        this.appliesVulnerable = false;
        this.appliesBurn = false;

    }

    public SlimeAutoAttack(AbstractCreature player, Integer damage, AttackEffect AE, SpawnedSlime slime, boolean appliesPoison, boolean appliesSlimed, boolean appliesWeak, Integer debuffamount, boolean beamVFX, int block, boolean CultistBuff, boolean hitsall, boolean appliesVuln, boolean appliesBurn, boolean searVFX) {

        this.owner = player;
        this.actionType = ActionType.POWER;
        this.attackEffect = AttackEffect.POISON;
        this.duration = 0.01F;
        this.debuffamount = debuffamount;
        this.AE = AE;
        this.damage = damage;
        this.slime = slime;
        this.beamVFX = beamVFX;
        this.CultistBuff = CultistBuff;
        this.appliesPoison = appliesPoison;
        this.appliesSlimed = appliesSlimed;
        this.appliesWeak = appliesWeak;
        this.hitsAll = hitsall;
        this.appliesVulnerable = appliesVuln;
        this.appliesBurn = appliesBurn;
        this.searVFX = searVFX;

    }

    public SlimeAutoAttack(AbstractCreature player, Integer damage, AttackEffect AE, SpawnedSlime slime, boolean appliesPoison, boolean appliesSlimed, boolean appliesWeak, Integer debuffamount, boolean beamVFX, int block, boolean CultistBuff, boolean hitsall, boolean appliesVuln, boolean appliesBurn, boolean searVFX, boolean appliesSelfThorns) {

        this.owner = player;
        this.actionType = ActionType.POWER;
        this.attackEffect = AttackEffect.POISON;
        this.duration = 0.01F;
        this.debuffamount = debuffamount;
        this.AE = AE;
        this.damage = damage;
        this.slime = slime;
        this.beamVFX = beamVFX;
        this.CultistBuff = CultistBuff;
        this.appliesPoison = appliesPoison;
        this.appliesSlimed = appliesSlimed;
        this.appliesWeak = appliesWeak;
        this.hitsAll = hitsall;
        this.appliesVulnerable = appliesVuln;
        this.appliesBurn = appliesBurn;
        this.searVFX = searVFX;
        this.selfThorns = appliesSelfThorns;

    }

    public void update() {

        logger.info("Starting auto attack");
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
            return;
        }

        if (slime instanceof BronzeSlime){
            if (((BronzeSlime)slime).stunned){
                this.isDone = true;
                ((BronzeSlime)slime).unsquish();
                return;
            }
        }

        logger.info("Finding target");
        float speedTime = 0.2F / (float) AbstractDungeon.player.orbs.size();

        if (Settings.FAST_MODE) {

            speedTime = 0.10F;

        }
        AbstractCreature mo = AbstractDungeon.getMonsters().getRandomMonster(true);
        if (mo != null) {
            if (this.CultistBuff) {
                CardCrawlGame.sound.playA("VO_CULTIST_1A", .3f);
                AbstractDungeon.actionManager.addToTop(new SlimeAutoCultistBuff(1, this.slime));

            }

            if (slime instanceof ShieldSlime)
                AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.debuffamount));

            if (this.hitsAll) {
                AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(this.damage, true, true), DamageInfo.DamageType.THORNS, AttackEffect.POISON));

            } else {
                AbstractDungeon.actionManager.addToTop(new DamageAction(mo,
                        new DamageInfo(AbstractDungeon.player, this.damage, DamageInfo.DamageType.THORNS),
                        AE));
            }

            if (this.appliesPoison)
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mo, AbstractDungeon.player, new PoisonPower(mo, AbstractDungeon.player, this.debuffamount), this.debuffamount, true, AbstractGameAction.AttackEffect.POISON));
            if (this.appliesSlimed)
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mo, AbstractDungeon.player, new SlimedPower(mo, AbstractDungeon.player, this.debuffamount), this.debuffamount, true, AttackEffect.NONE));
            if (this.appliesWeak)
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mo, AbstractDungeon.player, new WeakPower(mo, this.debuffamount, false), this.debuffamount, true, AbstractGameAction.AttackEffect.NONE));
            if (this.appliesVulnerable)
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mo, AbstractDungeon.player, new VulnerablePower(mo, this.debuffamount, false), this.debuffamount, true, AbstractGameAction.AttackEffect.NONE));
            if (this.appliesBurn)
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mo, AbstractDungeon.player, new BurnPower(mo, this.debuffamount), this.debuffamount, true, AbstractGameAction.AttackEffect.NONE));
            if (this.selfThorns) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ThornsPower(AbstractDungeon.player, this.debuffamount), this.debuffamount, true, AbstractGameAction.AttackEffect.NONE));
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseThornsPower(AbstractDungeon.player, this.debuffamount), this.debuffamount, true, AbstractGameAction.AttackEffect.NONE));
            }
            AbstractDungeon.actionManager.addToTop(new VFXAction(new SlimeIntentEffect(slime.intentImage, slime, speedTime), speedTime));
            if (slime.movesToAttack) {
                //.actionManager.addToTop(new VFXAction(new SlimeIntentMovementEffect(slime, speedTime), speedTime));
                slime.useFastAttackAnimation();

            }
            if (this.beamVFX) {
                CardCrawlGame.sound.playA("ATTACK_MAGIC_BEAM_SHORT", -.2f);
                if (hitsAll) {
                    for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(slime.cX + 3 * Settings.scale, slime.cY + 22 * Settings.scale, q.hb.cX, q.hb.cY)));

                    }

                } else {
                    AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(slime.cX + 3 * Settings.scale, slime.cY + 22 * Settings.scale, mo.hb.cX, mo.hb.cY)));
                }
            }
            if (this.searVFX) {
                AbstractDungeon.actionManager.addToTop(new VFXAction(new SearEffect(slime.cX + 3 * Settings.scale, slime.cY + 22 * Settings.scale, mo.hb.cX, mo.hb.cY), 0.3F));
            }
            if (slime instanceof BronzeSlime){
                ((BronzeSlime)slime).squish();
            }

            //logger.info("Targetng " + mo.name);

        }


        this.isDone = true;
    }
}

