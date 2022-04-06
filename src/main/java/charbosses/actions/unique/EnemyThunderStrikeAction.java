package charbosses.actions.unique;


import charbosses.bosses.AbstractCharBoss;
import charbosses.orbs.EnemyEmptyOrbSlot;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class EnemyThunderStrikeAction extends AbstractGameAction {
    private DamageInfo info = null;
    private AbstractCreature target;
    private int hitCount;

    public EnemyThunderStrikeAction(AbstractCreature m, DamageInfo info, int hitCount) {
        this.info = info;
        this.target = m;
        this.hitCount = hitCount;
    }

    public void update() {
        for(int i = 0; i < hitCount; ++i) { // TODO: SFX
            this.addToTop(new DamageAction(this.target, this.info, AttackEffect.BLUNT_LIGHT, true));
        }

        this.isDone = true;
    }
}
