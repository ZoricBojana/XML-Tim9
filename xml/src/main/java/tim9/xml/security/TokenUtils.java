package tim9.xml.security;
import java.util.Date;

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
	    private String SECRET;

	    @Value("180000")
	    private Long expiration;
	    
		@Value("Authorization")
		private String AUTH_HEADER;
		
		private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;


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
	            claims = Jwts.parser().setSigningKey(SECRET)
	                    .parseClaimsJws(token).getBody();
	        } catch (Exception e) {
	        	e.printStackTrace();
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

	    	Date now = new Date();
			String token = Jwts.builder()
					.setSubject(userDetails.getUsername())
					.setIssuedAt(now)
					.setExpiration(new Date(System.currentTimeMillis() + 3600000))
					.signWith(SIGNATURE_ALGORITHM, SECRET)
					.compact();
			return token;
	    }
	    
	    public String getUsernameFromToken(String token) {
			String username;
			try {
				final Claims claims = this.getClaimsFromToken(token);
				username = claims.getSubject();
			} catch (Exception e) {
				e.printStackTrace();
				username = null;
			}
			return username;
		}
	    
	    
	 // Functions for getting JWT token out of HTTP request

		public String getToken(HttpServletRequest request) {
			String authHeader = getAuthHeaderFromHeader(request);
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				return authHeader.substring(7);
			}

			return null;
		}

		public String getAuthHeaderFromHeader(HttpServletRequest request) {
			return request.getHeader(AUTH_HEADER);
		}
}
