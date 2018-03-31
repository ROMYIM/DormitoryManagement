package com.dormitory.service;
/**
*@author:   yim
*@date:  2018��3��31������2:35:41
*@description:   
*/

import java.util.Date;
import java.util.List;

import com.dormitory.entity.Message;

public interface IMessageService {

	List<Message> findUnSendMessages(String receiverId);
	
	List<Message> findMessagsBySendDate(String senderId, String receiverId, Date sendDate);
	
	void addMessage(Message message);
}
