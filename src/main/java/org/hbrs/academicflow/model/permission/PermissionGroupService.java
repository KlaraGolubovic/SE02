package org.hbrs.academicflow.model.permission;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionGroupService {
    private static final List<PermissionGroup> PERMISSION_GROUPS = Lists.newCopyOnWriteArrayList();

    private final PermissionGroupRepository repository;

    @PostConstruct
    public void loadPermissionGroups() {
        CompletableFuture.runAsync(() -> PERMISSION_GROUPS.addAll(this.repository.findAll()));
    }

    public Optional<PermissionGroup> findPermissionGroupByName(String name) {
        return PERMISSION_GROUPS.stream().filter(group -> group.getName().equalsIgnoreCase(name)).findFirst();
    }

    public List<String> findPermissionGroupNames() {
        return PERMISSION_GROUPS.stream().map(PermissionGroup::getName).collect(Collectors.toList());
    }
}
