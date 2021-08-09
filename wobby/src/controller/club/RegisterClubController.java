package controller.club;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.user.UserSessionUtils;
import service.CommunityManager;
import service.ExistingUserException;
import service.UserManager;
import service.dto.ClubDTO;

public class RegisterClubController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		CommunityManager commManager = CommunityManager.getInstance();

		String clubId = request.getParameter("clubId");
		String userId = UserSessionUtils.getLoginUserId(session);
		
		commManager.increaseMember(clubId);
		commManager.newMember(userId, clubId);
		commManager.updateClubMbti(clubId);
		
		System.out.println("가입완료");

		return "redirect:/club/myClub";
	}
}