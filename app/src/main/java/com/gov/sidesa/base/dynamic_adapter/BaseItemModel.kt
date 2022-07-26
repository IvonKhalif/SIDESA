package com.gov.sidesa.base.dynamic_adapter

abstract class BaseItemModel {

    abstract fun type(typeFactory: ViewHolderFactory): Int

    abstract fun areItemsTheSame(
        newItem: BaseItemModel
    ): Boolean

    abstract fun areContentsTheSame(
        newItem: BaseItemModel
    ): Boolean
}