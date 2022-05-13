package com.wf.hackathon.entity;

public enum ERole {
  ROLE_USER("user"),
  ROLE_ADMIN("admin");

  private String value;

  ERole(String value){
    this.value = value;
  }
  public String getValue(){
    return value;
  }
}
