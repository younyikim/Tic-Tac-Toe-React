package persistence.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import persistence.dao.AlarmDAO;
import service.dto.AlarmDTO;
import service.dto.MsgDTO;

public class AlarmDAOImpl implements AlarmDAO {
	
	private JDBCUtil jdbcUtil = null;

	public AlarmDAOImpl() {
		jdbcUtil = new JDBCUtil();
	}

	/**
	 * AlarmList 반환
	 */
	public List<AlarmDTO> getAlarmList(String userId) {
		String query = "SELECT ALARMLISTNO, POST_ID, COMMENT_ID " + 
						"FROM ALRAMLIST " + 
						"WHERE USER_ID = ?";
		jdbcUtil.setSqlAndParameters(query, new Object[] { userId }); // JDBCUtil 에 query 설정

		try {
			ResultSet rs = jdbcUtil.executeQuery(); // query 문 실행
			List<AlarmDTO> list = new ArrayList<AlarmDTO>(); // MsgDTO 객체들을 담기위한 list 객체
			while (rs.next()) {
				AlarmDTO dto = new AlarmDTO(); // 하나의 MsgDTO 객체 생성 후 정보 설정
				dto.setAlarmNo(rs.getString("ALARMLISTNO"));
				dto.setAlarmNo(rs.getString("POST_ID"));
				dto.setAlarmNo(rs.getString("COMMENT_ID"));
				list.add(dto);
			}
			return list;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close(); // ResultSet, PreparedStatement, Connection 반환
		}
		return null;
	}

	public int deleteAlarm(String alarmNo) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM ALARMLIST WHERE ALARMLISTNO = ?";
		jdbcUtil.setSqlAndParameters(sql, new Object[] { alarmNo }); // JDBCUtil에 delete문과 매개 변수 설정

		try {
			int result = jdbcUtil.executeUpdate(); // delete 문 실행
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close(); // resource 반환
		}
		return 0;
	}
}

/*
2.hobbyDAO가 필요없다. 사용자가 직접 추가하는 게 아니라 있는 hobby에서 골라쓰는 형식이라 필요 X, 사용자가 가입 시에 정해진 목록 중에 취미를 다중선택해서 저장하는데 이때 DB에 저장하려면 
메소드를 따로 만들어야하는지 or createUser메소드에서 배열로 받은 hobbyList를 추가할 수 있는지?
   */