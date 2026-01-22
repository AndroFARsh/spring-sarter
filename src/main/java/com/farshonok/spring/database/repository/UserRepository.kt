package com.farshonok.spring.database.repository

import com.farshonok.spring.database.entities.User
import org.springframework.data.repository.Repository

interface UserRepository : Repository<User, Int>


