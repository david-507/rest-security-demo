package org.dmace.security.demo.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBatchDTO {

    private String name;

    @Builder.Default
    private List<Long> products = new ArrayList<>();
}
