package persistence.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import persistence.dao.ClubDAO;
import persistence.dao.ConnectionManager;
import persistence.dao.impl.JDBCUtil;
import service.dto.ClubDTO;
import service.dto.UserDTO;
import persistence.dao.impl.AppException;

public class ClubDAOImpl implements ClubDAO {
   private JDBCUtil jdbcUtil = null;
   private static ConnectionManager connMan = new ConnectionManager();
   static Connection conn = null;

   public ClubDAOImpl() {
      jdbcUtil = new JDBCUtil(); // JDBCUtil 媛앹껜  깮 꽦
   }

   /**
    * Club  깮 꽦 븯湲  (11/29  솗 씤)
    * 
    * @return
    */
   public int createClub(ClubDTO club, String[] hobby) {
//      Connection conn = null;
      PreparedStatement pStmt = null;
      String sql = "INSERT INTO Club (CLUB_ID, CLUBNAME, STARTDATE, REGION, NUMOFMEMBERS, MAXOFMEMBERS, "
				+ "CHAIR_ID) VALUES (?, ?, SYSDATE, ?, 1, ?, ?) ";


      String sql2 = "INSERT INTO CLUB_CATEGORY VALUES (?, ?)";

      try {
         // sql1
         conn = connMan.getConnection();

//             conn.setAutoCommit(false);
         pStmt = conn.prepareStatement(sql);
         pStmt.setString(1, club.getClubId());
         pStmt.setString(2, club.getName());
         pStmt.setString(3, club.getRegion());
         pStmt.setInt(4, club.getMaxNumMembers());
         pStmt.setString(5, club.getChairId());

         if (pStmt.executeUpdate() != 1)
            throw new AppException();
         pStmt.close();

         // sql2
         for (int i = 0; i < 2; i++) {
            pStmt = conn.prepareStatement(sql2);
            pStmt.setString(1, hobby[i]);
            pStmt.setString(2, club.getClubId());

            if (pStmt.executeUpdate() != 1)
               throw new AppException();
            if (i < 1) {
               pStmt.close();
            }
         }

         conn.commit();

         return 1;
      } catch (Exception ex) {
         if (ex instanceof AppException) {
            try {
               conn.rollback();
            } catch (SQLException e) {
               e.printStackTrace();
            }
            System.out.println(" 듃 옖 옲 뀡 씠 rollback  릺 뿀 뒿 땲 떎.");
         }
         ex.printStackTrace();
      } finally {
         try {
            conn.setAutoCommit(true); // auto-commit mode  꽕 젙
         } catch (SQLException ex) {
            ex.printStackTrace();
         }
         if (pStmt != null) {
            try {
               pStmt.close();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
         }
      }
      return 0;
   }

   /**
    * CLUB 뿉  깉濡쒖슫  궗 슜 옄 異붽  븯湲  (11/29  솗 씤)
    */
   public int insertUser(String userId, String clubId) {
      String sql = "INSERT INTO USER_CLUB (CLUB_ID, USER_ID, REGISTERDATE) VALUES (?, ?, SYSDATE)";

      jdbcUtil.setSqlAndParameters(sql, new Object[] { clubId, userId }); // JDBCUtil에 update문과 매개 변수 설정
      try {
         if (jdbcUtil.executeUpdate() != 1) // update 문 실행
            throw new AppException();

         return 1;
      } catch (Exception ex) {
         if (ex instanceof AppException) {
            jdbcUtil.rollback();
            System.out.println("트랜잭션이 rollback 되었습니다.");
         }
      } finally {
         jdbcUtil.commit();
         jdbcUtil.close(); // resource 반환
      }
      return 0;
   }

   /**
    * CLUB에 사용자 삭제하기
    */
   public int removeUser(String userId, String clubId) {
      String sql = "DELETE FROM USER_CLUB " + "WHERE CLUB_ID = ? AND USER_ID = ?";

      jdbcUtil.setSqlAndParameters(sql, new Object[] { clubId, userId });
      try {
         if (jdbcUtil.executeUpdate() != 1)
            throw new AppException();
         jdbcUtil.commit();
         return 1;
      } catch (Exception ex) {
         if (ex instanceof AppException) {
            jdbcUtil.rollback();
            System.out.println("트랜잭션이 rollback 되었습니다.");
         }
      } finally {
         jdbcUtil.commit();
         jdbcUtil.close(); // resource 반환
      }
      return 0;
   }
/*
   public int increaseClubMemberCnt(String clubId) {
      String sql = "UPDATE Club " + "SET NUMOFMEMBERS=NVL(NUMOFMEMBERS,0) + 1 " + "WHERE CLUB_ID = ?";

      jdbcUtil.setSqlAndParameters(sql, new Object[] { clubId });

      try {
         if (jdbcUtil.executeUpdate() != 1)
            throw new AppException();
         jdbcUtil.commit();
         return 1;
      } catch (Exception ex) {
         jdbcUtil.rollback();
         ex.printStackTrace();
      } finally {
         jdbcUtil.commit();
         jdbcUtil.close();
      }
      return 0;
   }
   
   public int decreaseClubMemberCnt(String clubId) {
      String sql = "UPDATE Club " + "SET NUMOFMEMBERS=NVL(NUMOFMEMBERS,0) - 1 " + "WHERE CLUB_ID = ?";

      jdbcUtil.setSqlAndParameters(sql, new Object[] { clubId });

      try {
         if (jdbcUtil.executeUpdate() != 1)
            throw new AppException();
         jdbcUtil.commit();
         return 1;
      } catch (Exception ex) {
         jdbcUtil.rollback();
         ex.printStackTrace();
      } finally {
         jdbcUtil.commit();
         jdbcUtil.close();
      }
      return 0;
   }
*/
   

   public int update(ClubDTO club) {
      String sql = "UPDATE Club " + "SET REGION=?, CHARI_ID=? " + "WHERE CLUB_ID = ?";
      Object[] param = new Object[] { club.getRegion(), club.getChairId(),club.getClubId() };
      jdbcUtil.setSqlAndParameters(sql, param);
      try {
         int result = jdbcUtil.executeUpdate(); // update 臾   떎 뻾
         return result;
      } catch (Exception ex) {
         jdbcUtil.rollback();
         ex.printStackTrace();
      } finally {
         jdbcUtil.commit();
         jdbcUtil.close(); // resource 諛섎궔
      }
      return 0;
   }

   /**
    * CLUB 쓽  쉶 옣 쓣 蹂 寃  (11/29  솗 씤)
    */
   public int updateChair(ClubDTO club) {
      String sql = "UPDATE CLUB " + "SET CHAIR_ID= ? " + "WHERE CLUB_ID=?";
      Object[] param = new Object[] { club.getChairId(), club.getClubId() };
      jdbcUtil.setSqlAndParameters(sql, param);

      try {
         int result = jdbcUtil.executeUpdate(); // update 臾   떎 뻾
         return result;
      } catch (Exception ex) {
         jdbcUtil.rollback();
         ex.printStackTrace();
      } finally {
         jdbcUtil.commit();
         jdbcUtil.close(); // resource 諛섎궔
      }
      return 0;
   }

   /**
    * CLUB 쓣  궘 젣 (11/29  솗 씤)
    */
   public int delete(String clubId) {
      PreparedStatement pStmt = null;
      
      String sql1 = "DELETE FROM USER_CLUB WHERE CLUB_ID = ?";
      
      String sql2 = "DELETE FROM BOARD WHERE CLUB_ID = ?";
      
      String sql3 = "DELETE FROM CLUB WHERE CLUB_ID = ?";
   
      try {
         conn = connMan.getConnection();
         pStmt = conn.prepareStatement(sql1);
         pStmt.setString(1, clubId);
         
         if (pStmt.executeUpdate() != 1)
            throw new AppException();
         pStmt.close();
         
         pStmt = conn.prepareStatement(sql2);
         pStmt.setString(1, clubId);
         
         if (pStmt.executeUpdate() != 1)
            throw new AppException();
         pStmt.close();
         
         pStmt = conn.prepareStatement(sql3);
         pStmt.setString(1, clubId);
         
         if (pStmt.executeUpdate() != 1)
            throw new AppException();
         
         conn.commit();
         
         return 1;
      } catch (Exception ex) {
         jdbcUtil.rollback();
         ex.printStackTrace();
      } finally {
         jdbcUtil.commit();
         jdbcUtil.close(); // resource 諛섎궔
      }
      return 0;
   }

   /* 吏  뿭 留ㅼ묶 */
   public ClubDTO regionMatching(UserDTO user) {
      String sql = "SELECT c.CLUB_ID AS ID, CLUBNAME, STARTDATE, CHAIR_ID, c.REGION AS CLUB_REGION "
            + "FROM Club c LEFT OUTER JOIN User u ON c.region = u.region " + "WHERE u.USER_ID = ?";
      jdbcUtil.setSqlAndParameters(sql, new Object[] { user.getUserId() });

      ClubDTO club = null;
      try {
         ResultSet rs = jdbcUtil.executeQuery();
         if (rs.next()) {
            club = new ClubDTO(rs.getString("ID"), rs.getString("CLUBNAME"), rs.getDate("STARTDATE"),
                  rs.getString("CHAIR_ID"), rs.getString("CLUB_REGION"));
         }
      } catch (Exception ex) {
         ex.printStackTrace();
      } finally {
         jdbcUtil.close(); // resource  뛾 룇瑗   꼶
      }
      return club;
   }

   /**
    * club 李얘린
    */
   public ClubDTO findClub(String clubId) {
      String sql = "SELECT c.CLUB_ID , CLUBNAME,  STARTDATE, NUMOFMEMBERS, MAXOFMEMBERS, CHAIR_ID, u.name As chairName, c.region AS region, c.POPULARITY AS CLUB_POPULARITY "
            + "FROM Club c LEFT OUTER JOIN USERINFO u ON c.CHAIR_ID = u.USER_ID  " + "WHERE c.CLUB_ID=? ";
      jdbcUtil.setSqlAndParameters(sql, new Object[] { clubId });
      ClubDTO club = null;
      try {
         ResultSet rs = jdbcUtil.executeQuery();
         if (rs.next()) {
            club = new ClubDTO(clubId, rs.getString("CLUBNAME"), rs.getString("CHAIR_ID"),
                  rs.getInt("NUMOFMEMBERS"), rs.getInt("MAXOFMEMBERS"), rs.getDate("STARTDATE"),
                  rs.getString("chairName"), rs.getString("region"), rs.getString("CLUB_POPULARITY"));
            return club;
         }
      } catch (Exception ex) {
         ex.printStackTrace();
      } finally {
         jdbcUtil.close();
      }
      return null;
   }

   /**
    * CLUB 由ъ뒪 듃瑜  諛섑솚
    */
   public List<ClubDTO> findClubList() {
      String sql = "SELECT C.CLUB_ID AS ID, CLUBNAME, STARTDATE, CHAIR_ID, u.name As chairName, "
            + "COUNT(u.USER_ID) AS NUMOFMEMBERS, POPUALRITY "
            + "FROM Club c LEFT OUTER JOIN USERINFO u ON c.CHAIR_ID = u.USER_ID  "
            + "GROUP BY C.CLUB_ID, CLUBNAME, STARTDATE, CHAIR_ID, c.POPUALRITY, u.name, POPUALRITY "
            + "ORDER BY CLUBNAME";
      jdbcUtil.setSqlAndParameters(sql, null);

      try {
         ResultSet rs = jdbcUtil.executeQuery();
         List<ClubDTO> clubList = new ArrayList<ClubDTO>();
         while (rs.next()) {
            ClubDTO club = new ClubDTO(rs.getString("ID"), rs.getString("CLUBNAME"), rs.getString("CHAIR_ID"),
                  rs.getInt("NUMOFMEMBERS"), rs.getDate("STARTDATE"), rs.getString("chairName"),
                  rs.getString("POPUALRITY"));
            clubList.add(club);
         }
         return clubList;

      } catch (Exception ex) {
         ex.printStackTrace();
      } finally {
         jdbcUtil.close(); // resource  뛾 룇瑗   꼶
      }
      return null;
   }

   /**
    * club 씠 議댁옱 븯 뒗吏   솗 씤
    */
   public boolean existingClub(String clubId) {
      String sql = "SELECT count(*) FROM Club WHERE CLUB_ID=?";
      jdbcUtil.setSqlAndParameters(sql, new Object[] { clubId });

      try {
         ResultSet rs = jdbcUtil.executeQuery();
         if (rs.next()) {
            int count = rs.getInt(1);
            return (count == 1 ? true : false);
         }
      } catch (Exception ex) {
         ex.printStackTrace();
      } finally {
         jdbcUtil.close();
      }
      return false;
   }

   

   public List<String> findClubListbyMBTI(String mbti) {
      System.out.println(mbti);
      String sql = "SELECT CLUB_ID " + "FROM CLUB " + "WHERE mbti = ?";
      jdbcUtil.setSqlAndParameters(sql, new Object[] { mbti });

      try {
         ResultSet rs = jdbcUtil.executeQuery();
         List<String> findClubListbyMBTI = new ArrayList<String>();
         while (rs.next()) {
            System.out.println(rs.getString("CLUB_ID"));
            findClubListbyMBTI.add(rs.getString("CLUB_ID"));
         }
         return findClubListbyMBTI;

      } catch (Exception ex) {
         ex.printStackTrace();
      } finally {
         jdbcUtil.close();
      }
      return null;
   }

   public List<String> findClubListbyRegion(String region) {
      System.out.println(region);
      String sql = "SELECT CLUB_ID " + "FROM CLUB " + "WHERE region = ?";
      jdbcUtil.setSqlAndParameters(sql, new Object[] { region });

      try {
         ResultSet rs = jdbcUtil.executeQuery();
         List<String> findClubListbyRegion = new ArrayList<String>();
         while (rs.next()) {
            System.out.println(rs.getString("CLUB_ID"));
            findClubListbyRegion.add(rs.getString("CLUB_ID"));
         }
         return findClubListbyRegion;

      } catch (Exception ex) {
         ex.printStackTrace();
      } finally {
         jdbcUtil.close();
      }
      return null;
   }

   public List<String> findUsersClubIDList(String userId) {
      String sql = "SELECT CLUB_ID " + "FROM USER_CLUB " + "WHERE USER_ID = ?";
      jdbcUtil.setSqlAndParameters(sql, new Object[] { userId });

      try {
         ResultSet rs = jdbcUtil.executeQuery();
         List<String> usersClubIDList = new ArrayList<String>();
         while (rs.next()) {
            System.out.println(rs.getString("CLUB_ID"));
            usersClubIDList.add(rs.getString("CLUB_ID"));
         }
         return usersClubIDList;

      } catch (Exception ex) {
         ex.printStackTrace();
      } finally {
         jdbcUtil.close();
      }
      return null;
   }
   
   public List<String> findChairsClubIDList(String chairId) {
      String sql = "SELECT CLUB_ID " + "FROM CLUB " + "WHERE CHAIR_ID = ?";
      jdbcUtil.setSqlAndParameters(sql, new Object[] { chairId });

      try {
         ResultSet rs = jdbcUtil.executeQuery();
         List<String> chairsClubIDList = new ArrayList<String>();
         while (rs.next()) {
            System.out.println(rs.getString("CLUB_ID"));
            chairsClubIDList.add(rs.getString("CLUB_ID"));
         }
         return chairsClubIDList;

      } catch (Exception ex) {
         ex.printStackTrace();
      } finally {
         jdbcUtil.close();
      }
      return null;
   }

   public List<String> findClubCategory(String clubId) {
      String sql = "SELECT h.TITLE AS TITLE " + "FROM CLUB_CATEGORY c LEFT OUTER JOIN HOBBY h ON c.HB_ID = h.HB_ID "
            + "WHERE c.CLUB_ID = ?";
      jdbcUtil.setSqlAndParameters(sql, new Object[] { clubId });

      try {
         ResultSet rs = jdbcUtil.executeQuery();
         List<String> categoryList = new ArrayList<String>();
         while (rs.next()) {
            System.out.println(rs.getString("TITLE"));
            categoryList.add(rs.getString("TITLE"));
         }
         return categoryList;

      } catch (Exception ex) {
         ex.printStackTrace();
      } finally {
         jdbcUtil.close();
      }
      return null;
   }
   
   public List<String> findPopularClubList(){
       String sql = ""
              + "SELECT ROWNUM, CLUB_ID " 
              + "FROM (SELECT CLUB_ID "
                   + "     FROM CLUB "
                   + "WHERE POPULARITY = '인기') "
               // + "ORDER BY NUMOFMEMBERS) "
               + "WHERE ROWNUM <= 3 ";
       jdbcUtil.setSqlAndParameters(sql, null);

       try {
           ResultSet rs = jdbcUtil.executeQuery();
           List<String> usersClubIDList = new ArrayList<String>();
           while (rs.next()) {
               System.out.println(rs.getString("CLUB_ID"));
               usersClubIDList.add(rs.getString("CLUB_ID"));
           }
           return usersClubIDList;

       } catch (Exception ex) {
           ex.printStackTrace();
       } finally {
           jdbcUtil.close();
       }
       return null;
   }
   
   public List<String> findClubListbyCategory(String first, String second, String third){
       String sql = "SELECT DISTINCT CLUB_ID "
               + "FROM CLUB_CATEGORY "
               + "WHERE HB_ID = ? OR HB_ID = ? OR  HB_ID = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] {first, second, third});

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            List<String> clubListbyCategory = new ArrayList<String>();
            while (rs.next()) {
                System.out.println(rs.getString("CLUB_ID"));
                clubListbyCategory.add(rs.getString("CLUB_ID"));
            }
            return clubListbyCategory;

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }

   public List<String> searchClubList(String keyword){
       String sql = "SELECT CLUB_ID, CLUBNAME "
               + "FROM Club  "
               + "WHERE CLUBNAME LIKE '%'||?||'%' ";    
       jdbcUtil.setSqlAndParameters(sql, new Object[] {keyword});  
       
       try {
           ResultSet rs = jdbcUtil.executeQuery();                    
           List<String> findClubListbyMBTI = new ArrayList<String>();  
           while (rs.next()) {   
               System.out.println(rs.getString("CLUB_ID"));
               findClubListbyMBTI.add(rs.getString("CLUB_ID"));             
           }       
           return findClubListbyMBTI;                    
           
       } catch (Exception ex) {
           ex.printStackTrace();
       } finally {
           jdbcUtil.close();      
       }
       return null;   
   }
   
   public int increaseClubMemberCnt(String clubId) {
		String sql = "UPDATE Club " + "SET NUMOFMEMBERS=NVL(NUMOFMEMBERS,0) + 1 " + "WHERE CLUB_ID = ?";

		jdbcUtil.setSqlAndParameters(sql, new Object[] { clubId });

		try {
			if (jdbcUtil.executeUpdate() != 1)
				throw new AppException();
			jdbcUtil.commit();
			return 1;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close();
		}
		return 0;
	}
	
	public int decreaseClubMemberCnt(String clubId) {
		String sql = "UPDATE Club " + "SET NUMOFMEMBERS=NVL(NUMOFMEMBERS,0) - 1 " + "WHERE CLUB_ID = ?";

		jdbcUtil.setSqlAndParameters(sql, new Object[] { clubId });

		try {
			if (jdbcUtil.executeUpdate() != 1)
				throw new AppException();
			jdbcUtil.commit();
			return 1;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.commit();
			jdbcUtil.close();
		}
		return 0;
	}
	

    public int updateClubMbti(String clubId, String mbti){    
        String sql = "UPDATE CLUB "
                + "SET MBTI = ? "
                + "WHERE CLUB_ID = ?";
       
       jdbcUtil.setSqlAndParameters(sql, new Object[] {mbti, clubId});   // JDBCUtil 뿉 update臾멸낵 留ㅺ컻 蹂  닔  꽕 젙
       try {            
            if (jdbcUtil.executeUpdate() != 1) // update 臾   떎 뻾
                throw new AppException(); 
            
          return 1;
       } catch (Exception ex) {
          if (ex instanceof AppException) {
             jdbcUtil.rollback();
             System.out.println(" 듃 옖 옲 뀡 씠 rollback  릺 뿀 뒿 땲 떎.");      
          }
       }
       finally {
          jdbcUtil.commit();
          jdbcUtil.close();   // resource 諛섑솚
       }      
       return 0;
    }
    

    public String findMaxOfMbtiInClub(String clubId){ 
        String sql = "SELECT  MBTI "
                + "FROM USER_CLUB uc LEFT OUTER JOIN USERINFO u ON uc.USER_ID = u.USER_ID "
                + "GROUP BY MBTI "
                + "HAVING  count(MBTI) = ( SELECT  max(CNT)  FROM ( "
                + "SELECT  count(u.MBTI) as CNT "
                + "FROM  USER_CLUB uc LEFT OUTER JOIN USERINFO u ON uc.USER_ID = u.USER_ID "
                + "WHERE CLUB_ID = ? "
                + "GROUP BY MBTI ) ) ";
        jdbcUtil.setSqlAndParameters(sql, new Object[] {clubId});   // JDBCUtil 뿉 update臾멸낵 留ㅺ컻 蹂  닔  꽕 젙
        try {       
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                return rs.getString("MBTI");
            }
        } catch (Exception ex) {
           if (ex instanceof AppException) {
              jdbcUtil.rollback();
              System.out.println(" 듃 옖 옲 뀡 씠 rollback  릺 뿀 뒿 땲 떎.");      
           }
        }
        finally {
           jdbcUtil.commit();
           jdbcUtil.close();   // resource 諛섑솚
        }      
        return null;
     }
    
}