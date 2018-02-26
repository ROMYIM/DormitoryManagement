package com.dormitory.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class Information implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String content;
	private Date sendDate;
	private Date deadline;

	public Information() {
		// TODO Auto-generated constructor stub
	}
	
	public Information(Integer id, Date sendDate, String content) {
		setContent(content);
		setId(id);
		setSendDate(sendDate);
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "content", columnDefinition = "text")
	public String getContent() {
		return content;
	}
	
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	
	@Column(name = "send_date", nullable = false, updatable = false, columnDefinition = "date")
	@Temporal(TemporalType.DATE)
	public Date getSendDate() {
		return sendDate;
	}
	
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	
	@Column(name = "deadline", columnDefinition = "date")
	@Temporal(TemporalType.DATE)
	public Date getDeadline() {
		return deadline;
	}

}
