package org.lger.demo;

enum DataBaseUser {
    URL("jdbc:mysql://localhost:3306/questionnaire"), USER("zzmdb"), PWD("zzm3305");
    private String dbParameter;

    private DataBaseUser(String dbParameter) {
        this.dbParameter = dbParameter;
    }

    public String getDbParameter() {
        return dbParameter;
    }

    public void setDbParameter(String dbParameter) {
        this.dbParameter = dbParameter;
    }

    @Override
    public String toString() {
        return dbParameter;
    }
}
