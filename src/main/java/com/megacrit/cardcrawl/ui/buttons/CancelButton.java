//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.ui.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.core.CardCrawlGame.GameMode;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MenuPanelScreen.PanelScreen;
import ui.RoomRestartButton;

public class CancelButton {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final int W = 512;
    private static final int H = 256;
    private static final Color HOVER_BLEND_COLOR;
    private static final float SHOW_X;
    private static final float DRAW_Y;
    public static final float HIDE_X;
    public float current_x;
    private float target_x;
    public boolean isHidden;
    private float glowAlpha;
    private Color glowColor;
    public String buttonText;
    private static final float TEXT_OFFSET_X;
    private static final float TEXT_OFFSET_Y;
    private static final float HITBOX_W;
    private static final float HITBOX_H;
    public Hitbox hb;
    public   RoomRestartButton restartField = null;
    public CancelButton() {
        this.current_x = HIDE_X;
        this.target_x = this.current_x;
        this.isHidden = true;
        this.glowAlpha = 0.0F;
        this.glowColor = Settings.GOLD_COLOR.cpy();
        this.buttonText = "NOT_SET";
        this.hb = new Hitbox(0.0F, 0.0F, HITBOX_W, HITBOX_H);
        this.hb.move(SHOW_X - 106.0F * Settings.scale, DRAW_Y + 60.0F * Settings.scale);
        this.restartField = new RoomRestartButton();
    }

    public void update() {
        if (!this.isHidden) {

                this.updateGlow();
            this.hb.update();
            if (InputHelper.justClickedLeft && this.hb.hovered) {
                this.hb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
            }

            if (this.hb.justHovered) {
                CardCrawlGame.sound.play("UI_HOVER");
            }

            if (this.hb.clicked || (InputHelper.pressedEscape || CInputActionSet.cancel.isJustPressed()) && this.current_x != HIDE_X) {
                AbstractDungeon.screenSwap = false;
                InputHelper.pressedEscape = false;
                this.hb.clicked = false;
                this.hide();
                if (CardCrawlGame.mode == GameMode.CHAR_SELECT) {
                    this.hb.clicked = false;
                    if (CardCrawlGame.mainMenuScreen.statsScreen.screenUp) {
                        CardCrawlGame.mainMenuScreen.statsScreen.hide();
                        return;
                    }

                    if (CardCrawlGame.mainMenuScreen.isSettingsUp) {
                        CardCrawlGame.mainMenuScreen.lighten();
                        CardCrawlGame.mainMenuScreen.isSettingsUp = false;
                        CardCrawlGame.mainMenuScreen.screen = CurScreen.MAIN_MENU;
                        if (!CardCrawlGame.mainMenuScreen.panelScreen.panels.isEmpty()) {
                            CardCrawlGame.mainMenuScreen.panelScreen.open(PanelScreen.SETTINGS);
                        }

                        this.hide();
                        return;
                    }

                    if (this.buttonText.equals(TEXT[0])) {
                        return;
                    }
                }

                if (AbstractDungeon.screen == CurrentScreen.MAP) {
                    CardCrawlGame.sound.play("MAP_CLOSE", 0.05F);
                }

                if (AbstractDungeon.screen == CurrentScreen.GRID && (AbstractDungeon.gridSelectScreen.forUpgrade || AbstractDungeon.gridSelectScreen.forTransform || AbstractDungeon.gridSelectScreen.forPurge)) {
                    if (!AbstractDungeon.gridSelectScreen.confirmScreenUp) {
                        AbstractDungeon.closeCurrentScreen();
                        if (AbstractDungeon.getCurrRoom() instanceof RestRoom) {
                            RestRoom r = (RestRoom)AbstractDungeon.getCurrRoom();
                            r.campfireUI.reopen();
                        }

                        return;
                    }

                    AbstractDungeon.gridSelectScreen.cancelUpgrade();
                } else {
                    if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() instanceof TreasureRoomBoss && AbstractDungeon.screen == CurrentScreen.BOSS_REWARD) {
                        TreasureRoomBoss r = (TreasureRoomBoss)AbstractDungeon.getCurrRoom();
                        r.chest.close();
                    }

                    AbstractDungeon.closeCurrentScreen();
                }
            }

        }

        if (this.current_x != this.target_x) {
            this.current_x = MathUtils.lerp(this.current_x, this.target_x, Gdx.graphics.getDeltaTime() * 9.0F);
            if (Math.abs(this.current_x - this.target_x) < Settings.UI_SNAP_THRESHOLD) {
                this.current_x = this.target_x;
            }
        }
        if (CardCrawlGame.isInARun() &&AbstractDungeon.screen == AbstractDungeon.CurrentScreen.SETTINGS &&(AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoom || AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoomElite || AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoomBoss) && !(AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
            this.restartField.update();
        }
    }

    private void updateGlow() {
        this.glowAlpha += Gdx.graphics.getDeltaTime() * 3.0F;
        if (this.glowAlpha < 0.0F) {
            this.glowAlpha *= -1.0F;
        }

        float tmp = MathUtils.cos(this.glowAlpha);
        if (tmp < 0.0F) {
            this.glowColor.a = -tmp / 2.0F + 0.3F;
        } else {
            this.glowColor.a = tmp / 2.0F + 0.3F;
        }

    }

