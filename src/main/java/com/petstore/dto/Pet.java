package com.petstore.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author sarvesh
 * @version 0.0.1
 * @since 0.0.1
 */
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"id", "category", "name", "photoUrls", "tags", "status"})
@Data
@NoArgsConstructor
@ApiModel(description = "All details about the pet.")
public class Pet {
    @ApiModelProperty(notes = "The id of the pet", position = 0)
    private Long id;

    @ApiModelProperty(notes = "The category of the pet", position = 1)
    private Category category;

    @ApiModelProperty(notes = "The name of the pet", position = 2)
    private String name;

    @ApiModelProperty(notes = "List of photo urls of the pet", position = 3)
    private List<String> photoUrls;

    @ApiModelProperty(notes = "List of tags of the pet", position = 4)
    private List<Tag> tags;

    @ApiModelProperty(notes = "The status of the pet", allowableValues = "available,pending,sold", position = 5)
    private String status;
}
