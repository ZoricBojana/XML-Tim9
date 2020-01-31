package tim9.xml.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import rs.ac.uns.msb.Person;
import tim9.xml.repository.PersonRepository;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private PersonRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person user = null;
        try {
            user = userRepository.findOneByUsername(email);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", email));
        } else {
            return  (UserDetails) user;
        }
    }
}