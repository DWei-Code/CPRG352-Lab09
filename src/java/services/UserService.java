package services;

import dataaccess.*;
import models.*;

public class UserService
{
    private static final
    String WILD_CARD = "*";
    
    private
    UserDB userDB = new UserDB();
    
    public void addUser(User user)
    {
//        userDB.insert(user.getEmail(), user.isActive(), user.getFirstName(),
//                      user.getLastName(), user.getPassword(), user.getRole());
    }
    
    public User[] getAll()
    {
        return null;
//        return userDB.get(WILD_CARD);
    }
    
    public void removeUser(User user)
    {
//        userDB.remove(user.getEmail());
    }
    
    public void updateUser(User user)
    {
        removeUser(user);
        addUser(user);
    }
    
    public User[] getRange(int range)
    {
        return null;
    }
}
