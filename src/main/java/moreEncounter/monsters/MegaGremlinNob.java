package moreEncounter.monsters;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AngerPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class MegaGremlinNob extends AbstractMonster {
  public static final String ID = "GremlinNob";
  
  public MegaGremlinNob(float x, float y) {
    this(x, y, true);
  }
  
  public MegaGremlinNob(float x, float y, boolean setVuln) {
    super(NAME, "GremlinNob", 400, -70.0F, -10.0F, 350.0F, 400.0F, null, x, y);
    this.usedBellow = false;
    this.intentOffsetX = -60.0F * Settings.scale;
    this.type = AbstractMonster.EnemyType.ELITE;
    this.dialogX = -90.0F * Settings.scale;
    this.dialogY = 150.0F * Settings.scale;
    this.canVuln = setVuln;
    if (AbstractDungeon.ascensionLevel >= 8) {
      setHp(390, 400);
    } else {
      setHp(360, 370);
    } 
    if (AbstractDungeon.ascensionLevel >= 3) {
      this.bashDmg = 10;
      this.rushDmg = 15;
    } else {
      this.bashDmg = 8;
      this.rushDmg = 18;
    } 
    this.damage.add(new DamageInfo((AbstractCreature)this, this.rushDmg));
    this.damage.add(new DamageInfo((AbstractCreature)this, this.bashDmg));
    loadAnimation("images/monsters/theBottom/nobGremlin/skeleton.atlas", "images/monsters/theBottom/nobGremlin/skeleton.json", 0.7F);
    AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
    e.setTime(e.getEndTime() * MathUtils.random());
  }
  
  public void takeTurn() {
    switch (this.nextMove) {
      case 1:
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new AnimateSlowAttackAction((AbstractCreature)this));
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        break;
      case 2:
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new AnimateSlowAttackAction((AbstractCreature)this));
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        if (this.canVuln)
          AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)this, (AbstractPower)new VulnerablePower((AbstractCreature)AbstractDungeon.player, 2, true), 2)); 
        break;
      case 3:
        playSfx();
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new TalkAction((AbstractCreature)this, DIALOG[0], 1.0F, 3.0F));
        if (AbstractDungeon.ascensionLevel >= 18) {
          AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction((AbstractCreature)this, (AbstractCreature)this, (AbstractPower)new AngerPower((AbstractCreature)this, 3), 3));
          break;
        } 
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction((AbstractCreature)this, (AbstractCreature)this, (AbstractPower)new AngerPower((AbstractCreature)this, 2), 2));
        break;
    } 
    AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new RollMoveAction(this));
  }
  
  private void playSfx() {
    int roll = MathUtils.random(2);
    if (roll == 0) {
      AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new SFXAction("VO_GREMLINNOB_1A"));
    } else if (roll == 1) {
      AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new SFXAction("VO_GREMLINNOB_1B"));
    } else {
      AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new SFXAction("VO_GREMLINNOB_1C"));
    } 
  }
  
  protected void getMove(int num) {
    if (!this.usedBellow) {
      this.usedBellow = true;
      setMove((byte)3, AbstractMonster.Intent.BUFF);
    } else {
      if (AbstractDungeon.ascensionLevel >= 18) {
        if (!lastMove((byte)2) && !lastMoveBefore((byte)2)) {
          if (this.canVuln) {
            setMove(MOVES[0], (byte)2, AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(1)).base);
          } else {
            setMove((byte)2, AbstractMonster.Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base);
          } 
          return;
        } 
      } else if (num < 33) {
        if (this.canVuln) {
          setMove(MOVES[0], (byte)2, AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(1)).base);
        } else {
          setMove((byte)2, AbstractMonster.Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base);
        } 
        return;
      } 
      if (lastTwoMoves((byte)1)) {
        if (this.canVuln) {
          setMove(MOVES[0], (byte)2, AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(1)).base);
        } else {
          setMove((byte)2, AbstractMonster.Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base);
        } 
      } else {
        setMove((byte)1, AbstractMonster.Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
      } 
    } 
  }
  
  private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GremlinNob");
  
  public static final String NAME = monsterStrings.NAME;
  
  public static final String[] MOVES = monsterStrings.MOVES;
  
  public static final String[] DIALOG = monsterStrings.DIALOG;
  
  private final int bashDmg;
  
  private final int rushDmg;
  
  private boolean usedBellow;
  
  private final boolean canVuln;
}
