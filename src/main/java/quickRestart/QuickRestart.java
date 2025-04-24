package quickRestart;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.android.mods.BaseMod;
import com.megacrit.cardcrawl.android.mods.interfaces.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import helpers.RestartRunHelper;

public class QuickRestart implements EditCardsSubscriber,
        PostInitializeSubscriber,
        EditStringsSubscriber,
        EditRelicsSubscriber,
        EditKeywordsSubscriber,PostBattleSubscriber ,PostRenderSubscriber{
    public static final String MOD_ID = "QuickRestart";
    private static final Color YELLOW_COLOR = new Color(0.98F, 0.95F, 0.05F, 1.0F);

    public static void initialize() {
        new QuickRestart();
    }

    public QuickRestart() {
        BaseMod.subscribe(this);
    }

    public static String makeId(String name) {
        return MOD_ID + ":" + name;
    }

    public static String getResourcePath(String path) {
        return "UnlockAll-TestImages/" + path;
    }

    @Override
    public void receiveEditCards() {

    }



    @Override
    public void receiveEditStrings() {
    }

    @Override
    public void receiveEditRelics() {

    }

    @Override
    public void receiveEditKeywords() {

    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {

    }

    @Override
    public void receivePostInitialize() {

    }

    @Override
    public void receivePostRender(SpriteBatch spriteBatch) {
        if (RestartRunHelper.queuedRoomRestart) {
            RestartRunHelper.restartRoom();
        }
    }
}
