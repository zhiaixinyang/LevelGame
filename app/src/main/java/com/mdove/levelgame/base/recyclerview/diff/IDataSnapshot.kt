package com.mdove.levelgame.base.recyclerview.diff

interface IDataSnapshot<T> {
    fun referenceEquals(other: IDataSnapshot<T>): Boolean

    fun snapshotEquals(other: IDataSnapshot<T>): Boolean
}