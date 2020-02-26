package miny.dao;

import java.sql.SQLException;
import java.util.List;

import miny.vo.TacoVO;

public interface TacoDAO {
	int insertTacolab(TacoVO vo) throws SQLException;
	int updateTacolab(TacoVO vo) throws SQLException;
	List<TacoVO> getTacolab() throws SQLException;
}
