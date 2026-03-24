package com.farshonok.spring.service

import org.springframework.cglib.proxy.InvocationHandler
import org.springframework.cglib.proxy.Proxy
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class OidcUserServiceImpl(
    val userDetailsService: UserDetailsService,
) : OAuth2UserService<OidcUserRequest, OidcUser> {

    override fun loadUser(userRequest: OidcUserRequest?): OidcUser {
        return Optional.ofNullable(userRequest?.idToken)
            .map { idToken ->
                val userDetails = userDetailsService.loadUserByUsername(idToken.email)
                val oidcUser = DefaultOidcUser(userDetails.authorities, idToken)

                createProxy(userDetails, oidcUser) as OidcUser
            }.orElseThrow()
    }


    private fun createProxy(userDetails: UserDetails, oidcUser: OidcUser) : Any {
        val userDetailsMethods = UserDetails::class.java.methods.toSet()

        fun handler(): InvocationHandler = { _, method, args ->
            if (userDetailsMethods.contains(method)) {
                method.invoke(userDetails, *args)
            } else {
                method.invoke(oidcUser, *args)
            }
        }

        return Proxy.newProxyInstance(
            javaClass.classLoader,
            arrayOf(OidcUser::class.java, UserDetails::class.java),
            handler()
        ) as OidcUser
    }
}