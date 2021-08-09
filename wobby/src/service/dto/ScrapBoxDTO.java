package service.dto;

import java.sql.Date;

public class ScrapBoxDTO {
   private int ScrapNo;
   private int PostId;
   private String UserId;
   private Date ScrapDate;
    private String boardName;
   
   public String getBoardName() {
        return boardName;
    }
    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }
    public Date getScrapDate() {
        return ScrapDate;
    }
    public void setScrapDate(Date scrapDate) {
        ScrapDate = scrapDate;
    }
    public int getScrapNo() {
      return ScrapNo;
   }
   public void setScrapNo(int scrapNo) {
      ScrapNo = scrapNo;
   }
   public int getPostId() {
      return PostId;
   }
   public void setPostId(int postId) {
       PostId = postId;
   }
   public String getUserId() {
      return UserId;
   }
   public void setUserId(String userId) {
      UserId = userId;
   }
}