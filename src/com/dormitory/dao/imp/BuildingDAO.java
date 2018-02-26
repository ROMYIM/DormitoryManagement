package com.dormitory.dao.imp;

import org.springframework.stereotype.Repository;

import com.dormitory.dao.IBuildingDAO;
import com.dormitory.entity.Building;

@Repository("buildingDAO")
public class BuildingDAO extends BaseDAO<Building, Integer> implements IBuildingDAO {


}
