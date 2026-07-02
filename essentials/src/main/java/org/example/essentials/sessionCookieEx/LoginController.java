package org.example.essentials.sessionCookieEx;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login (
            HttpSession session
    ){
        if(session.getAttribute("username") != null){
            return "redirect:/dashborad";
        }

        return "login";
    }

    @PostMapping("/login")
    public  String login(
            @RequestParam String username,
            HttpSession session
            ){
        session.setAttribute("username", username);

        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();       // 서버가 가진 세션(=로그인 상태)을 통째로 비운다
        return "redirect:/login";
    }
}

/*
1. 세션과 쿠키의 저장 위치·수명·용도 차이를 표로 설명할 수 있다
    세션
     저장 위치 : 서버
     수명 : 서버에서 관리하는 시간이 만료될때까지
     용도 : 로그인과 같이 데이터를 외부에 노출하면 안되는 것들
    쿠키
     저장 위치 : 클라이언트 (브라우저 메모리, 로컬 디스크)
     수명 : 따로 설정하지 않을 시 브라우저 닫으면 제거, setMaxAge로 초단위로 설정
     용도 : 세션에 저장하는 데이터에 비해 비교적 덜 안전한 데이터, 세션을 사용하기 위한 매개체(ex. JSESSIONID)

2.  왜 로그인은 세션에, 테마는 쿠키에 두는지 이유를 댈 수 있다
     민감정보인 로그인은 세션에, 민감정보가 아니지만 상태를 저장해야하는 테마는 쿠키에 저장

3.  세션이 없을 때 리다이렉트로 접근을 막는 흐름을 짤 수 있다
     세션을 조회해서 해당 세션이 존재하지 않는 경우 디폴트 창으로 리다이렉트 시킨다.
4. @CookieValue가 수업의 getCookies() 루프와 같은 일을 한다는 걸 안다
     for문을 사용하여 가진 쿠키중에서 내가 찾는 쿠키에 해당하는 것을 찾는 것을 @CookieValue로 사용할 수 있다.

5.  maxAge와 쿠키 삭제(수명 0)의 원리를 안다
     따로 설정하지 않는 경우 브라우저 닫으면 사라진다.
     쿠키를 지워버리고 싶은경우 maxAge값을 0 으로 설정하면 지워진다.

6.  로그아웃(invalidate) 후에도 쿠키가 남는 이유를 설명할 수 있다
     해당 코드는 세션을 없애는 작업이므로 쿠키 삭제와는 별개이다.
     세션을 지우고 쿠키도 정리하고 싶은 경우 setMaxAge(0) 을 추가하면 된다.
* */