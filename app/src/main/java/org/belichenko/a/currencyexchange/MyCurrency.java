package org.belichenko.a.currencyexchange;

import java.util.Random;

/**
 * Currency
 */
class MyCurrency {
    private String name;
    private String id;
    private float buyCource;
    private float sellCource;

    public MyCurrency(String name) {
        this.name = String.valueOf(name).toUpperCase();
    }

    public MyCurrency(String name, String id, float middleCource) {

        this.name = String.valueOf(name).toUpperCase();
        this.id = id;

        // pseudo generate buy and sell courses by the middle course
        float delta = new Random().nextFloat();
        if (middleCource < 30.0f) {
            this.buyCource = middleCource - delta;
            this.sellCource = middleCource + delta;
        } else {
            this.buyCource = middleCource - delta * 1.8f;
            this.sellCource = middleCource + delta * 2.2f;
        }
    }

    public MyCurrency(String name, float buyCource, float sellCource) {

        this(name);
        this.buyCource = buyCource;
        this.sellCource = sellCource;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getBuyCource() {
        return buyCource;
    }

    public void setBuyCource(float buyCource) {
        this.buyCource = buyCource;
    }

    public float getSellCource() {
        return sellCource;
    }

    public void setSellCource(float sellCource) {
        this.sellCource = sellCource;
    }

    @Override
    public String toString() {
        return getName()+"  "+String.format("%.3f", getBuyCource())+"  "+String.format("%.3f", getSellCource());
    }
}
