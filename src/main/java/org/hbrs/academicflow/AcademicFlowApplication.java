package org.hbrs.academicflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AcademicFlowApplication extends SpringBootServletInitializer {
  public static void main(String[] args) {
    SpringApplication.run(AcademicFlowApplication.class, args);
  }
}
