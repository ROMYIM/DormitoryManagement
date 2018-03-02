package com.dormitory.constant;

public enum ResultType {
	SUCCESS(000, "success", "�����ɹ�"), NO_USER(101, "failed", "û�и��û�"), ERR_PASSWORD(102, "failed", "�������"), 
	AL_REGISTER(103, "failed", "�Ѿ�ע��"),ERR_LOGIN(104, "failed", "��¼����"), ERR_QUERY(200, "failed", "��ѯʧ��"), 
	ERR_DORMITORY(201, "failed", "��ѯ����ʧ��"), ERR_STUDENT(202, "failed", "��ѯѧ��ʧ��"),ERR_VIOLATION(203, "failed", "��ѯΥ���¼ʧ��"), 
	ERR_REPAIR(204, "failed", "��ѯά�޼�¼ʧ��"), ERR_DORADMIN(205, "failed", "��ѯ�������Աʧ��"),ERR_NOTICE(206, "failed", "��ѯ����ʧ��"),
	ERR_UNKONOWN(500, "failed", "δ֪����"), ERR_AUTH(105, "failed", "��ݲ�ƥ��");
	
	private final int code;
	private final String result;
	private final String message;
	
	private ResultType(int code, String result, String message) {
		// TODO Auto-generated constructor stub
		this.code = code;
		this.result = result;
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getResult() {
		return result;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("code:").append(getCode()).append(",").append("result:").
		append(getResult()).append(",").append("message:").append(getMessage());
		return stringBuffer.toString();
	}
}
