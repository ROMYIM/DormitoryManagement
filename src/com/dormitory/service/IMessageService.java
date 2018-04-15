package com.dormitory.service;
/**
*@author:   yim
*@date:  2018年3月31日下午2:35:41
*@description:   
*/

import java.util.List;

import com.dormitory.entity.Message;

public interface IMessageService {

	List<Message> findUnSendMessagesGroupBySender(String receiverId);
	
	List<Message> findMessagsBySendDate(String targetId, String ownerId, String sendDate);
	
	void addMessage(Message message);
}
