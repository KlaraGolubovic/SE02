package org.hbrs.academicflow.control.apply;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.hbrs.academicflow.model.apply.Apply;
import org.hbrs.academicflow.model.apply.ApplyDTO;
import org.hbrs.academicflow.model.apply.ApplyRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ApplyService {

  @Autowired
  private final ApplyMapper mapper;
  @Autowired
  private final ApplyRepository repository;

  public @NotNull List<ApplyDTO> findApplication() {
    return this.repository.findAll().stream().map(this.mapper::toDTO).collect(Collectors.toList());
  }

  public @NotNull Optional<Apply> findStudentByApplication(Apply apply) {
    return this.repository.findById(apply.getId());
  }

  public void delete(Apply apply) {
    this.repository.delete(apply);
  }

  public Apply findByApplicationID(UUID uuid) {
    return this.repository.findByApplyID(uuid);
  }

  public Apply doSaveApplication(Apply apply) {
    return this.repository.save(apply);
  }

  public void close() {
    this.repository.flush();
  }

  public List<Apply> findAll() {
    return this.repository.findAll();
  }

  public Apply findByStudentAndAdvertisement(UUID uuid, UUID uuid2) {
    return this.repository.findByStudentAndAdvertisement(uuid, uuid2);
  }

  public List<Apply> findApplicationsByCompany(UUID id) {
    return this.repository.findApplicationsByCompany(id);
  }

  public List<Apply> findApplicationByStudent(UUID id) {
    return this.repository.findApplicationsByStudent(id);
  }

  public List<Apply> findApplicationsByUserID(UUID id) {
    return this.repository.findApplicationsByUserID(id);
  }
}
