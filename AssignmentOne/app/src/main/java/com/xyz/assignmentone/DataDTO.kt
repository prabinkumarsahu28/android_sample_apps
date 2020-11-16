package com.xyz.assignmentone

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DataDTO(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("avatar")
	val avatar: String? = null
)