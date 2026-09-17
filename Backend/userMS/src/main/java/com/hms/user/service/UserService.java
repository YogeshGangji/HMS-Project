package com.hms.user.service;

import com.hms.user.dto.UserDTO;
import com.hms.user.exception.HMSException;
import org.springframework.stereotype.Service;


public interface UserService {

    public void registerUser(UserDTO userDTO) throws HMSException;
    public UserDTO loginUser(UserDTO userDTO) throws HMSException;
    public UserDTO getUserById(Long id)  throws HMSException;
    public void  updateUser(UserDTO userDTO);
    public void deleteUser(Long id);
    public UserDTO getUserByEmail(String email) throws HMSException;
    public Boolean existUserById(Long id) throws HMSException;

}
