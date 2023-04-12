package service.role;

import model.Role;
import model.RoleName;

import java.util.ArrayList;
import java.util.List;

public class RoleServiceIMPL implements IRoleService{
    public static List<Role> roleList = new ArrayList<>();
    static {
        roleList.add(new Role(1, RoleName.USER));
        roleList.add(new Role(2, RoleName.PM));
        roleList.add(new Role(3, RoleName.ADMIN));
    }
    @Override
    public List<Role> findAll() {
        return roleList;
    }

    @Override
    public Role findByName(RoleName name) {
        for (Role role:roleList) {
            if (name == role.getName()) {
                return role;
            }
        }
        return null;
    }
}
