package helpers;


import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.SeedHelper;
import com.megacrit.cardcrawl.helpers.TipTracker;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.screens.DungeonTransitionScreen;
import com.megacrit.cardcrawl.shop.ShopScreen;
import java.lang.reflect.Field;


public class RestartRunHelper {
  public static boolean queuedRestart;
  
  public static boolean queuedScoreRestart;
  
  public static boolean queuedRoomRestart;
  
  public static boolean evilMode = false;
  
  public static void restartRun() {
    stopLingeringSounds();
    AbstractDungeon.getCurrRoom().clearEvent();
    if (!queuedRestart)
      AbstractDungeon.closeCurrentScreen(); 
    CardCrawlGame.dungeonTransitionScreen = new DungeonTransitionScreen("Exordium");
    AbstractDungeon.reset();
    Settings.hasEmeraldKey = false;
    Settings.hasRubyKey = false;
    Settings.hasSapphireKey = false;
    ShopScreen.resetPurgeCost();
    CardCrawlGame.tips.initialize();
    CardCrawlGame.metricData.clearData();
    CardHelper.clear();
    TipTracker.refresh();
    System.gc();

    if (evilMode)
      setDownfallMode(); 
    if (CardCrawlGame.chosenCharacter == null)
      CardCrawlGame.chosenCharacter = AbstractDungeon.player.chosenClass; 
    if (!Settings.seedSet) {

      Long sTime = Long.valueOf(System.nanoTime());
      Random rng = new Random(sTime);
      Settings.seedSourceTimestamp = sTime.longValue();
      Settings.seed = Long.valueOf(SeedHelper.generateUnoffensiveSeed(rng));
      SeedHelper.cachedSeed = null;
    } 
    AbstractDungeon.generateSeeds();

    CardCrawlGame.mode = CardCrawlGame.GameMode.CHAR_SELECT;

    queuedRestart = false;
    evilMode = false;
  }
  
  public static void scoreAndRestart() {
    AbstractDungeon.deathScreen = new DeathScreen(AbstractDungeon.getMonsters());

    restartRun();
    queuedScoreRestart = false;
  }
  
  public static void restartRoom() {
    stopLingeringSounds();
    AbstractDungeon.closeCurrentScreen();
    AbstractDungeon.dungeonMapScreen.closeInstantly();
    AbstractDungeon.reset();
    CardCrawlGame.loadingSave = true;
    CardCrawlGame.mode = CardCrawlGame.GameMode.CHAR_SELECT;

    queuedRoomRestart = false;
  }
  
  public static void stopLingeringSounds() {
    CardCrawlGame.music.fadeAll();
    if (Settings.AMBIANCE_ON)
      CardCrawlGame.sound.stop("WIND"); 
    if (AbstractDungeon.scene != null)
      AbstractDungeon.scene.fadeOutAmbiance(); 
  }
  
  private static Field evilField = null;
  
  public static boolean isDownfallMode() {
    if (evilField == null)
      try {
        Class<?> clz = Class.forName("downfall.patches.EvilModeCharacterSelect");
        evilField = clz.getField("evilMode");
      } catch (ClassNotFoundException|NoSuchFieldException e) {
        e.printStackTrace();
      }  
    try {
      return evilField.getBoolean(null);
    } catch (IllegalAccessException e) {
      return false;
    } 
  }
  
  public static void setDownfallMode() {
    if (evilField == null)
      try {
        Class<?> clz = Class.forName("downfall.patches.EvilModeCharacterSelect");
        evilField = clz.getField("evilMode");
      } catch (ClassNotFoundException|NoSuchFieldException e) {
        e.printStackTrace();
      }  
    try {
      evilField.set(null, Boolean.valueOf(true));
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } 
  }
}
