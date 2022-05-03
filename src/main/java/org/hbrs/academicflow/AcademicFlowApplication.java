package org.hbrs.academicflow;

import org.hibernate.AnnotationException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AcademicFlowApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    try {
      SpringApplication.run(AcademicFlowApplication.class, args);
    } catch (BeanCreationException bce) {
      Throwable rc = bce.getRootCause();
      if (rc == null) {
        bce.printStackTrace();
      } else {
        rc.printStackTrace();
      }
    } catch (AnnotationException e) {
      e.printStackTrace();
    }
  }
}
