package com.nrapendra.applicationdata;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "application_data")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "request")
    private String request;

    @Column(columnDefinition = "jsonb")
    @Type(value=JsonBinaryType.class)
    private Map<Object, Object> response = new HashMap<>();

    @Column(name = "http_status_code")
    private Integer httpStatusCode;
}

