package com.dormitory.dao.imp;

import org.springframework.stereotype.Repository;

import com.dormitory.dao.IStudentDAO;
import com.dormitory.entity.Student;

@Repository("studentDAO")
public class StudentDAO extends BaseDAO<Student, String> implements IStudentDAO {

}
