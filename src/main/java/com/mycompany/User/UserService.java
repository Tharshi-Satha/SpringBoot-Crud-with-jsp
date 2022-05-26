package com.mycompany.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired private UserRepository repo;

    public List<User> listAll(){
        List<User> users = (List<User>)repo.findAll();
        return users;
    }
    public void save(User user){
        repo.save(user);
    }
    public User get(Integer id) throws UserNotFoundException {
        Optional<User> userDetail = repo.findById(id);
        if (userDetail.isPresent()){
            return userDetail.get();
        }else {
            throw new UserNotFoundException("Could not find any users with Id");
        }
    }

    public void delete(Integer id) throws UserNotFoundException {
        Long count = repo.countById(id);
        if (count == null || count ==0){
            throw new UserNotFoundException("Could not find any users with ID" + id);
        }
        repo.deleteById(id);
    }
}
