package moreEncounter;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.android.mods.BaseMod;
import com.megacrit.cardcrawl.android.mods.interfaces.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.monsters.beyond.*;
import com.megacrit.cardcrawl.monsters.city.*;
import com.megacrit.cardcrawl.monsters.exordium.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import moreEncounter.monsters.MegaGremlinNob;

import java.util.ArrayList;

public class MoreEncounter implements EditCardsSubscriber,
        PostInitializeSubscriber,
        EditStringsSubscriber,
        EditRelicsSubscriber,
        EditKeywordsSubscriber, PostBattleSubscriber {
    public static final String MOD_ID = "MoreEncounter";
    private static final Color YELLOW_COLOR = new Color(0.98F, 0.95F, 0.05F, 1.0F);

    public static void initialize() {
        new MoreEncounter();
    }

    public MoreEncounter() {
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

    private static void addEncounter(String dungeon, EncounterType type, String id, float weight, BaseMod.GetMonster func) {
        BaseMod.addMonster(id, func);
        switch (type) {
            case WEAK:
                BaseMod.addMonsterEncounter(dungeon, new MonsterInfo(id, weight));
                break;
            case STRONG:
                BaseMod.addStrongMonsterEncounter(dungeon, new MonsterInfo(id, weight));
                break;
            case ELITE:
                BaseMod.addEliteEncounter(dungeon, new MonsterInfo(id, weight));
                break;
        }
    }

    private static void addEncounter(String dungeon, EncounterType type, String id, float weight, BaseMod.GetMonsterGroup func) {
        BaseMod.addMonster(id, func);
        switch (type) {
            case WEAK:
                BaseMod.addMonsterEncounter(dungeon, new MonsterInfo(id, weight));
                break;
            case STRONG:
                BaseMod.addStrongMonsterEncounter(dungeon, new MonsterInfo(id, weight));
                break;
            case ELITE:
                BaseMod.addEliteEncounter(dungeon, new MonsterInfo(id, weight));
                break;
        }
    }

    private void addExordiumEncounters() {
        String id = "Exordium";

        addEncounter(id, EncounterType.WEAK, "1 Sentry", 2.0F, new BaseMod.GetMonster() {
            @Override
            public AbstractMonster get() {
                return new Sentry(0.0F, 0.0F);
            }
        });
        addEncounter(id, EncounterType.WEAK, "Exploder", 1.5F, new BaseMod.GetMonster() {
            @Override
            public AbstractMonster get() {
                return new Exploder(0.0F, 0.0F);
            }
        });
        addEncounter(id, EncounterType.WEAK, "2 Gremlins", 1.5F, new BaseMod.GetMonsterGroup() {
            @Override
            public MonsterGroup get() {
                ArrayList<String> gremlinPool = new ArrayList<>();
                gremlinPool.add("GremlinWarrior");
                gremlinPool.add("GremlinWarrior");
                gremlinPool.add("GremlinThief");
                gremlinPool.add("GremlinFat");
                gremlinPool.add("GremlinTsundere");
                gremlinPool.add("GremlinWizard");
                AbstractMonster[] list = new AbstractMonster[2];
                String id1 = gremlinPool.get(AbstractDungeon.miscRng.random(gremlinPool.size() - 1));
                gremlinPool.remove(id1);
                list[0] = MonsterHelper.getGremlin(id1, -100.0F, 15.0F);
                id1 = gremlinPool.get(AbstractDungeon.miscRng.random(gremlinPool.size() - 1));
                gremlinPool.remove(id1);
                list[1] = MonsterHelper.getGremlin(id1, 20.0F, 0.0F);
                return new MonsterGroup(list);
            }
        });
        addEncounter(id, EncounterType.STRONG, "2 Snake daggers", 1.0F, new BaseMod.GetMonsterGroup() {
            @Override
            public MonsterGroup get() {
                return new MonsterGroup(new AbstractMonster[]{
                        (AbstractMonster) new SnakeDagger(-100.0F, 5.0F),
                        (AbstractMonster) new SnakeDagger(40.0F, 0.0F)
                });
            }
        });
        addEncounter(id, EncounterType.STRONG, "2 Byrds", 2.0F, new BaseMod.GetMonsterGroup() {
            @Override
            public MonsterGroup get() {
                return new MonsterGroup(new AbstractMonster[]{
                        (AbstractMonster) new Byrd(-80.0F, MathUtils.random(25.0F, 70.0F)),
                        (AbstractMonster) new Byrd(200.0F, MathUtils.random(25.0F, 70.0F))
                });
            }
        });
        addEncounter(id, EncounterType.STRONG, "2 Shapes", 1.0F, new BaseMod.GetMonsterGroup() {
            @Override
            public MonsterGroup get() {
                return new MonsterGroup(new AbstractMonster[]{
                        (AbstractMonster) new Exploder(-150.0F, 15.0F),
                        (AbstractMonster) new Repulsor(80.0F, 0.0F)
                });
            }
        });
        addEncounter(id, EncounterType.ELITE, "2 Cultists", 1.0F, new BaseMod.GetMonsterGroup() {
            @Override
            public MonsterGroup get() {
                return new MonsterGroup(new AbstractMonster[]{
                        (AbstractMonster) new Cultist(-200.0F, 15.0F),
                        (AbstractMonster) new Cultist(80.0F, 0.0F)
                });
            }
        });
        addEncounter(id, EncounterType.ELITE, "Snecko in Exordium", 2.0F, new BaseMod.GetMonster() {
            @Override
            public AbstractMonster get() {
                Snecko snecko = new Snecko();
                snecko.currentHealth = (int) (snecko.maxHealth * 0.75F);
                snecko.healthBarUpdatedEvent();
                return snecko;
            }
        });
    }

    private void addCityEncounters() {
        String id = "TheCity";
        addEncounter(id, EncounterType.WEAK, "2 Jaw Worms", 2.0F, new BaseMod.GetMonsterGroup() {
            @Override
            public MonsterGroup get() {
                return new MonsterGroup(new AbstractMonster[]{
                        (AbstractMonster) new JawWorm(-200.0F, 15.0F),
                        (AbstractMonster) new JawWorm(80.0F, 0.0F)
                });
            }
        });
        addEncounter(id, EncounterType.WEAK, "Weak Giant Head", 2.0F, new BaseMod.GetMonster() {
            @Override
            public AbstractMonster get() {
                GiantHead head = new GiantHead();
                head.currentHealth = (int) (head.maxHealth / 3.5F);
                head.healthBarUpdatedEvent();
                return head;
            }
        });
        addEncounter(id, EncounterType.STRONG, "2 Jaw Worms (hard)", 2.0F, new BaseMod.GetMonsterGroup() {
            @Override
            public MonsterGroup get() {
                return new MonsterGroup(new AbstractMonster[]{
                        (AbstractMonster) new JawWorm(-200.0F, 15.0F, true),
                        (AbstractMonster) new JawWorm(80.0F, 0.0F, false)
                });
            }
        });
        addEncounter(id, EncounterType.STRONG, "2 Darklings", 1.0F, new BaseMod.GetMonsterGroup() {
            @Override
            public MonsterGroup get() {
                return new MonsterGroup(new AbstractMonster[]{
                        (AbstractMonster) new Darkling(-200.0F, 15.0F),
                        (AbstractMonster) new Darkling(80.0F, 0.0F)
                });
            }
        });
        BaseMod.addEliteEncounter(id, new MonsterInfo("4 Byrds", 2.0F));
        addEncounter(id, EncounterType.ELITE, "Weak Reptomancer", 1.0F, new BaseMod.GetMonster() {
            @Override
            public AbstractMonster get() {
                return new Reptomancer();
            }
        });
    }

    private void addBeyondEncounters() {
        String id = "TheBeyond";
        addEncounter(id, EncounterType.WEAK, "2 Large Slimes", 2.0F, new BaseMod.GetMonsterGroup() {
            @Override
            public MonsterGroup get() {
                return new MonsterGroup(new AbstractMonster[]{
                        (AbstractMonster) new SpikeSlime_L(-385.0F, 20.0F, 0, AbstractDungeon.miscRng.random(60, 70)),
                        (AbstractMonster) new AcidSlime_L(-385.0F, 20.0F, 0, AbstractDungeon.miscRng.random(70, 80))
                });
            }
        });
        addEncounter(id, EncounterType.WEAK, "Awaken Lagavulin", 1.0F, new BaseMod.GetMonster() {
            @Override
            public AbstractMonster get() {
                return new Lagavulin(false);
            }
        });
        addEncounter(id, EncounterType.STRONG, "FungiBeast and Snake Plant", 1.0F, new BaseMod.GetMonsterGroup() {
            @Override
            public MonsterGroup get() {
                return new MonsterGroup(new AbstractMonster[]{
                        (AbstractMonster) new SnakePlant(0.0F, 0.0F),
                        (AbstractMonster) new FungiBeast(-250.0F, -15.0F)
                });
            }
        });
        addEncounter(id, EncounterType.STRONG, "Minions", 2.0F, new BaseMod.GetMonsterGroup() {
            @Override
            public MonsterGroup get() {

                ArrayList<String> gremlinPool = new ArrayList<>();
                gremlinPool.add("GremlinWarrior");
                gremlinPool.add("GremlinThief");
                gremlinPool.add("GremlinFat");
                gremlinPool.add("GremlinTsundere");
                gremlinPool.add("GremlinWizard");
                return new MonsterGroup(new AbstractMonster[]{
                        MonsterHelper.getGremlin(gremlinPool.get(AbstractDungeon.miscRng.random(gremlinPool.size() - 1)), -600.0F, 10.0F),
                        MonsterHelper.getGremlin(gremlinPool.get(AbstractDungeon.miscRng.random(gremlinPool.size() - 1)), -500.0F, 0.0F),
                        (AbstractMonster) new TorchHead(-400.0F, 0.0F),
                        (AbstractMonster) new TorchHead(-300.0F, -5.0F),
                        (AbstractMonster) new BronzeOrb(-150.0F, 5.0F, 1),
                        (AbstractMonster) new BronzeOrb(-20.0F, 5.0F, 2),
                        (AbstractMonster) new SnakeDagger(100.0F, 10.0F)
                });
            }
        });
        BaseMod.addEliteEncounter(id, new MonsterInfo("Snecko and Mystics", 3.0F));
        BaseMod.addEliteEncounter(id, new MonsterInfo("Mysterious Sphere", 2.0F));
        addEncounter(id, EncounterType.ELITE, "Snekco and Chosen", 2.0F, new BaseMod.GetMonsterGroup() {
            @Override
            public MonsterGroup get() {
                return new MonsterGroup(new AbstractMonster[]{
                        (AbstractMonster) new Snecko(-230.0F, 15.0F),
                        (AbstractMonster) new Chosen(100.0F, 25.0F)
                });
            }
        });
    }

    public enum EncounterType {
        WEAK, STRONG, ELITE;
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
        BaseMod.encounterList=new ArrayList<>();

        addExordiumEncounters();
        addCityEncounters();
        addBeyondEncounters();
        addEncounter(TheEnding.id,  EncounterType.ELITE, "Aggressive", 1.0F,
                new BaseMod.GetMonsterGroup() {
                    @Override
                    public MonsterGroup get() {
                        return new MonsterGroup(new AbstractMonster[]{
                                (AbstractMonster)new GremlinNob(-280.0F, 20.0F),
                                (AbstractMonster)new BookOfStabbing(),(AbstractMonster)new SnakePlant(300.0F, 20.0F)
                        });
                    }
                });
        GremlinNob nob;

        addEncounter(TheEnding.id,  EncounterType.ELITE, "Mega_GremlinNob", 1.0F,
                new BaseMod.GetMonsterGroup() {

                    @Override
                    public MonsterGroup get() {

                        return new MonsterGroup(new AbstractMonster[]{

                                (AbstractMonster)new MegaGremlinNob(-280.0F, 20.0F),
                                                   });
                    }
                });





    }
}
