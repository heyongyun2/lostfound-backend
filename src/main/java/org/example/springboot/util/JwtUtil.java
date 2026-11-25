package org.example.springboot.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;


@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String sceretStr;
    @Value("${jwt.expire}")
    private long expiretime;
    private SecretKey secretKey;
    @PostConstruct
    public  void  initkey(){
        byte[] bytes=Base64.getDecoder().decode(sceretStr);
        secretKey=Keys.hmacShaKeyFor(bytes);

    }
    public String Createtoken(Long userId){
        Date now =new Date();
        Date expireData=new Date(now.getTime()+expiretime);
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(now)
                .setExpiration(expireData)
                .signWith(secretKey)
                .compact();
    }
    public Claims parseToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

    }
    public Long getUserId(String token){
        Claims claims=parseToken(token);
        return Long.parseLong(claims.getSubject());

    }
    public boolean verifyToken(String token){
        try{
            parseToken(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }



}

