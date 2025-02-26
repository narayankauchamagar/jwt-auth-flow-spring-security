package com.behl.cerberus.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class UserUpdationRequestDto implements Serializable {

	private static final long serialVersionUID = -7069159584211174210L;

	@Schema(requiredMode = RequiredMode.NOT_REQUIRED, description = "first-name of user", example = "Hardik", maxLength = 15, minLength = 3)
	private final String firstName;

	@Schema(requiredMode = RequiredMode.NOT_REQUIRED, description = "last-name of user", example = "Singh", maxLength = 15, minLength = 3)
	private final String lastName;

}