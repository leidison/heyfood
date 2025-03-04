package com.heyfood.usecases

class UserUseCases(
    val createUser: CreateUserUseCase = CreateUserUseCase(),
    val updateUser: UpdateUserUseCase = UpdateUserUseCase(),
    val findUser: FindUserUseCase = FindUserUseCase(),
    val paginateUsers: PaginateUsersUseCase = PaginateUsersUseCase()
)