package com.example.graphbackend.model;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorDTO {

    private long timestamp = System.currentTimeMillis();
    private String exception;
    private List<ErrorDetailDTO> errors = new ArrayList<>();

    public void addError(ErrorDetailDTO errorDetailDTO) {
        errors.add(errorDetailDTO);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("timestamp", timestamp)
            .append("exception", exception)
            .append("errors", errors)
            .toString();
    }
}
