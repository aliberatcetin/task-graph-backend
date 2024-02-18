package com.example.graphbackend.model;

import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
public class ErrorDetailDTO {
    private String source;
    private String message;
    private String errorCode;
    private String[] args = ArrayUtils.EMPTY_STRING_ARRAY;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("key", source)
                .append("message", message)
                .append("errorCode", errorCode)
                .append("args", args)
                .toString();
    }
}
