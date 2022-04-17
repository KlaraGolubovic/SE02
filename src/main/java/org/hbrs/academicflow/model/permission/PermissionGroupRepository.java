package org.hbrs.academicflow.model.permission;

import java.util.List;

import org.hbrs.academicflow.model.permission.dto.PermissionGroupDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionGroupRepository extends JpaRepository<PermissionGroup, String> {
    @Query("select new org.hbrs.academicflow.model.permission.dto.PermissionGroupDTOImpl(group) from PermissionGroup group where group.name=:name")
    PermissionGroupDTO findPermissionGroupByName(@Param("name") String name);
    List<PermissionGroup> findAll();
}