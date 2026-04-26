package org.example.webaimanagement.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.webaimanagement.pojo.EmpExpr;

import java.util.List;

@Mapper
public interface EmpExprMapper {


    void insertBatch(List<EmpExpr> empList);

    void deleteByEmpId(List<Integer> empIds);
}
