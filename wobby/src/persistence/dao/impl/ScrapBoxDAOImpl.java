package persistence.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import persistence.dao.ScrapBoxDAO;
import service.dto.ScrapBoxDTO;

// 뜝 룞 삕 뜝 룞 삕 뜝 룞 삕  뜝 뙥怨ㅼ삕
public class ScrapBoxDAOImpl implements ScrapBoxDAO {

    private JDBCUtil jdbcUtil = null;

    public ScrapBoxDAOImpl() {
        jdbcUtil = new JDBCUtil();
    }

    /**
     *  뜝 룞 삕泥  Scrap  뜝 룞 삕 뜝 룞 삕 듃  뜝 룞 삕 솚
     */
    public List<ScrapBoxDTO> getScrapList(String userId) {
        String query = "SELECT SCRAP_NO, POST_ID, SCRAPDATE "
                + "FROM SCRAP "
                + "WHERE USER_ID = ? "
                + "ORDER BY SCRAPDATE DESC";
        jdbcUtil.setSqlAndParameters(query, new Object[] { userId });
        
        try {
            ResultSet rs = jdbcUtil.executeQuery(); 
            List<ScrapBoxDTO> list = new ArrayList<ScrapBoxDTO>(); 
            while (rs.next()) {
                ScrapBoxDTO dto = new ScrapBoxDTO(); 
                dto.setScrapNo(rs.getInt("SCRAP_NO"));
                dto.setPostId(rs.getInt("POST_ID"));
                dto.setScrapDate(rs.getDate("SCRAPDATE"));
                System.out.println(rs.getInt("SCRAP_NO") + "/" + rs.getInt("POST_ID") + "/" + rs.getDate("SCRAPDATE"));
                list.add(dto);
            }
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close(); // ResultSet, PreparedStatement, Connection  뜝 룞 삕 솚
        }
        return null;
    }

    /**
     * Post 뜝 룞 삕 ScrapBox 뜝 룞 삕  뜝 룞 삕 뜝 룞 삕
     */
    public int createScrap(ScrapBoxDTO scrap) {
        String sql = "INSERT INTO SCRAP VALUES (?, ?, SCRAP_SEQ.NEXTVAL, SYSDATE)";

        Object[] param = new Object[] { scrap.getPostId(), scrap.getUserId() };
        jdbcUtil.setSqlAndParameters(sql, param); // JDBCUtil  뜝 룞 삕 insert 뜝 룞 삕 뜝 룞 삕  뜝 떊怨ㅼ삕  뜝 룞 삕 뜝 룞 삕  뜝 룞 삕 뜝 룞 삕

        try {
            int result = jdbcUtil.executeUpdate(); 
            return result;
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close(); // resource  뜝 룞 삕 솚
        }
        return 0;
    }

    /**
     * Scrap 뜝 룞 삕 Post 뜝 룞 삕 뜝 룞 삕  뜝 룞 삕 뜝 룞 삕
     */
    public int deleteScrap(String ScrapNo) {
        String sql = "DELETE FROM SCRAP WHERE SCRAP_NO = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] { ScrapNo }); // JDBCUtil 뜝 룞 삕 delete 뜝 룞 삕 뜝 룞 삕  뜝 떊怨ㅼ삕  뜝 룞 삕 뜝 룞 삕  뜝 룞 삕 뜝 룞 삕

        try {
            int result = jdbcUtil.executeUpdate(); // delete  뜝 룞 삕  뜝 룞 삕 뜝 룞 삕
            return result;
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close(); // resource  뜝 룞 삕 솚
        }
        return 0;
    }
    
    public int increaseNumOfScraps(int postId) {
        String sql = "UPDATE POST SET NUMOFSCRAPS = (SELECT COUNT(SCRAP_NO) FROM SCRAP)" + 
    " WHERE POST_ID=?";   
        jdbcUtil.setSqlAndParameters(sql,  new Object[] {postId});   // JDBCUtil 뜝 럥 뱺 update  닱簾ル챶沅  嶺뚮씞 걝     솻洹⑥삕 뜝 럥 빢  뜝 럡 맟 뜝 럩 젧
           
        try {            
           int result = jdbcUtil.executeUpdate();   // update   닱 뜝   뜝 럥堉꾢뜝 럥六 
           return result;
        } catch (Exception ex) {
           jdbcUtil.rollback();
           ex.printStackTrace();
        }
        finally {
           jdbcUtil.commit();
           jdbcUtil.close();   // resource  뛾 룇瑗   꼶
        }      
        return 0;
    }
    

    public int deleteScrap(int scrapNo) {
        String sql = "DELETE FROM SCRAP WHERE SCRAP_NO = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] { scrapNo }); // JDBCUtil 뜝 룞 삕 delete 뜝 룞 삕 뜝 룞 삕  뜝 떊怨ㅼ삕  뜝 룞 삕 뜝 룞 삕  뜝 룞 삕 뜝 룞 삕

        try {
            int result = jdbcUtil.executeUpdate(); // delete  뜝 룞 삕  뜝 룞 삕 뜝 룞 삕
            return result;
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {
            jdbcUtil.commit();
            jdbcUtil.close(); // resource  뜝 룞 삕 솚
        }
        return 0;
    }

    public String getPostIdbyScrap(int scrapNo) {
        String query = "SELECT POST_ID "
                + "FROM SCRAP "
                + "WHERE SCRAP_NO = ? ";
        jdbcUtil.setSqlAndParameters(query, new Object[] { scrapNo });
        
        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                return rs.getString("POST_ID");
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close(); // ResultSet, PreparedStatement, Connection  뜝 룞 삕 솚
        }
        return null;
    }

}