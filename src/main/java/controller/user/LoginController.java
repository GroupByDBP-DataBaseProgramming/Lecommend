package controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import model.service.UserManager;

public class LoginController implements Controller {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = request.getParameter("stuId");
		String password = request.getParameter("stuPw");
		
		System.out.println("<Logincontroller> userId: " + userId + " password: " + password);

		try {
			// 모델에 로그인 처리를 위임
			UserManager manager = UserManager.getInstance();
			manager.login(userId, password);

			// 세션에 사용자 이이디 저장
			HttpSession session = request.getSession();
			session.setAttribute("userId", userId);

			return "redirect:/home";
		} catch (Exception e) {
			/*
			 * UserNotFoundException이나 PasswordMismatchException 발생 시 다시 login form을 사용자에게
			 * 전송하고 오류 메세지도 출력
			 */
			request.setAttribute("loginFailed", true);
			request.setAttribute("exception", e);
			return "/user/loginForm.jsp";
		}
	}
}
