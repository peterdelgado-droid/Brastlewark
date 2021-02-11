package com.e.brastlewark.domain


import com.google.gson.annotations.SerializedName
data class Brastlewark (

	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("thumbnail") val thumbnail : String,
	@SerializedName("age") val age : Int,
	@SerializedName("weight") val weight : Double,
	@SerializedName("height") val height : Double,
	@SerializedName("hair_color") val hair_color : String,
	@SerializedName("professions") val professions : List<String>,
	@SerializedName("friends") val friends : List<String>,
	@SerializedName("Brastlewark") val brastlewark : List<Brastlewark>,
)
