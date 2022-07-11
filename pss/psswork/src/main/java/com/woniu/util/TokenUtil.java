package com.woniu.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenUtil {

    //使用uuid生成密钥
    private static final String SECRET= UUID.randomUUID().toString();
    //保存在载荷中的登录人的键
    private static final String USERNAME_KEY="usernameKey";

    /**
     * 生成token
     * @param username  当前登录人的帐号
     * @return
     */
    public static String createToken(String username) throws JOSEException {
        //头部
        JWSHeader header=new JWSHeader.Builder(JWSAlgorithm.HS256)//加密算法
                .type(JOSEObjectType.JWT)
                .build();
        //载荷
        Map<String,Object> map=new HashMap<String,Object>();
        map.put(USERNAME_KEY,username);
        Payload payload=new Payload(map);

        //创建密钥对象
        JWSSigner jwsSigner=new MACSigner(SECRET);

        //创建签名对象
        JWSObject jwsObject=new JWSObject(header, payload);
        jwsObject.sign(jwsSigner);

        return jwsObject.serialize();
    }

    /**
     * 取出载荷中保存的用户名
     * @param tokenStr  登录时生成的token字符串
     * @return
     */
    public static String getUserName(String tokenStr) throws ParseException {
        //将token字符串转为签名对象
        JWSObject jwsObject=JWSObject.parse(tokenStr);
        Map<String,Object> map=jwsObject.getPayload().toJSONObject();
        return (String) map.get(USERNAME_KEY);
    }

    /**
     * 验证token是否合法
     * @param tokenStr
     * @return
     */
    public static boolean verifyToken(String tokenStr) throws Exception {
        //将token字符串转为签名对象
        JWSObject jwsObject=JWSObject.parse(tokenStr);
        //密钥
        JWSVerifier jwsVerifier = new MACVerifier(SECRET);
        return jwsObject.verify(jwsVerifier);
    }
}
