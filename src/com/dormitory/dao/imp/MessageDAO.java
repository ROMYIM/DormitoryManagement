package com.dormitory.dao.imp;

import org.springframework.stereotype.Repository;

import com.dormitory.dao.IMessageDAO;
import com.dormitory.entity.Message;

/**
*@author:   yim
*@date:  2018年3月31日下午2:29:57
*@description:   
*/
@Repository("messageDAO")
public class MessageDAO extends BaseDAO<Message, Integer> implements IMessageDAO {

}
