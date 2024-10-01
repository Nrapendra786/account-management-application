package com.nrapendra.applicationdata;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "application_data")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Converts({
        @Convert(attributeName = "json", converter = JsonStringType.class),
        @Convert(attributeName = "jsonb", converter = JsonBinaryType.class)
})
public class ApplicationData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "request")
    private String request;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<Object, Object> response = new HashMap<>();

    @Column(name = "http_status_code")
    private Integer httpResponseCode;
}

