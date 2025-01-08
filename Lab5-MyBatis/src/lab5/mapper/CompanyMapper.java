package lab5.mapper;

import java.util.List;
//import java.util.Map;

import lab5.Department;
import lab5.Employee;

//import model.Comment;

public interface CompanyMapper {
    Department selectDeptByName(String deptname);
    
    List<Employee> selectEmpsByDeptNo(int deptno);
    
    Department selectDeptAndEmpsByDeptName(String deptName);
    
//    Comment selectCommentByPrimaryKey(long commentNo);
//    
//    List<Comment> selectCommentByCondition(Map<String, Object> condition);
//    
//    int insertComment(Comment comment);   
// 
//    int updateComment(Comment comment);
//    
//    int deleteComment(long commentNo);
//
//    int deleteAllComments();
}
