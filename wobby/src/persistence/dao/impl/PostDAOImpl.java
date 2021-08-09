package persistence.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistence.dao.impl.JDBCUtil;
import persistence.dao.ConnectionManager;
import persistence.dao.PostDAO;
import service.dto.BoardDTO;
import service.dto.CommunityDTO;
import service.dto.PostDTO;

public class PostDAOImpl implements PostDAO {
private JDBCUtil jdbcUtil = null;
private static ConnectionManager connMan = new ConnectionManager();
   
   public PostDAOImpl() {         
      jdbcUtil = new JDBCUtil();   // JDBCUtil 
   }
   
   public int create(PostDTO post, int boardId) {
      Connection conn = null;
      PreparedStatement pStmt = null;         
      ResultSet rs = null;
      
      String sql = "INSERT INTO Post (POST_ID, POSTDATE, USER_ID, TITLE, CONTENTS,BOARD_ID) VALUES (POST_SEQ.nextval, SYSDATE, ?, ?, ?, ?)";
      
      String sql2 = "UPDATE BOARD SET NUMOFPOST = (SELECT COUNT(POST_ID) FROM POST p  WHERE p.BOARD_ID=? ) " + 
            "      WHERE BOARD_ID=?";
      
      String sql3 = "UPDATE POST SET NICKNAME = (SELECT NICKNAME FROM USERINFO"
      		+ " WHERE USER_ID = ? ) " 
    		  + "WHERE USER_ID=?";
         try {   
            //sql1
            conn = connMan.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, post.getUserId());
            pStmt.setString(2, post.getTitle());
            pStmt.setString(3, post.getContents());
            pStmt.setInt(4, boardId);
             
            pStmt.addBatch();
            pStmt.executeBatch();
            pStmt.clearParameters();
            pStmt.close();
            
            //sql2
            pStmt = conn.prepareStatement(sql2);
            pStmt.setInt(1, boardId);
            pStmt.setInt(2, boardId);
            
            pStmt.addBatch();
            pStmt.executeBatch();
            pStmt.clearParameters();
            pStmt.close();
            
          //sql3
            pStmt = conn.prepareStatement(sql3);
            pStmt.setString(1, post.getUserId());
            pStmt.setString(2, post.getUserId());
            
            pStmt.addBatch();
            pStmt.executeBatch();
            
   
            return 1;
         } catch (Exception ex) {
            ex.printStackTrace();
         } finally {      
            if (pStmt != null) {
               try {
                  pStmt.close();
               } catch (SQLException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               }
            }
            if (conn != null) 
               try {
                  conn.rollback();
                  conn.close();
               }catch(SQLException ex) {
                  ex.printStackTrace();
               }
            
         }      
         return 0;
   }
   
   /**
    * post를 수정 
    */
   public int update(PostDTO post) {
      String sql = "UPDATE Post "
            + "SET TITLE=?, CONTENTS = ?, UPDATEDATE=SYSDATE "
            + "WHERE POST_ID=? ";
      int postId = post.getPostId();
   
      Object[] param = new Object[] {post.getTitle(), post.getContents(), post.getPostId()};            
      jdbcUtil.setSqlAndParameters(sql, param);   
         
      try {            
         int result = jdbcUtil.executeUpdate();   
         return result;
      } catch (Exception ex) {
         jdbcUtil.rollback();
         ex.printStackTrace();
      }
      finally {
         jdbcUtil.commit();
         jdbcUtil.close();  
      }      
      return 0;
      
   }
   
   /**
    * post 삭제 
    */
   public int delete(int postId)  {
      String sql = "DELETE FROM POST WHERE POST_ID=?";      
      jdbcUtil.setSqlAndParameters(sql, new Object[] {postId});   

      try {            
         int result = jdbcUtil.executeUpdate();   
         return result;
      } catch (Exception ex) {
         jdbcUtil.rollback();
         ex.printStackTrace();
      }
      finally {
         jdbcUtil.commit();
         jdbcUtil.close();   
      }      
      return 0;
   }
   
   /*
    * post 조회 수 
    * */
   public int increasePostViewCnt(int postId) {
      String sql = "UPDATE Post "
            + "SET NUMOFVIEW=NVL(NUMOFVIEW,0) + 1 "
            + "WHERE POST_ID=?";
            
      jdbcUtil.setSqlAndParameters(sql, new Object[] {postId});   
         
      try {            
         int result = jdbcUtil.executeUpdate();   
         return result;
      } catch (Exception ex) {
         jdbcUtil.rollback();
         ex.printStackTrace();
      }
      finally {
         jdbcUtil.commit();
         jdbcUtil.close();   
      }      
      return 0;
   }
   
   /*
    * post 스크랩수
    * */
   public int increasePostScrapCnt(int postId) {
      String sql = "UPDATE Post "
            + "SET NUMOFSCRAPS=NVL(NUMOFSCRAPS,0) + 1 "
            + "WHERE POST_ID=?";
            
      jdbcUtil.setSqlAndParameters(sql, new Object[] {postId});   
         
      try {            
         int result = jdbcUtil.executeUpdate();   
         return result;
      } catch (Exception ex) {
         jdbcUtil.rollback();
         ex.printStackTrace();
      }
      finally {
         jdbcUtil.commit();
         jdbcUtil.close();   
      }      
      return 0;
   }
   
   /**
    * 인기 post를 list로 반환 
    */
    public List<PostDTO> findPopularPostList(int boardId) {
       String sql = "SELECT POST_ID, TITLE, NICKNAME, POSTDATE ,NUMOFVIEW, NUMOFSCRAPS "
                + "FROM POST " 
               + "WHERE BOARD_ID=? AND NUMOFVIEW >= 10 AND NUMOFSCRAPS >= 10 "
                  + "ORDER BY NUMOFVIEW ";        
               jdbcUtil.setSqlAndParameters(sql, new Object[] {boardId});      
                        
               try {
                  ResultSet rs = jdbcUtil.executeQuery();              
                  List<PostDTO> postList = new ArrayList<PostDTO>();   
                  PostDTO post = null;
                  while (rs.next()) {
                     post = new PostDTO();         

                     post.setPostId(rs.getInt("POST_ID"));
                     post.setTitle(rs.getString("TITLE"));
                     post.setNickname(rs.getString("NICKNAME"));
                     post.setPostDate(rs.getDate("POSTDATE"));
                     post.setNumOfView(rs.getInt("NUMOFVIEW"));
                     post.setNumOfScraps(rs.getInt("NUMOFSCRAPS"));
                     
                     postList.add(post);            
                  }   
                  jdbcUtil.commit();
                  return postList;               
                  
               } catch (Exception ex ) {
                  if (ex instanceof AppException) {
                     jdbcUtil.rollback();
                     System.out.println("트랜잭션이 rollback 되었습니다.");	
                  }
                  ex.printStackTrace();
               }
               finally {   
                  if (jdbcUtil != null) {
                     jdbcUtil.close();
                  }
               }
               return null;
    }
   /**
    * post 찾기 
    */
   public PostDTO findPost(int postId)  {
	   String sql = "SELECT POST_ID, USER_ID, TITLE, NICKNAME, POSTDATE ,NUMOFVIEW, NUMOFSCRAPS, BOARD_ID, CONTENTS "
	             	+ "FROM POST " 
	             	+ "WHERE POST_ID=? ";
	                          
      jdbcUtil.setSqlAndParameters(sql, new Object[] {postId});   
      PostDTO post = null;
      try {
         ResultSet rs = jdbcUtil.executeQuery();     
         if (rs.next()) {                  
            post = new PostDTO();     
            
            post.setPostId(rs.getInt("POST_ID"));
            post.setUserId(rs.getString("USER_ID"));
            post.setTitle(rs.getString("TITLE"));
            post.setNickname(rs.getString("NICKNAME"));
            post.setPostDate(rs.getDate("POSTDATE"));
            post.setNumOfView(rs.getInt("NUMOFVIEW"));
            post.setNumOfScraps(rs.getInt("NUMOFSCRAPS"));
            post.setBoardId(rs.getInt("BOARD_ID"));
            post.setContents(rs.getString("CONTENTS"));
         }
      } catch (Exception ex ) {
          if (ex instanceof AppException) {
              jdbcUtil.rollback();
              System.out.println("트랜잭션이 rollback 되었습니다.");	
           }
           ex.printStackTrace();
        }
        finally {   
           if (jdbcUtil != null) {
              jdbcUtil.close();
           }
        }
      return post;
   }
   
   /**
    * boardId에 해당하는 post를 list로 반환 
    */
   public List<PostDTO> findPostList(int boardId)  {
      String sql = "SELECT POST_ID, TITLE, NICKNAME, POSTDATE ,NUMOFVIEW, NUMOFSCRAPS, BOARD_ID "
             + "FROM POST " 
            + "WHERE BOARD_ID=? "
               + "ORDER BY POST_ID ";
      		System.out.println("PostDAO - findPostList Start");
            jdbcUtil.setSqlAndParameters(sql, new Object[] {boardId});      
                     
            try {
               ResultSet rs = jdbcUtil.executeQuery();               
               List<PostDTO> postList = new ArrayList<PostDTO>();   
               PostDTO post = null;
               while (rs.next()) {
                  post = new PostDTO();         

                  post.setPostId(rs.getInt("POST_ID"));
                  post.setTitle(rs.getString("TITLE"));
                  post.setNickname(rs.getString("NICKNAME"));
                  post.setPostDate(rs.getDate("POSTDATE"));
                  post.setNumOfView(rs.getInt("NUMOFVIEW"));
                  post.setNumOfScraps(rs.getInt("NUMOFSCRAPS"));
                  post.setBoardId(rs.getInt("BOARD_ID"));
                  
                  postList.add(post);            
               }   
               jdbcUtil.commit();
               return postList;               
               
            } catch (Exception ex ) {
               if (ex instanceof AppException) {
                  jdbcUtil.rollback();
                  System.out.println("트랜잭션이 rollback 되었습니다.");	
               }
               ex.printStackTrace();
            }
            finally {   
               if (jdbcUtil != null) {
                  jdbcUtil.close();
               }
            }
            return null;
	   
//	   System.out.println("PostDAO - findPostList Start");
//	      String sql = "SELECT POST_ID, TITLE "
//	             + "FROM POST " 
//	            + "WHERE BOARD_ID=? "
//	               + "ORDER BY POST_ID ";        
//	            jdbcUtil.setSqlAndParameters(sql, new Object[] {boardId}); 
//	                     
//	            try {
//	               ResultSet rs = jdbcUtil.executeQuery();   
//	               List<PostDTO> postList = new ArrayList<PostDTO>();   
//	               PostDTO post = null;
//	               while (rs.next()) {
//	System.out.println(rs.getString("TITLE"));
//	                  post = new PostDTO(); 
//	                  post.setPostId(rs.getInt("POST_ID"));
//	                  post.setTitle(rs.getString("TITLE"));
//
//	                  postList.add(post);                     }   
//	               jdbcUtil.commit();
//	               return postList;               
//	               
//	            } catch (Exception ex ) {
//	               if (ex instanceof AppException) {
//	                  jdbcUtil.rollback();
//	                  System.out.println("");   
//	               }
//	               ex.printStackTrace();
//	            }
//	            finally {   
//	               if (jdbcUtil != null) {
//	                  jdbcUtil.close();
//	               }
//	            }
//	            return null;

   }
   
    public List<PostDTO> searchPostList(String keyword)  {
        String sql = "SELECT POST_ID, TITLE, NICKNAME, POSTDATE, NUMOFVIEW, NUMOFSCRAPS "
                + "FROM POST "
                + "WHERE CONTENTS LIKE '%'||?||'%' "
                + "ORDER BY POST_ID ";        
                jdbcUtil.setSqlAndParameters(sql, new Object[] {keyword});      
                           
                try {
                    ResultSet rs = jdbcUtil.executeQuery();                  
                    List<PostDTO> postList = new ArrayList<PostDTO>();  
                    PostDTO post = null;
                    while (rs.next()) {
                        post = new PostDTO();           

                        post.setPostId(rs.getInt("POST_ID"));
                        post.setTitle(rs.getString("TITLE"));
                        post.setNickname(rs.getString("NICKNAME"));
                        post.setPostDate(rs.getDate("POSTDATE"));
                        post.setNumOfView(rs.getInt("NUMOFVIEW"));
                        post.setNumOfScraps(rs.getInt("NUMOFSCRAPS"));
                        
                        postList.add(post);           
                    }   
                    jdbcUtil.commit();
                    return postList;                    
                    
                } catch (Exception ex ) {
                    if (ex instanceof AppException) {
                        jdbcUtil.rollback();
                        System.out.println("트랜잭션이 rollback 되었습니다.");	
                    }
                    ex.printStackTrace();
                }
                finally {   
                    if (jdbcUtil != null) {
                        jdbcUtil.close();
                    }
                }
                return null;
    }
    
    public int decreaseNumOfScraps(int postId) {
        String sql = "UPDATE POST SET NUMOFSCRAPS = NUMOFSCRAPS - 1 " + "      WHERE POST_ID=?";   
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
    
   /**
    * post가 존재하는지 검사 
    */
   public boolean existingPost(int postId)  {
      String sql = "SELECT count(*) FROM POST WEHRE POST_ID=?";      
      jdbcUtil.setSqlAndParameters(sql, new Object[] {postId});   

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
   

   public PostDTO findScrapPostList(int postId)  {
       String sql = "SELECT POST_ID, POSTDATE, USER_ID, TITLE, p.BOARD_ID AS BOARD_ID, b.NAME AS BOARD_NAME, UPDATEDATE, CONTENTS, NICKNAME "
              + "FROM POST p LEFT OUTER JOIN BOARD b ON p.BOARD_ID = b.BOARD_ID   "
              + "WHERE p.POST_ID = ? ";              
       jdbcUtil.setSqlAndParameters(sql, new Object[] {postId});   // JDBCUtil 뜝 럥 뱺 query  닱簾ル챶沅  嶺뚮씞 걝     솻洹⑥삕 뜝 럥 빢  뜝 럡 맟 뜝 럩 젧
       PostDTO post = null;
       try {
          ResultSet rs = jdbcUtil.executeQuery();
          if (rs.next()) {
              post = new PostDTO();           // Community  뤆 룇鍮섊뙼 뮁紐닷뜝   뜝 럡臾멨뜝 럡 뎽 뜝 럥由  뜝 럥 뿰  뜝 럩寃긷뜝 럩 궨  뜝 럥六у뜝 럩踰   뜝 럩 젧 솻洹   뜝 룞 삕  뜝 룞 삕 뜝 럩 궋
              post.setPostId(rs.getInt("POST_ID"));
              post.setPostDate(rs.getDate("POSTDATE"));
              post.setUserId(rs.getString("USER_ID"));
              post.setTitle(rs.getString("TITLE"));
              post.setBoardId(rs.getInt("BOARD_ID"));
              post.setBoardName(rs.getString("BOARD_NAME"));
              post.setUpdateDate(rs.getDate("UPDATEDATE"));
              post.setContents(rs.getString("CONTENTS"));
              post.setNickname(rs.getString("NICKNAME"));
          }   
          jdbcUtil.commit();
          return post;
       } catch (Exception ex) {
          ex.printStackTrace();
       } finally {
          jdbcUtil.close();      // resource  뛾 룇瑗   꼶
       }
       return null;
    }
   
   public int increaseNumOfScraps(int postId) {
       String sql = "UPDATE POST SET NUMOFSCRAPS = NUMOFSCRAPS+1" + " WHERE POST_ID=?";   
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
}