package com.dormitory.dao.imp;

import org.springframework.stereotype.Repository;

import com.dormitory.dao.IBillDAO;
import com.dormitory.entity.Bill;

@Repository("billDAO")
public class BillDAO extends BaseDAO<Bill, Integer> implements IBillDAO {


}
