package models;

import java.io.Serializable;

public class Role implements Serializable {

    private int roleId;
    private String roleName;

    public Role() {

    }

    public Role(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }
    
    public String getRoleNameCapitalized()
    {
        String[] words = roleName.split(" ");
        
        StringBuffer sb = new StringBuffer();

        for(int i=0; i<words.length; i++)
        {
            sb.append(Character.toUpperCase(words[i].charAt(0))).append(words[i].substring(1)).append(" ");
        }          
        return sb.toString().trim();
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}