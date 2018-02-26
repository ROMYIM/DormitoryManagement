package com.dormitory.constant;

public enum PropertyName {
	ID("id", "id"), NAME("名字", "name"), PASSWORD("密码", "password"), AUTH("身份","authentication"),
    GENDER("性别", "gender"), MAJOR("专业", "major"), GRADE("年级", "grade"), CLASS("班级", "classNum"),
    BUILDING_NUM("宿舍楼", "buildingNum");
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
