package com.behdavar.backservices.auth.entity.constant;

/**
 * @author Abbas Alishirvani
 */
public final class ClientEntityConstant extends AuthBaseEntityConstant {

    public static final String NAME = "CLIENT_DETAILS";
    public static final String TABLE_NAME = TBL_PREFIX + NAME;
    public static final String PRIMARY_KEY = PRIMARY_KEY_PREFIX + NAME + ID_SUFFIX;
    public static final String FOREIGN_KEY = FOREIGN_KEY_PREFIX + NAME + ID_SUFFIX;

    public static final String CLIENT_ID = "CLIENT_ID";

    public static final String INDEX_CLIENT_ID = INDEX_PREFIX + NAME + CLIENT_ID;
    public static final String UC_CLIENT_ID = UNIQUE_CONSTRAINT_PREFIX + NAME + CLIENT_ID;

    private ClientEntityConstant() {
    }
}
