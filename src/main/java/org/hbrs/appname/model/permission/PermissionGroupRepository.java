package org.hbrs.appname.model.permission;

import org.hbrs.appname.model.permission.dto.PermissionGroupDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionGroupRepository extends JpaRepository<PermissionGroup, Integer> {
    @Query("select new org.hbrs.appname.model.permission.dto.PermissionGroupDTOImpl(group) from PermissionGroup group where group.name=:name")
    PermissionGroupDTO findPermissionGroupByName(@Param("name") String name);
}