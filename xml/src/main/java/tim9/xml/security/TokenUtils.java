package tim9.xml.security;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtils {
	 @Value("mySecret")
	    private String secret;

	    @Value("18000")
	    private Long expiration;
	    
		@Value("Authorization")
		private String AUTH_HEADER;


	    public String getEmailFromToken(String token) {
	        String email;
	        try {
	            Claims claims = this.getClaimsFromToken(token);
	            email = claims.getSubject();
	        } catch (Exception e) {
	            email = null;
	        }
	        return email;
	    }

	    private Claims getClaimsFromToken(String token) {
	        Claims claims;
	        try {
	            claims = Jwts.parser().setSigningKey(this.secret)
	                    .parseClaimsJws(token).getBody();
	        } catch (Exception e) {
	            claims = null;
	        }
	        return claims;
	    }

	    public Date getExpirationDateFromToken(String token) {
	        Date expiration;
	        try {
	            final Claims claims = this.getClaimsFromToken(token);
	            expiration = claims.getExpiration();
	        } catch (Exception e) {
	            expiration = null;
	        }
	        return expiration;
	    }

	    private boolean isTokenExpired(String token) {
	        final Date expiration = this.getExpirationDateFromToken(token);
	        return expiration.before(new Date(System.currentTimeMillis()));
	    }

	    public boolean validateToken(String token, UserDetails userDetails) {
	        final String email = getEmailFromToken(token);
	        return email.equals(userDetails.getUsername())
	                && !isTokenExpired(token);
	    }

	    public String generateToken(UserDetails userDetails) {
	        Map<String, Object> claims = new HashMap<>();
	        claims.put("sub", userDetails.getUsername());
	        claims.put("created", new Date(System.currentTimeMillis()));
	        return Jwts.builder().setSubject(userDetails.getUsername())
	        		.setClaims(claims)
	                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
	                .signWith(SignatureAlgorithm.HS512, secret).compact();
	    }
	    
	    public String getUsernameFromToken(String token) {
			String username;
			try {
				final Claims claims = this.getClaimsFromToken(token);
				username = claims.getSubject();
				System.out.println("try token " + username);
			} catch (Exception e) {
				username = null;
				System.out.println("catch token");
			}
			return username;
		}
	    
	    
	 // Functions for getting JWT token out of HTTP request

		public String getToken(HttpServletRequest request) {
			String authHeader = getAuthHeaderFromHeader(request);
			System.out.println("Token " + authHeader);
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				return authHeader.substring(7);
			}

			return null;
		}

		public String getAuthHeaderFromHeader(HttpServletRequest request) {
			return request.getHeader(AUTH_HEADER);
		}
}
