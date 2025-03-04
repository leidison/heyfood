package com.heyfood.repositories

interface BaseRepository<T, ID: Any> {
    suspend fun create(entity: T): ID
    suspend fun update(entity: T)
    suspend fun delete(entity: T)
    suspend fun all(): List<T>
    suspend fun find(id: ID): T?
    suspend fun paginate(keep: Int, skip: Int): Pair<List<T>, Int>
    suspend fun count(): Int
}