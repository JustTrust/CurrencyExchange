package org.belichenko.a.currencyexchange;

/**
 * Default list of banks
 */
enum DefaultBanks {
    PRIVAT("Приватбанк","305299"),
    OSCHAD("Ощадбанк","300465"),
    EXIM("Укрэксимбанк","322313"),
    PUMB("ПУМБ","334851");

    private String name;
    private String mfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMfo() {
        return mfo;
    }

    public void setMfo(String mfo) {
        this.mfo = mfo;
    }

    DefaultBanks(String name, String mfo) {
        this.name = name;
        this.mfo = mfo;
    }
}
