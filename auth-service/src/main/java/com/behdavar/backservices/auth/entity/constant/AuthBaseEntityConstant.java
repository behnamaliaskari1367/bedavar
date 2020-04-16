package com.behdavar.backservices.auth.entity.constant;

/**
 * @author Abbas Alishirvani
 */
public abstract class AuthBaseEntityConstant {

    public static final String SCHEMA = "BEHDAVAR";
    static final String TBL_PREFIX = "BSTBL_";
    static final String PRIMARY_KEY_PREFIX = "PK_";
    static final String FOREIGN_KEY_PREFIX = "FK_";
    static final String ID_SUFFIX = "_ID";

    static final String INDEX_PREFIX = "IDXBSTBL";
    static final String UNIQUE_CONSTRAINT_PREFIX = "UKBSTBL";

    public static final String YES_NO_TYPE = "yes_no";

    public static final int VERSION_LENGTH = 5;
    public static final int USER_AUDIT_LENGTH = 100;

    AuthBaseEntityConstant() {
    }
}
