package org.belichenko.a.currencyexchange;

/**
 * Default list of basic currency
 */
enum DefaultCurrency {
    UAH("980",1.0f),
    USD("840",25.0f),
    EUR("978",27.1f),
    RUR("643",66.5f);

    private String code;
    private float middleCourse;

    DefaultCurrency(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public float getMiddleCourse() {
        return middleCourse;
    }

    public void setMiddleCourse(float middleCourse) {
        this.middleCourse = middleCourse;
    }

    DefaultCurrency(String code, float middleCourse) {
        this.code = code;
        this.middleCourse = middleCourse;
    }
}
