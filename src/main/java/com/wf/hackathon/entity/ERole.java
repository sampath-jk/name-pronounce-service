package com.wf.hackathon.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ERole {
  ROLE_USER("user"),
  ROLE_ADMIN("admin");

  private String value;

  ERole(String value){
    this.value = value;
  }

  @JsonValue
  public String getValue(){
    return value;
  }
}
