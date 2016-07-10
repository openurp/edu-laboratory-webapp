package org.openurp.edu.laboratory.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.LongIdObject;

@Entity(name = "org.openurp.edu.laboratory.model.ExprTest")
public class ExprTest extends LongIdObject {

  private static final long serialVersionUID = -9082945017125428707L;

  @ManyToOne(fetch = FetchType.LAZY)
  private ExprProgram program;

  @Size(max = 500)
  private String content;

  public ExprProgram getProgram() {
    return program;
  }

  public void setProgram(ExprProgram program) {
    this.program = program;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
