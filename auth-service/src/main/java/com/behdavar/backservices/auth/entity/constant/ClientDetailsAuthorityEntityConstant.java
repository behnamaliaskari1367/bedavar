package com.behdavar.backservices.auth.entity.constant;

/**
 * @author Abbas Alishirvani
 */
public class ClientDetailsAuthorityEntityConstant extends AuthBaseEntityConstant {

    public static final String NAME = "CLIENT_DETAILS_AUTHORITY";
    public static final String TABLE_NAME = TBL_PREFIX + NAME;
    public static final String PRIMARY_KEY = PRIMARY_KEY_PREFIX + NAME + ID_SUFFIX;
    public static final String FOREIGN_KEY = FOREIGN_KEY_PREFIX + NAME + ID_SUFFIX;

    private ClientDetailsAuthorityEntityConstant() {
    }
}
