package com.example.pachong.utils;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.INTEGER)
public class TimerHandler extends BaseTypeHandler<String> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
    ps.setString(i, parameter);
  }

  @Override
  public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String returnValue = columnName;
		try {
			int s = (int) rs.getInt(columnName);
			int s1=s%60;
			int m=s/60;
			int m1=m%60;
			int h=m/60;
			returnValue = (h>=10?(h+""):("0"+h))+":"+(m1>=10?(m1+""):("0"+m1))+":"+(s1>=10?(s1+""):("0"+s1));
		} catch (Exception e) {}
		return returnValue;
//    return rs.getString(columnName);
  }

  @Override
  public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return rs.getString(columnIndex);
  }

  @Override
  public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    String returnValue = String.valueOf(columnIndex);
		try {
			int s = (int) cs.getInt(columnIndex);
			int s1=s%60;
			int m=s/60;
			int m1=m%60;
			int h=m/60;
			returnValue = (h>=10?(h+""):("0"+h))+":"+(m1>=10?(m1+""):("0"+m1))+":"+(s1>=10?(s1+""):("0"+s1));
		} catch (Exception e) {}
		return returnValue;
//    return cs.getString(columnIndex);
  }

//	@Override
//	public void setParameter(PreparedStatement ps, int i, Object parameter,String jdbcType) throws SQLException {
//		// TODO Auto-generated method stub
//	}
//
//	@Override
//	public Object getResult(ResultSet rs, String columnName) throws SQLException {
//		String returnValue = columnName;
//		try {
//			int s = (int) rs.getInt(columnName);
//			int s1=s%60;
//			int m=s/60;
//			int m1=m%60;
//			int h=m/60;
//			returnValue = (h>=10?(h+""):("0"+h))+":"+(m1>=10?(m1+""):("0"+m1))+":"+(s1>=10?(s1+""):("0"+s1));
//		} catch (Exception e) {}
//		return returnValue;
//	}
//
//	@Override
//	public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
//		String returnValue = String.valueOf(columnIndex);
//		try {
//			int s = (int) cs.getInt(columnIndex);
//			int s1=s%60;
//			int m=s/60;
//			int m1=m%60;
//			int h=m/60;
//			returnValue = (h>=10?(h+""):("0"+h))+":"+(m1>=10?(m1+""):("0"+m1))+":"+(s1>=10?(s1+""):("0"+s1));
//		} catch (Exception e) {}
//		return returnValue;
//	}
//
//	@Override
//	public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public Object valueOf(String s) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
