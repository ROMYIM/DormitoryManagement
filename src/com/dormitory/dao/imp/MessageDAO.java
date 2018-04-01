package com.dormitory.dao.imp;

import org.springframework.stereotype.Repository;

import com.dormitory.dao.IMessageDAO;
import com.dormitory.entity.Message;

/**
*@author:   yim
*@date:  2018��3��31������2:29:57
*@description:   
*/
@Repository("messageDAO")
public class MessageDAO extends BaseDAO<Message, Integer> implements IMessageDAO {

}
