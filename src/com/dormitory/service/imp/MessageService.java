package com.dormitory.service.imp;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dormitory.constant.MessageStatus;
import com.dormitory.dao.IMessageDAO;
import com.dormitory.entity.Message;
import com.dormitory.service.IMessageService;

/**
*@author:   yim
*@date:  2018年3月31日下午2:46:46
*@description:   
*/
@Service("messageService")
public class MessageService implements IMessageService {
	
	@Resource(name = "messageDAO")
	private IMessageDAO messageDAO;

	@Override
	public List<Message> findUnSendMessages(String receiverId) {
		// TODO Auto-generated method stub
		String hql = "select new Message(m.senderId, m.content, m.sendDate) from Message m where m.receiverId = ? and m.status = ?";
		return (List<Message>) messageDAO.queryByHQL(hql, receiverId, MessageStatus.NOT_SEND);
	}

	@Override
	public List<Message> findMessagsBySendDate(String senderId, String receiverId, Date sendDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addMessage(Message message) {
		// TODO Auto-generated method stub
		messageDAO.save(message);
	}

}
