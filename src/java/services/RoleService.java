package services;

import models.*;
import dataaccess.*;

public class RoleService
{
    private static final
    String WILD_CARD = "*";
    
    private
    RoleDB roleDB = new RoleDB();
    
    public Role[] getAll() throws Exception
    {
        return roleDB.get(WILD_CARD);
    }
}
