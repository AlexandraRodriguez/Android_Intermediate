package com.example.ale.pokedexapp;


import android.graphics.Bitmap;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.parse.ParseObject;

@Table(name = "pokemon")
public class Pokemon extends Model {
    private String id;
    @Column(name = "name", index = true)
    private String name;
    @Column(name = "number")
    private int number;
    @Column(name = "image")
    private Bitmap image;

   @Column(name = "type")
    private String type;
    @Column(name = "description")
    private String description;
    @Column(name = "specie")
    private String specie;
    @Column(name = "abilities")
    private String abilities;
    @Column(name = "eggGroups")
    private String eggGroups;

    @Column(name = "weight")
    private double weight;
    @Column(name = "height")
    private double height;
    @Column(name = "hp")
    private int hp;
    @Column(name = "attack")
    private int attack;
    @Column(name = "defense")
    private int defense;
    @Column(name = "speed")
    private int speed;
    @Column(name = "spAttack")
    private int spAttack;
    @Column(name = "spDefense")
    private int spDefense;

    public Pokemon() {
        super();
        type = "";
        abilities = "";
        eggGroups = "";
        height = 0.0;
        weight = 0.0;
    }

    public void saveLocally(){
        save();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String mType) {
        if (type.equals(""))
            type = mType;
        else
            type = type + ", " + mType;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public String getAbilities() {
        return abilities;
    }

    public void setAbilities(String ability) {
        if (abilities.equals(""))
            abilities = ability;
        else
            abilities = abilities + ", " + ability;
    }

    public String getEggGroups() {
        return eggGroups;
    }

    public void setEggGroups(String eggGroup) {
        if (eggGroups.equals(""))
            eggGroups = eggGroup;
        else
            eggGroups = eggGroups + ", " + eggGroup;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(int mWeight) {
        weight = mWeight / 10;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(int mHeight) {
        height = mHeight / 10;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpAttack() {
        return spAttack;
    }

    public void setSpAttack(int spAttack) {
        this.spAttack = spAttack;
    }

    public int getSpDefense() {
        return spDefense;
    }

    public void setSpDefense(int spDefense) {
        this.spDefense = spDefense;
    }
}
