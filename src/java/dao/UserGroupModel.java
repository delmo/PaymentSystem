/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entities.UserGroup;
import java.util.List;

/**
 *
 * @author Rhayan
 */
public interface UserGroupModel {
        public List<String> getAdmins();
        public UserGroup getUserGroup(Long id);
        public void saveUserGroup(UserGroup ug);
        public void saveNewUserGroup(UserGroup ug);
        public void updateUserGroup(UserGroup ug);
        public void deleteUserGroup(UserGroup ug);
}
