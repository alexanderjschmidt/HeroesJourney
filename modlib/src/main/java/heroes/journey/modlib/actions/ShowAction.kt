package heroes.journey.modlib.actions

enum class ShowAction {
  YES, GRAYED, NO;

  fun and(other: ShowAction): ShowAction {
    return if (this.ordinal > other.ordinal) this else other
  }
}
