package com.dormitory.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.dormitory.constant.MessageStatus;

/**
*@author:   yim
*@date:  2018��3��31������1:30:30
*@description:   
*/
@Entity
@Table(name = "message")
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String senderId;
	private String receiverId;
	private MessageStatus status;
	private String content;
	private String sendDate;
	

	public Message() {
		super();
	}

	public Message(String senderId, String content, String sendDate) {
		super();
		this.senderId = senderId;
		this.content = content;
		this.sendDate = sendDate;
	}

	public Message(String senderId, String receiverId, String content, String sendDate) {
		super();
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.content = content;
		this.sendDate = sendDate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "sender_id", nullable = false, updatable = false)
	public String getSenderId() {
		return senderId;
	}
	
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	
	@Column(name = "receiver_id", nullable = false, updatable = false)
	public String getReceiverId() {
		return receiverId;
	}
	
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	
	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	public MessageStatus getStatus() {
		return status;
	}
	
	public void setStatus(MessageStatus status) {
		this.status = status;
	}
	
	@Column(name = "content", nullable = false)
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "send_date", nullable = false, updatable = false, columnDefinition = "datetime")
	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
}
