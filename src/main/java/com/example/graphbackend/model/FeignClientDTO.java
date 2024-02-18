package com.example.graphbackend.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


@Data
public class FeignClientDTO implements Serializable {
    private static final long serialVersionUID = -4015718285765982226L;

    private String exception;
    private List<ErrorDetailDTO> errors = new ArrayList<>();
    private HttpStatus httpStatus;

    public FeignClientDTO() {

    }

    public FeignClientDTO(String exception, String... exceptionMessage) {
        super();
        if (StringUtils.isNotBlank(exception)) {
            this.exception = exception;
        }

        Stream.of(exceptionMessage).forEach(msg -> {
            ErrorDetailDTO errorDetail = new ErrorDetailDTO();
            errorDetail.setMessage(msg);

            errors.add(errorDetail);
        });
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("exception", exception)
                .append("errors", errors)
                .append("httpStatus", httpStatus)
                .toString();
    }
}
