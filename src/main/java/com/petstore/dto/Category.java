package com.petstore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "name"})
@Data
@NoArgsConstructor
@ApiModel(description = "All details about the category.")
public class Category {
    @ApiModelProperty(notes = "The id of the category", position = 0)
    private Long id;

    @ApiModelProperty(notes = "The name of the category", position = 1)
    private String name;
}
