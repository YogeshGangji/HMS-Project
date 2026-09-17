package com.hms.user.service;

import com.hms.user.client.Client;
import com.hms.user.dto.Roles;
import com.hms.user.dto.UserDTO;
import com.hms.user.entity.User;
import com.hms.user.exception.HMSException;
import com.hms.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private ApiService apiService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Client client;

    @Override
    public void registerUser(UserDTO userDTO) throws HMSException{

        Optional<User> userOptional = userRepository.findByEmail(userDTO.getEmail());

        if(userOptional.isPresent()){
            throw new HMSException("USER_ALREADY_EXISTS");
        }

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Long profileId = null;
        if(userDTO.getRole().equals(Roles.DOCTOR))
        {
            profileId = client.addDoctorProfile(userDTO);
        }
        else if(userDTO.getRole().equals(Roles.PATIENT))
        {
            profileId = client.addPatientProfile(userDTO);
        }
//       = apiService.addProfile(userDTO).block();

        System.out.printf("profileId: %d",profileId);
        userDTO.setProfileId(profileId);
//        userDTO.setActive(true);
        userRepository.save(userDTO.toEntity());
    }

    @Override
    public UserDTO loginUser(UserDTO userDTO) {
        User user = userRepository.findByEmail(userDTO.getEmail()).orElseThrow(()->new HMSException("USER_NOT_FOUND"));
        if(!passwordEncoder.matches(userDTO.getPassword(),user.getPassword()))
        {
            throw new HMSException("INVALID_PASSWORD");
        }

        user.setPassword(null);
        return user.toDTO();
    }

    @Override
    public UserDTO getUserById(Long id) throws HMSException{
        return  userRepository.findById(id)
                .orElseThrow(() -> new HMSException("USER_NOT_FOUND"))
                .toDTO();
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        if(!userRepository.existsById(userDTO.getId()))
        {
            throw new HMSException("USER_NOT_FOUND");
        }

        Optional<User> userOptional = userRepository.findById(userDTO.getId());
        if(userOptional.isPresent()){
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userRepository.save(userDTO.toEntity());
        }
    }

    @Override
    public void deleteUser(Long id) {

        if(userRepository.findById(id).isEmpty())
        {
            throw new HMSException("USER_NOT_FOUND");
        }
        User user = userRepository.findById(id).get();
        //        user.setActive(false);

    }

    @Override
    public UserDTO getUserByEmail(String email) throws HMSException {
        return userRepository.findByEmail(email).orElseThrow(()->new HMSException("USER_NOT_FOUND")).toDTO();
    }

    @Override
    public Boolean existUserById(Long id) throws HMSException {
        if(id != null)
            return userRepository.existsById(id);
        else
            throw new HMSException("Id is empty");
    }

}
