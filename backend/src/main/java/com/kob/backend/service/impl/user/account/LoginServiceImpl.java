package com.kob.backend.service.impl.user.account;

import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.service.user.account.LoginService;
import com.kob.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Map<String,String> getToken(String username, String password){
//        把用户名和密码封装成类，存为加密之后的字符串
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username,password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);  //如果登录失败会自动处理
//        登陆成功取出用户
        UserDetailsImpl loginUser = (UserDetailsImpl)authenticate.getPrincipal();
        User user = loginUser.getUser();

//        把用户userId封装成jwt-token
        String jwt = JwtUtil.createJWT(user.getId().toString());

        Map<String,String> map = new HashMap<>();
        map.put("error_message","success");
        map.put("token",jwt);

        return map;
    }
}
