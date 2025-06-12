package heroes.journey.modding.api.definitions

data class ItemSubType(
    val name: String,
    val parentType: ItemType
) {
    override fun toString(): String = name
}
