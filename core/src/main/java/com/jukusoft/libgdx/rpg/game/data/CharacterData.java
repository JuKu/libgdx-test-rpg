package com.jukusoft.libgdx.rpg.game.data;

/**
 * Created by Justin on 08.02.2017.
 */
public class CharacterData {

    protected float health = 100;
    protected float maxHealth = 200;

    protected float mana = 20;
    protected float maxMana = 200;

    public CharacterData() {
        //
    }

    public float getHealth () {
        return this.health;
    }

    public float getMaxHealth () {
        return this.maxHealth;
    }

    public float getMana () {
        return this.mana;
    }

    public float getMaxMana () {
        return this.maxMana;
    }

}
