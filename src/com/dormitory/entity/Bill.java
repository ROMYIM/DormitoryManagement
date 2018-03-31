package com.dormitory.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.dormitory.constant.BillType;

@Entity
@Table(name = "bill")
public class Bill implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 
	private Integer id;
	
	@NotNull
	@DateTimeFormat(style = "yyyy-mm-dd")
	private Date payDate;
	
	@NotNull
	@Valid
	private Dormitory dormitory;
	
	@NotNull
	private BillType type;
	
	@NotNull
	@Digits(fraction = 2, integer = 3)
	@Min(value = 0)
	@NumberFormat(style = Style.CURRENCY)
	private Float payMoney;

	public Bill() {
		// TODO Auto-generated constructor stub
	}
	
	public Bill(Date payDate, BillType type, Float payMoney, Dormitory dormitory) {
		setPayDate(payDate);
		setType(type);
		setPayMoney(payMoney);
		setDormitory(dormitory);
	}
	
	public Bill(Integer id, Date payDate, BillType type, Float payMoney) {
		setId(id);
		setPayDate(payDate);
		setType(type);
		setPayMoney(payMoney);
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
	
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	
	@Column(name = "pay_date", nullable = false, updatable = false, columnDefinition = "date")
	@Temporal(TemporalType.DATE)
	public Date getPayDate() {
		return payDate;
	}
	
	public void setDormitory(Dormitory dormitory) {
		this.dormitory = dormitory;
	}
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "dormitory_id")
	public Dormitory getDormitory() {
		return dormitory;
	}
	
	public void setType(BillType type) {
		this.type = type;
	}
	
	@Column(name = "type", nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	public BillType getType() {
		return type;
	}
	
	public void setPayMoney(Float payMoney) {
		this.payMoney = payMoney;
	}
	
	@Column(name = "pay_money", nullable = false, updatable = false, columnDefinition = "float default 0.00")
	public Float getPayMoney() {
		return payMoney;
	}

}
