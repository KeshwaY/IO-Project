package com.pk.project_io.UserGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    UserGroup findUserGroupByName(String userGroupName);
}
