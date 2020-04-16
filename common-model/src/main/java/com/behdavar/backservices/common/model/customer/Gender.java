package com.behdavar.backservices.common.model.customer;

public enum Gender {
    MALE(1),
    FEMALE(2);

    private final int samatType;

    Gender (int samatType) {
        this.samatType = samatType;
    }

    public int getSamatType() {
        return samatType;
    }

    public static Gender searchForSamatType(int samatCode){
        for (Gender g : Gender.values()) {
            if(g.getSamatType()==samatCode){
                return g;
            }
        }
        return null;
    }
}
