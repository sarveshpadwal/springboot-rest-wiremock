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
@ApiModel(description = "All details about the tag.")
public class Tag {
    @ApiModelProperty(notes = "The id of the tag", position = 0)
    private Integer id;

    @ApiModelProperty(notes = "The name of the tag", position = 1)
    private String name;
}
