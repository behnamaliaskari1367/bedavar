package com.behdavar.backservices.auth.entity.constant;

/**
 * @author Abbas Alishirvani
 */
public final class AuthorityEntityConstant extends AuthBaseEntityConstant {

    public static final String NAME = "AUTHORITY";
    public static final String TABLE_NAME = TBL_PREFIX + NAME;
    public static final String PRIMARY_KEY = PRIMARY_KEY_PREFIX + NAME + ID_SUFFIX;
    public static final String FOREIGN_KEY = FOREIGN_KEY_PREFIX + NAME + ID_SUFFIX;

    public static final String COLUMN_NAME = "NAME";

    public static final String INDEX_NAME = INDEX_PREFIX + NAME + COLUMN_NAME;
    public static final String UC_NAME = UNIQUE_CONSTRAINT_PREFIX + NAME + COLUMN_NAME;

    private AuthorityEntityConstant() {
    }
}
