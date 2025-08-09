package heroes.journey.modlib.actions

import heroes.journey.modlib.attributes.Attributes
import heroes.journey.modlib.attributes.Stat
import heroes.journey.modlib.registries.InfoProvider
import heroes.journey.modlib.registries.Registrable
import heroes.journey.modlib.registries.Registries

open class Action(
    id: String,
    val isReturnsActionList: Boolean = false,
    val requirementsMetFn: (IActionContext) -> ShowAction = { ShowAction.YES },
    val onHoverFn: (IActionContext) -> Unit = {},
    val onSelectFn: (IActionContext) -> ActionResult,
    val turnCooldown: Int = 0,
    val cost: Attributes? = null,
    val customInfoProviderFn: ((IActionContext) -> InfoProvider)? = null,
    val tags: List<Stat> = emptyList(),
    // These are target tags (Tags a target must, could, cant have)
    val requiredAllTags: List<Stat> = emptyList(),
    val requiredAnyTags: List<Stat> = emptyList(),
    val forbiddenTags: List<Stat> = emptyList()
) : Registrable(id) {

    override fun getTitle(input: IActionContext): String {
        val customProvider = customInfoProviderFn?.invoke(input)
        if (customProvider != null) {
            return customProvider.getTitle(input)
        }
        return super.getTitle(input)
    }

    override fun getDescription(input: IActionContext): String {
        val customProvider = customInfoProviderFn?.invoke(input)
        if (customProvider != null) {
            return customProvider.getDescription(input)
        }
        return super.getDescription(input)
    }

    override fun register(): Action {
        Registries.ActionManager.register(this)
        return this
    }
}

// --- Builder Classes ---
open class ActionBuilder {
    var id: String = ""
    open var isReturnsActionList: Boolean = false
    var requirementsMetFn: (IActionContext) -> ShowAction = { ShowAction.YES }
    var onHoverFn: (IActionContext) -> Unit = {}
    var onSelectFn: (IActionContext) -> ActionResult = { NullResult() }
    var turnCooldown: Int = 0
    open var cost: Attributes? = null
    var customInfoProviderFn: ((IActionContext) -> InfoProvider)? = null

    private val tagIds = mutableListOf<String>()
    private val requiredAllTagIds = mutableListOf<String>()
    private val requiredAnyTagIds = mutableListOf<String>()
    private val forbiddenTagIds = mutableListOf<String>()

    fun tag(vararg tags: String) {
        tagIds.addAll(tags)
    }

    fun requiresAll(vararg tags: String) {
        requiredAllTagIds.addAll(tags)
    }

    fun requiresAny(vararg tags: String) {
        requiredAnyTagIds.addAll(tags)
    }

    fun forbids(vararg tags: String) {
        forbiddenTagIds.addAll(tags)
    }

    protected fun resolveTags(ids: List<String>): List<Stat> = ids.map { tagId ->
        Registries.StatManager[tagId] ?: throw IllegalArgumentException("Stat not found: $tagId")
    }

    open fun build(): Action = Action(
        id = id,
        isReturnsActionList = isReturnsActionList,
        requirementsMetFn = { ctx -> requirementsMetFn(ctx) },
        onHoverFn = { ctx -> onHoverFn(ctx) },
        onSelectFn = { ctx -> onSelectFn(ctx) },
        turnCooldown = turnCooldown,
        cost = cost,
        customInfoProviderFn = customInfoProviderFn,
        tags = resolveTags(tagIds),
        requiredAllTags = resolveTags(requiredAllTagIds),
        requiredAnyTags = resolveTags(requiredAnyTagIds),
        forbiddenTags = resolveTags(forbiddenTagIds)
    )
}

open class OptionActionBuilder : ActionBuilder() {
    override var isReturnsActionList: Boolean
        get() = false
        set(_) {}

    override var cost: Attributes?
        get() = null
        set(_) {}