    public boolean hovered() {
        return this.hb.hovered;
    }

    public void hide() {
        if (!this.isHidden) {
            this.hb.hovered = false;
            InputHelper.justClickedLeft = false;
            this.target_x = HIDE_X;
            this.isHidden = true;
        }
        restartField.hide();
    }

    public void hideInstantly() {
        if (!this.isHidden) {
            this.hb.hovered = false;
            InputHelper.justClickedLeft = false;
            this.target_x = HIDE_X;
            this.current_x = this.target_x;
            this.isHidden = true;
        }
         restartField.hideInstantly();

    }

    public void show(String buttonText) {
        if (this.isHidden) {
            this.glowAlpha = 0.0F;
            this.current_x = HIDE_X;
            this.target_x = SHOW_X;
            this.isHidden = false;
            this.buttonText = buttonText;
        } else {
            this.current_x = HIDE_X;
            this.buttonText = buttonText;
        }

        this.hb.hovered = false;
        if (CardCrawlGame.isInARun() && (AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoom || AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoomElite || AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoomBoss) && !(AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead())
            restartField.show(buttonText);
    }

    public void showInstantly(String buttonText) {
        this.current_x = SHOW_X;
        this.target_x = SHOW_X;
        this.isHidden = false;
        this.buttonText = buttonText;
        this.hb.hovered = false;
        if (CardCrawlGame.isInARun() && (AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoom || AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoomElite || AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoomBoss) && !(AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead())
            restartField.showInstantly(buttonText);
    }

    public void render(SpriteBatch sb) {

        sb.setColor(Color.WHITE);
        this.renderShadow(sb);
        sb.setColor(this.glowColor);
        this.renderOutline(sb);
        sb.setColor(Color.WHITE);
        this.renderButton(sb);
        if (this.hb.hovered && !this.hb.clickStarted) {
            sb.setBlendFunction(770, 1);
            sb.setColor(HOVER_BLEND_COLOR);
            this.renderButton(sb);
            sb.setBlendFunction(770, 771);
        }

        Color tmpColor = Settings.LIGHT_YELLOW_COLOR;
        if (this.hb.clickStarted) {
            tmpColor = Color.LIGHT_GRAY;
        }

        if (Settings.isControllerMode) {
            //  FontHelper.renderFontLeft(sb, FontHelper.buttonLabelFont, this.buttonText, this.current_x + TEXT_OFFSET_X - 30.0F * Settings.scale, DRAW_Y + TEXT_OFFSET_Y, tmpColor);
            FontHelper.renderFontLeft(sb, FontHelper.buttonLabelFont, this.buttonText, this.current_x + TEXT_OFFSET_X - 30.0F * Settings.scale, DRAW_Y + TEXT_OFFSET_Y, tmpColor);
        } else {
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, this.buttonText, this.current_x + TEXT_OFFSET_X, DRAW_Y + TEXT_OFFSET_Y, tmpColor);
        }

        this.renderControllerUi(sb);
        if (!this.isHidden) {
            this.hb.render(sb);
        }
        if (CardCrawlGame.isInARun() &&AbstractDungeon.screen == AbstractDungeon.CurrentScreen.SETTINGS && (AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoom || AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoomElite || AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoomBoss) && !(AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
            this.restartField.render(sb);
        }
    }

    private void renderShadow(SpriteBatch sb) {
        sb.draw(ImageMaster.CANCEL_BUTTON_SHADOW, this.current_x - 256.0F, DRAW_Y - 128.0F, 256.0F, 128.0F, 512.0F, 256.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
    }

    private void renderOutline(SpriteBatch sb) {
        sb.draw(ImageMaster.CANCEL_BUTTON_OUTLINE, this.current_x - 256.0F, DRAW_Y - 128.0F, 256.0F, 128.0F, 512.0F, 256.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
    }

    private void renderButton(SpriteBatch sb) {
        sb.draw(ImageMaster.CANCEL_BUTTON, this.current_x - 256.0F, DRAW_Y - 128.0F, 256.0F, 128.0F, 512.0F, 256.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
    }

    private void renderControllerUi(SpriteBatch sb) {
        if (Settings.isControllerMode) {
            sb.setColor(Color.WHITE);
            sb.draw(CInputActionSet.cancel.getKeyImg(), this.current_x - 32.0F - 210.0F * Settings.scale, DRAW_Y - 32.0F + 57.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }

    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("Cancel Button");
        TEXT = uiStrings.TEXT;
        HOVER_BLEND_COLOR = new Color(1.0F, 1.0F, 1.0F, 0.4F);
        SHOW_X = 256.0F * Settings.scale;
        DRAW_Y = 128.0F * Settings.scale;
        HIDE_X = SHOW_X - 400.0F * Settings.scale;
        TEXT_OFFSET_X = -136.0F * Settings.scale;
        TEXT_OFFSET_Y = 57.0F * Settings.scale;
        HITBOX_W = 300.0F * Settings.scale;
        HITBOX_H = 100.0F * Settings.scale;
    }
}
