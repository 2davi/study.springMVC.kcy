package kr.letech.study.sample.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SampleDAO {

	String selectNow(); 
}
