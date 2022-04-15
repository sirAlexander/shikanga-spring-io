package com.shikanga.user.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;


@Value
@Builder
@JsonDeserialize(builder = Department.DepartmentBuilder.class)
public class Department {

    Long departmentId;
    String departmentName;
    String departmentAddress;
    String departmentCode;

    @JsonPOJOBuilder(withPrefix = "")
    public static class DepartmentBuilder {
    }

}
