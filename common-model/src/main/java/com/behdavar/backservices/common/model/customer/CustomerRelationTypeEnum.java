package com.behdavar.backservices.common.model.customer;

import java.util.Objects;

public enum CustomerRelationTypeEnum {
    OTHER(999, "نامشخص"),
    LAWYER(1, "وکیل"),
    AGENT(2, "نماینده"),
    SHAREHOLDER(3, "سهامدار"),
    CLIENT(5, "موکل"),
    MEMBER_OF_THE_BOARD(6, "عضو هیئت مدیره"),
    BOSS(7, "رئیس"),
    MANAGING_DIRECTOR(8, "مدیر عامل"),
    AUDITOR(9, "حسابرس"),
    INSPECTOR(10, "بازرس"),
    FOUNDER(11, "موسس"),;

    private final Integer typeId;
    private final String typeTitle;

    CustomerRelationTypeEnum(Integer typeId, String typeTitle) {
        this.typeTitle = typeTitle;
        this.typeId = typeId;
    }

    public static CustomerRelationTypeEnum findByTypeId(String relationType) {
        if (Objects.isNull(relationType) || !relationType.matches("\\d")) {
            return CustomerRelationTypeEnum.OTHER;
        }

        Integer i = Integer.valueOf(relationType);
        return findByTypeId(i);
    }

    public static CustomerRelationTypeEnum findByTypeId(Integer relationType) {
        for (CustomerRelationTypeEnum e : CustomerRelationTypeEnum.values()) {
            if (Objects.equals(relationType, e.getTypeId())) {
                return e;
            }
        }
        return CustomerRelationTypeEnum.OTHER;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public String getTypeTitle() {
        return typeTitle;
    }
}
