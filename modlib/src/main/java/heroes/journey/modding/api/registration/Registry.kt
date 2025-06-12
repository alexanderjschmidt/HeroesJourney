package heroes.journey.modding.api.registration

class Registry<T> : MutableMap<String, T> by mutableMapOf() {
    fun get(entryStrings: List<String>): List<T> {
        return entryStrings.mapNotNull { get(it) }
    }

    fun getOrNull(key: String?): T? {
        if (key == null) return null
        return this[key]
    }

    fun register(entry: T): T {
        val key = entry.toString()
        if (containsKey(key)) {
            throw RuntimeException("${this::class.java} cannot register with name $key because that name is already registered")
        }
        put(key, entry)
        return entry
    }
}

