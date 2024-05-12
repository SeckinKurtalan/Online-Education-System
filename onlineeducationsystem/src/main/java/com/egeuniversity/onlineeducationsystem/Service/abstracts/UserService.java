package com.egeuniversity.onlineeducationsystem.Service.abstracts;

import com.egeuniversity.onlineeducationsystem.dto.UserDTO;
import com.egeuniversity.onlineeducationsystem.data.User;
import com.egeuniversity.onlineeducationsystem.dto.UserSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    User addUser(User user);

    void removeUser(String userId);

    User getUser(String userId);

    User updateUser(String id, UserDTO dto);

    Page<User> listUsers(UserSearchDTO dto) throws Exception;

    String handleFileUpload(String id, MultipartFile file) throws IOException;

}
