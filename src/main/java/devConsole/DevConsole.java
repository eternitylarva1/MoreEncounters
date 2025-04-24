package devConsole;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.android.mods.BaseMod;
import com.megacrit.cardcrawl.android.mods.interfaces.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

public class DevConsole implements EditCardsSubscriber,
        PostInitializeSubscriber,
        EditStringsSubscriber,
        EditRelicsSubscriber,
        EditKeywordsSubscriber,PostBattleSubscriber {
    public static final String MOD_ID = "DevConsole";
    private static final Color YELLOW_COLOR = new Color(0.98F, 0.95F, 0.05F, 1.0F);

    public static void initialize() {
        new DevConsole();
    }

    public DevConsole() {
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
}
