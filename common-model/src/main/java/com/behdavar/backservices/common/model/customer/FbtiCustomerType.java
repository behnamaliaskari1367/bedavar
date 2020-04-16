package com.behdavar.backservices.common.model.customer;

public enum FbtiCustomerType {
    Real("IND.IND", CustomerType.Real),
    Legal("COR.COR", CustomerType.Legal);

    private final CustomerType customerType;
    private final String fbtiCode;

    FbtiCustomerType(String fbtiCode, CustomerType customerType) {
        this.fbtiCode = fbtiCode;
        this.customerType = customerType;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public String getFbtiCode() {
        return fbtiCode;
    }

    public static FbtiCustomerType searchForFbtiCode(String fbtiCode){
        for (FbtiCustomerType fbtiCustomerType : FbtiCustomerType.values()) {
            if(fbtiCustomerType.getFbtiCode().equals(fbtiCode)){
                return fbtiCustomerType;
            }
        }
        return null;
    }
}
