package com.behdavar.backservices.common.model.except;

public class BackServiceExceptionReason {

    public static final BackServiceExceptionReason UNKNOWN = new BackServiceExceptionReason("BA01", "خطای نا مشخص");
    public static final BackServiceExceptionReason ERROR_IN_INPUT_PARAMS = new BackServiceExceptionReason("BA02", "پارامترهای ورودی صحیح نمی باشد");
    public static final BackServiceExceptionReason ERROR_IN_DELETE = new BackServiceExceptionReason("BA03", "خطای در حذف اطلاعات از روی بانک اطلاعاتی");
    public static final BackServiceExceptionReason ERROR_IN_INSERT = new BackServiceExceptionReason("BA04", "خطا در درج اطلاعات بر روی بانک اطلاعاتی");
    public static final BackServiceExceptionReason ERROR_IN_UPDATE = new BackServiceExceptionReason("BA05", "خطا در به روز رسانی اطلاعات بر روی بانک اطلاعاتی");
    public static final BackServiceExceptionReason ERROR_IN_REST = new BackServiceExceptionReason("BA06", "خطا در اتصال به سرویس");
    public static final BackServiceExceptionReason ERROR_IN_WS = new BackServiceExceptionReason("BA07", "خطا در اتصال به وب سرویس");
    public static final BackServiceExceptionReason ERROR_IN_TOSAN_WS = new BackServiceExceptionReason("BA08", "خطا در برنامه توسن");
    public static final BackServiceExceptionReason ERROR_IN_SAMAT_WS = new BackServiceExceptionReason("BA08", "خطا در برنامه وب سرویس سمات");
    public static final BackServiceExceptionReason ERROR_IN_SAMAT_ETL = new BackServiceExceptionReason("BA08", "خطا در برنامه سرویس جمع آوری سمات");
    public static final BackServiceExceptionReason ERROR_IN_LOTUS_WS = new BackServiceExceptionReason("BA08", "خطا در برنامه وب سرویس لوتوس");

    private String code;
    private String description;

    public BackServiceExceptionReason(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
