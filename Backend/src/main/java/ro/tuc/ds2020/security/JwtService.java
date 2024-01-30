package ro.tuc.ds2020.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "71AE229101936A586E31980575FFA52F413F4428472BC36324945367566B5970";

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts.builder().
                setClaims(extraClaims).
                setSubject(userDetails.getUsername()).
                setIssuedAt(new Date(System.currentTimeMillis())).
                setExpiration(new Date(System.currentTimeMillis() + 1000*60*10)).// 10 minutes
                signWith(getSigningKey(), SignatureAlgorithm.HS256).
                compact();

    }

    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);

    }
    public Claims extractAllClaims(String token){
        return Jwts.
                parserBuilder().
                setSigningKey(getSigningKey()).build().
                parseClaimsJws(token).getBody();

    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUserName(token);

        return username.equals(userDetails.getUsername()) && isTokenUnexpired(token);
    }

    private boolean isTokenUnexpired(String token) {
        Date date = extractClaim(token, Claims::getExpiration);
        return date.after(new Date());
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
