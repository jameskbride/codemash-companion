package com.jameskbride.codemashcompanion.bus

import com.jameskbride.codemashcompanion.data.model.Tag

data class TagsUpdatedEvent constructor(val tags: MutableList<Tag> = mutableListOf())