package com.dormitory.constant;

public enum PropertyName {
	ID("id", "id"), NAME("����", "name"), PASSWORD("����", "password"), AUTH("���","authentication"),
    GENDER("�Ա�", "gender"), MAJOR("רҵ", "major"), GRADE("�꼶", "grade"), CLASS("�༶", "classNum"),
    BUILDING_NUM("����¥", "buildingNum");
    private String cnValue;
    private String enValue;
    PropertyName(String cnValue, String enValue) {
        this.cnValue = cnValue;
        this.enValue = enValue;
    }

    public final String getCnValue() {
        return cnValue;
    }

    public final String getEnValue() {
        return enValue;
    }
}
