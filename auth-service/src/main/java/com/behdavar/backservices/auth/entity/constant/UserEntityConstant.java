package com.behdavar.backservices.auth.entity.constant;

/**
 * @author Abbas Alishirvani
 */
public final class UserEntityConstant extends AuthBaseEntityConstant {

    public static final String NAME = "USER";
    public static final String TABLE_NAME = TBL_PREFIX + NAME;
    public static final String PRIMARY_KEY = PRIMARY_KEY_PREFIX + NAME + ID_SUFFIX;
    public static final String FOREIGN_KEY = FOREIGN_KEY_PREFIX + NAME + ID_SUFFIX;

    public static final String USER_NAME = "USER_NAME";

    public static final String INDEX_USER_NAME = INDEX_PREFIX + NAME + USER_NAME;
    public static final String UC_USER_NAME = UNIQUE_CONSTRAINT_PREFIX + NAME + USER_NAME;

    private UserEntityConstant() {
    }
}