    override fun build(): OptionAction = object : OptionAction(
        id = id,
        requirementsMetFn = { ctx -> requirementsMetFn(ctx) },
        onHoverFn = { ctx -> onHoverFn(ctx) },
        onSelectFn = { ctx -> onSelectFn(ctx) },
        1,
        cost = null
    ) {}
}

class BooleanOptionActionBuilder : OptionActionBuilder() {
    var isTrue: Boolean = true
    override var isReturnsActionList: Boolean
        get() = false
        set(_) {}

    override var cost: Attributes?
        get() = null
        set(_) {}

    override fun build(): BooleanOptionAction = BooleanOptionAction(
        id = id,
        requirementsMetFn = { ctx -> requirementsMetFn(ctx) },
        onHoverFn = { ctx -> onHoverFn(ctx) },
        onSelectFn = { ctx -> onSelectFn(ctx) },
        isTrue = isTrue,
        cost = null
    )
}

class TargetActionBuilder<I> {
    var id: String = ""
    var getTargets: (IActionContext) -> List<I> = { emptyList() }
    var targetAction: String = ""
    var requirementsMetFn: (IActionContext) -> ShowAction = { ShowAction.YES }
    var onHoverFn: (IActionContext) -> Unit = {}
    var cost: Attributes? = null
    var customInfoProviderFn: ((IActionContext) -> InfoProvider)? = null

    private val tagIds = mutableListOf<String>()
    private val requiredAllTagIds = mutableListOf<String>()
    private val requiredAnyTagIds = mutableListOf<String>()
    private val forbiddenTagIds = mutableListOf<String>()

    fun tag(vararg tags: String) {
        tagIds.addAll(tags)
    }

    fun requiresAll(vararg tags: String) {
        requiredAllTagIds.addAll(tags)
    }

    fun requiresAny(vararg tags: String) {
        requiredAnyTagIds.addAll(tags)
    }

    fun forbids(vararg tags: String) {
        forbiddenTagIds.addAll(tags)
    }

    private fun resolveTags(ids: List<String>): List<Stat> = ids.map { tagId ->
        Registries.StatManager[tagId] ?: throw IllegalArgumentException("Stat not found: $tagId")
    }

    fun build(): Action {
        return Action(
            id = id,
            isReturnsActionList = true,
            requirementsMetFn = { input ->
                val targets = getTargets(input)
                val targetShowAction = if (targets.isEmpty()) ShowAction.GRAYED else ShowAction.YES
                requirementsMetFn(input).and(targetShowAction)
            },
            onHoverFn = { ctx -> onHoverFn(ctx) },
            onSelectFn = { input ->
                val actionOptions = getTargets(input).map { option ->
                    val copyInput = (input as? IActionContext)?.getHashMapCopy()
                        ?: hashMapOf<String, String>()
                    copyInput["target"] = option.toString()
                    ActionEntry(targetAction, copyInput)
                }
                ActionListResult(actionOptions)
            },
            cost = cost,
            customInfoProviderFn = customInfoProviderFn,
            tags = resolveTags(tagIds),
            requiredAllTags = resolveTags(requiredAllTagIds),
            requiredAnyTags = resolveTags(requiredAnyTagIds),
            forbiddenTags = resolveTags(forbiddenTagIds)
        )
    }
}

// --- DSL entrypoints ---
fun action(init: ActionBuilder.() -> Unit): Action {
    val builder = ActionBuilder()
    builder.apply(init)
    return builder.build()
}

fun optionAction(init: OptionActionBuilder.() -> Unit): OptionAction {
    val builder = OptionActionBuilder()
    builder.apply(init)
    return builder.build()
}

fun booleanOptionAction(init: BooleanOptionActionBuilder.() -> Unit): BooleanOptionAction {
    val builder = BooleanOptionActionBuilder()
    builder.apply(init)
    return builder.build()
}

fun <I> targetAction(init: TargetActionBuilder<I>.() -> Unit): Action {
    val builder = TargetActionBuilder<I>()
    builder.apply(init)
    return builder.build()
}
