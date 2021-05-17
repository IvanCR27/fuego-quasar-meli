package com.fuego.quasar.app

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories


@SpringBootApplication
@ComponentScan(
    "com.fuego.quasar"
)
@EntityScan("com.fuego.quasar.model.entity")
@EnableJpaRepositories("com.fuego.quasar.model.repository")
class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, * args)
}